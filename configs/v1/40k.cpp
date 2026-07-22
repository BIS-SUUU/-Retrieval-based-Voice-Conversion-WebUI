#include <vector>
#include <optional>
#include <string>

struct TrainConfig {
    int log_interval = 200;
    int seed = 1234;
    int epochs = 20000;
    float learning_rate = 1e-4f;
    std::vector<float> betas = {0.8f, 0.99f};
    float eps = 1e-9f;
    int batch_size = 4;
    float lr_decay = 0.999875f;
    int segment_size = 12800;
    int init_lr_ratio = 1;
    int warmup_epochs = 0;
    float c_mel = 45.0f;
    float c_kl = 1.0f;
};

struct DataConfig {
    float max_wav_value = 32768.0f;
    int sampling_rate = 40000;          // تغير من 32000
    int filter_length = 2048;           // تغير من 1024
    int hop_length = 400;               // تغير من 320
    int win_length = 2048;              // تغير من 1024
    int n_mel_channels = 125;           // تغير من 80
    float mel_fmin = 0.0f;
    std::optional<float> mel_fmax = std::nullopt;
};

struct ModelConfig {
    int inter_channels = 192;
    int hidden_channels = 192;
    int filter_channels = 768;
    int n_heads = 2;
    int n_layers = 6;
    int kernel_size = 3;
    float p_dropout = 0.0f;
    std::string resblock = "1";
    std::vector<int> resblock_kernel_sizes = {3, 7, 11};
    std::vector<std::vector<int>> resblock_dilation_sizes = {
        {1, 3, 5}, {1, 3, 5}, {1, 3, 5}
    };
    std::vector<int> upsample_rates = {10, 10, 2, 2};          // تغيرت (كانت 5 أرقام)
    int upsample_initial_channel = 512;
    std::vector<int> upsample_kernel_sizes = {16, 16, 4, 4};   // تغيرت (كانت 5 أرقام)
    bool use_spectral_norm = false;
    int gin_channels = 256;
    int spk_embed_dim = 109;
};

// الهيكل الرئيسي (نفس الشيء)
struct Config {
    TrainConfig train;
    DataConfig data;
    ModelConfig model;
};
