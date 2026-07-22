#include <string>

struct RuntimeConfig {
    // مسارات الملفات
    std::string pth_path = "assets/weights/kikiV1.pth";
    std::string index_path = "logs/kikiV1.index";
    
    // إعدادات الصوت (Host API)
    std::string sg_hostapi = "MME";
    bool sg_wasapi_exclusive = false;
    std::string sg_input_device = "VoiceMeeter Output (VB-Audio Vo";
    std::string sg_output_device = "VoiceMeeter Aux Input (VB-Audio";
    
    // إعدادات المعالجة
    std::string sr_type = "sr_device";
    float threhold = -60.0f;          // ملاحظة: الإملاء الأصلي "threhold" (بدون sh)
    float pitch = 12.0f;
    float rms_mix_rate = 0.5f;
    float index_rate = 0.0f;
    float block_time = 0.13f;
    float crossfade_length = 0.08f;
    float extra_time = 2.0f;
    
    // طريقة استخراع الـ F0
    std::string f0method = "rmvpe";
};
