from fastapi import FastAPI, File, UploadFile, HTTPException
from fastapi.responses import FileResponse
import shutil
import os
from pathlib import Path

app = FastAPI(title="VC FastAPI Stub")

STORAGE_DIR = Path("./storage")
STORAGE_DIR.mkdir(parents=True, exist_ok=True)

@app.post("/convert")
async def convert_audio(file: UploadFile = File(...)):
    """
    Endpoint placeholder to receive an audio file and return the converted audio.
    Current behavior: echoes the uploaded file back to the client to allow end-to-end testing.

    TODO: Integrate model inference here. Example flow:
      1. Save uploaded WAV to disk
      2. Load model (TorchScript/ONNX) once at startup or keep in memory
      3. Run preprocess -> model -> vocoder
      4. Return generated WAV file
    """
    if not file.filename.lower().endswith(('.wav', '.mp3', '.ogg')):
        raise HTTPException(status_code=400, detail="Unsupported file type")

    save_path = STORAGE_DIR / file.filename
    with save_path.open("wb") as f:
        shutil.copyfileobj(file.file, f)

    # For now just return the same file back
    return FileResponse(path=save_path, media_type="audio/wav", filename=file.filename)

@app.get("/health")
async def health():
    return {"status": "ok"}
