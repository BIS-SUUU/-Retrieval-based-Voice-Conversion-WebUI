package com.example.vc

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import okhttp3.*
import java.io.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private val SAMPLE_RATE = 22050
    private val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
    private val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

    private val client = OkHttpClient()

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            // handle result if needed
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }

        // For demo: start a background thread to record and send audio
        thread {
            val bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT)
            val recorder = AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AUDIO_FORMAT, bufferSize)
            val outFile = File(cacheDir, "recorded.raw")
            val fos = FileOutputStream(outFile)

            recorder.startRecording()
            val buffer = ByteArray(bufferSize)
            // record ~3 seconds for demo
            var read = 0
            val endTime = System.currentTimeMillis() + 3000
            while (System.currentTimeMillis() < endTime) {
                read = recorder.read(buffer, 0, buffer.size)
                if (read > 0) {
                    fos.write(buffer, 0, read)
                }
            }
            recorder.stop()
            recorder.release()
            fos.close()

            // wrap PCM raw into a WAV file
            val wavFile = File(cacheDir, "recorded.wav")
            rawToWave(outFile, wavFile)

            // send to server
            val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "recorded.wav", wavFile.asRequestBody("audio/wav".toMediaType()))
                .build()

            val request = Request.Builder()
                .url("http://10.0.2.2:8000/convert") // 10.0.2.2 for emulator -> host
                .post(requestBody)
                .build()

            client.newCall(request).execute().use { resp ->
                if (!resp.isSuccessful) return@thread
                val tmp = File(cacheDir, "out.wav")
                val sink = tmp.outputStream()
                sink.use { it.write(resp.body!!.bytes()) }

                // play resulting wav
                playWav(tmp)
            }
        }
    }

    private fun rawToWave(rawFile: File, waveFile: File) {
        val rawData = rawFile.readBytes()
        val totalAudioLen = rawData.size.toLong()
        val totalDataLen = totalAudioLen + 36
        val longSampleRate = SAMPLE_RATE.toLong()
        val channels = 1
        val byteRate = 16 * SAMPLE_RATE * channels / 8

        val out = FileOutputStream(waveFile)
        val header = ByteArray(44)

        // RIFF/WAVE header
        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()
        writeInt(header, 4, (totalDataLen + 8).toInt())
        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()
        writeInt(header, 16, 16)
        writeShort(header, 20, 1)
        writeShort(header, 22, channels)
        writeInt(header, 24, longSampleRate.toInt())
        writeInt(header, 28, byteRate)
        writeShort(header, 32, (channels * 16 / 8).toShort())
        writeShort(header, 34, 16)
        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()
        writeInt(header, 40, totalAudioLen.toInt())

        out.write(header, 0, 44)
        out.write(rawData)
        out.close()
    }

    private fun writeInt(header: ByteArray, offset: Int, value: Int) {
        header[offset] = (value and 0xff).toByte()
        header[offset + 1] = ((value shr 8) and 0xff).toByte()
        header[offset + 2] = ((value shr 16) and 0xff).toByte()
        header[offset + 3] = ((value shr 24) and 0xff).toByte()
    }

    private fun writeShort(header: ByteArray, offset: Int, value: Short) {
        header[offset] = (value.toInt() and 0xff).toByte()
        header[offset + 1] = ((value.toInt() shr 8) and 0xff).toByte()
    }

    private fun playWav(file: File) {
        // Very simple WAV player for PCM16 mono
        val bytes = file.readBytes()
        // Parse header
        val data = bytes.copyOfRange(44, bytes.size)
        val minBufSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AUDIO_FORMAT)
        val audioTrack = AudioTrack.Builder()
            .setAudioFormat(
                android.media.AudioFormat.Builder()
                    .setEncoding(AUDIO_FORMAT)
                    .setSampleRate(SAMPLE_RATE)
                    .setChannelMask(android.media.AudioFormat.CHANNEL_OUT_MONO)
                    .build()
            )
            .setBufferSizeInBytes(minBufSize)
            .build()
        audioTrack.play()
        audioTrack.write(data, 0, data.size)
        audioTrack.stop()
        audioTrack.release()
    }
}
