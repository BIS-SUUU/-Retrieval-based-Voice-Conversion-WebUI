<div align="center">

<h1>Retrieval-based-Voice-Conversion-WebUI</h1>
إطار عمل بسيط وسهل الاستخدام لتحويل جرس الصوت أو تغيير الصوت.<br><br>

[![صُنع بحب](https://img.shields.io/badge/made_with-%E2%9D%A4-red?style=for-the-badge&labelColor=orange)](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI)

<img src="https://counter.seku.su/cmoe?name=rvc&theme=r34" /><br>

[![Licence](https://img.shields.io/github/license/RVC-Project/Retrieval-based-Voice-Conversion-WebUI?style=for-the-badge)](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/blob/main/LICENSE)
[![Huggingface](https://img.shields.io/badge/🤗%20-Models-yellow.svg?style=for-the-badge)](https://huggingface.co/lj1995/VoiceConversionWebUI/tree/main/)


[**سجل التغييرات**](./Changelog_EN.md) | [**الأسئلة الشائعة (Frequently Asked Questions)**](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/wiki/FAQ-(Frequently-Asked-Questions))

[**English**](../en/README.en.md) | [**中文简体**](../../README.md) | [**日本語**](../jp/README.ja.md) | [**한국어**](../kr/README.ko.md) ([**韓國語**](../kr/README.ko.han.md)) | [**Français**](../fr/README.fr.md) | [**Türkçe**](../tr/README.tr.md) | [**Português**](../pt/README.pt.md) | [**العربية**](../ar/README.ar.md)

</div>

شاهد [الفيديو التوضيحي](https://www.bilibili.com/video/BV1pm4y1z7Gm/) الخاص بنا هنا!

<table>
   <tr>
		<td align="center">واجهة ويب للتدريب والاستدلال</td>
		<td align="center">واجهة رسومية لتغيير الصوت في الوقت الفعلي</td>
	</tr>
  <tr>
		<td align="center"><img src="https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/assets/129054828/092e5c12-0d49-4168-a590-0b0ef6a4f630"></td>
    <td align="center"><img src="https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/assets/129054828/730b4114-8805-44a1-ab1a-04668f3c30a6"></td>
	</tr>
	<tr>
		<td align="center">go-webui.bat</td>
		<td align="center">go-realtime_gui.bat</td>
	</tr>
  <tr>
    <td align="center">يمكنك اختيار الإجراء الذي ترغب في تنفيذه بحرية.</td>
		<td align="center">لقد حققنا زمن انتقال (latency) من الطرف إلى الطرف قدره 170 مللي ثانية. ومع استخدام أجهزة الإدخال والإخراج التي تدعم بروتوكول ASIO، تمكنا من تحقيق زمن انتقال قدره 90 مللي ثانية، إلا أن ذلك يعتمد بشكل كبير على دعم برمجيات التشغيل (drivers) الخاصة بالعتاد.</td>
	</tr>
</table>

تستخدم مجموعة البيانات الخاصة بنموذج التدريب المسبق ما يقرب من 50 ساعة من التسجيلات الصوتية عالية الجودة المستمدة من مجموعة بيانات VCTK مفتوحة المصدر.

ستُضاف بانتظام مجموعات بيانات لأغانٍ مرخّصة وعالية الجودة إلى مجموعة التدريب لتستخدمها، دون الحاجة إلى القلق بشأن انتهاك حقوق الطبع والنشر.

ترقبوا النموذج الأساسي المُدرَّب مسبقاً من إصدار RVCv3، الذي يتميز بمعاملات (parameters) أكثر، وبيانات تدريب أوسع نطاقاً، ونتائج أفضل، مع الحفاظ على سرعة الاستدلال (inference speed) دون تغيير، فضلاً عن حاجته إلى كمية أقل من البيانات لإتمام عملية التدريب.

## سمات:
+ تقليل تسرب النبرة (tone leakage) عن طريق استبدال ميزة المصدر بميزة من مجموعة التدريب باستخدام آلية الاسترجاع لأفضل نتيجة (top-1 retrieval)؛
+ تدريب سهل وسريع، حتى على بطاقات الرسوميات الضعيفة؛
+ التدريب باستخدام كميات قليلة من البيانات (يُوصى بـ 10 دقائق أو أكثر من الكلام منخفض الضوضاء)؛
+ دمج النماذج لتغيير الطابع الصوتي (باستخدام علامة تبويب معالجة ckpt -> دمج ckpt)؛
+ واجهة ويب سهلة الاستخدام؛
+ نموذج pymss/MSST لفصل الأصوات البشرية عن الآلات الموسيقية بسرعة؛
+ خوارزمية استخلاص الأصوات ذات الطبقة العالية [InterSpeech2023-RMVPE](#Credits) لتجنب مشكلة الصوت المكتوم. توفر هذه الخوارزمية أفضل النتائج (بفارق ملحوظ)، كما أنها أسرع وتستهلك موارد أقل مقارنةً بـ Crepe_full؛
+ تستخدم أنظمة AMD/Intel مجموعة الاعتماديات الخاصة بالمعالج (CPU)؛ إذ قد يستخدم نظام Windows تقنية DirectML، بينما يستخدم نظام Linux المعالج (CPU)؛

## إعداد البيئة

يستهدف هذا الفرع **Python 3.12 x64**. قم ​​بتنفيذ جميع الأوامر من المجلد الجذر للمستودع. يُنصح باستخدام نظام Ubuntu 24.04 x86_64.

### أوبونتو 24.04

```bash
sudo apt update
sudo apt install -y python3.12 python3.12-venv python3.12-dev ffmpeg unzip libsndfile1 libportaudio2

python3.12 -m venv .venv
source .venv/bin/activate
python -m pip install --upgrade pip setuptools wheel
```

### ويندوز

قم بتثبيت Python 3.12 x64، ثم أنشئ بيئة افتراضية:

```powershell
py -3.12 -m venv .venv
.venv\Scripts\activate
python -m pip install --upgrade pip setuptools wheel
```

### اختر التبعيات حسب العتاد

<div dir="rtl">

| العتاد | التثبيت |
| ---: | ---: |
| المعالج (CPU)، AMD، Intel | استخدم `requirments_cpu_py312.txt`؛ قد يستخدم Windows تقنية DirectML، بينما يستخدم Linux المعالج (CPU) |
| سلسلة NVIDIA RTX 50 | ثبّت زوج Torch مع CUDA 12.8 أولاً، ثم `requirments_cu128_py312.txt` |
| معالجات NVIDIA الرسومية قبل سلسلة RTX 50 | ثبّت زوج Torch مع CUDA 11.8 أولاً، ثم `requirments_cu118_py312.txt` |

</div>

#### المعالج (CPU)، AMD، Intel

```bash
python -m pip install -r requirments_cpu_py312.txt
```

#### سلسلة NVIDIA RTX 50: على مرحلتين

```bash
python -m pip install torch==2.7.1+cu128 torchaudio==2.7.1+cu128 \
  --index-url https://download.pytorch.org/whl/cu128 \
  --extra-index-url https://pypi.org/simple
python -m pip install -r requirments_cu128_py312.txt
```

#### معالجات NVIDIA الرسومية قبل سلسلة RTX 50: على مرحلتين

```bash
python -m pip install torch==2.7.1+cu118 torchaudio==2.7.1+cu118 \
  --index-url https://download.pytorch.org/whl/cu118 \
  --extra-index-url https://pypi.org/simple
python -m pip install -r requirments_cu118_py312.txt
```

التحقق من Torch وCUDA:

```bash
python -c "import torch; print('torch:', torch.__version__); print('cuda:', torch.version.cuda); print('cuda available:', torch.cuda.is_available())"
```


### فهارس الحزم

تُحدد ملفات `requirements_*.txt` الثلاثة فهارس الحزم الخاصة بها في بدايتها؛ لذا يُرجى الإبقاء على الخوادم المرآتية (mirrors) الافتراضية الموجودة في البر الرئيسي للصين. وللتحول إلى استخدام الفهارس الرسمية، استبدل فقط `--index-url` و `--extra-index-url` مع الإبقاء على إصدارات الحزم، ولاحقات CUDA، وترتيب الخطوات المرحليتين (two-stage order) دون تغيير.

| Default mirror (المرآة الافتراضية) | Official source (مصدر رسمي) |
| --- | --- |
| `https://mirrors.pku.edu.cn/pypi/simple` | `https://pypi.org/simple` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cpu` | `https://download.pytorch.org/whl/cpu` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cu118` | `https://download.pytorch.org/whl/cu118` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cu128` | `https://download.pytorch.org/whl/cu128` |

## النماذج وأدلة وقت التشغيل

تقوم واجهة الويب (WebUI) بإنشاء مجلدات وقت التشغيل تلقائياً. قم بتنزيل النماذج من [مستودع نماذج Hugging Face](https://huggingface.co/lj1995/VoiceConversionWebUI/tree/main) وحافظ على الهيكلية التالية:

```text
assets/
├── hubert_base/
│   ├── config.json
│   ├── preprocessor_config.json
│   └── pytorch_model.bin
├── rmvpe/rmvpe.pt
├── pretrained/
├── pretrained_v2/
├── pymss_weights/
├── weights/        # الخاصة بالمستخدم .pth بصيغة (RVC) نماذج
└── indices/        # فِهرِس الخاصة بالمستخدم (index) ملفات
logs/
└── mute/           # عينات صمت للتدريب

# المسارات الدقيقة المستخدمة في الكود
assets/hubert_base/preprocessor_config.json
assets/hubert_base/pytorch_model.bin
assets/rmvpe/rmvpe.pt
assets/pretrained/*.pth
assets/pretrained_v2/*.pth
assets/pymss_weights/*
assets/weights/*.pth
assets/indices/*.index
logs/mute/*
```

### تنزيل النماذج

```bash
python -m pip install --upgrade huggingface_hub

# مطلوب للاستنتاج واستخلاص السمات
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "hubert_base/*" --local-dir assets
hf download lj1995/VoiceConversionWebUI rmvpe.pt --revision main \
  --local-dir assets/rmvpe

# v2 و v1 مطلوب للتدريب على الإصدارين
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "pretrained/*" "pretrained_v2/*" --local-dir assets
hf download lj1995/VoiceConversionWebUI mute.zip --revision main \
  --local-dir .model-downloads
python -m zipfile -e .model-downloads/mute.zip logs

# MSST/pymss مطلوب فقط لفصل الأصوات باستخدام
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "pymss_weights/*" --local-dir assets
```

تتطلب بيئات DirectML على أنظمة Windows (المعتمدة على معالجات AMD أو Intel) أيضاً ما يلي:

```bash
hf download lj1995/VoiceConversionWebUI rmvpe.onnx --revision main \
  --local-dir assets/rmvpe
```


### FFmpeg

يقوم أمر التثبيت الخاص بـ Ubuntu المذكور أعلاه بتثبيت FFmpeg. أما في نظام Windows، فضع هذه الملفات في المجلد الجذري للمستودع:

- [ffmpeg.exe](https://huggingface.co/lj1995/VoiceConversionWebUI/resolve/main/ffmpeg.exe?download=true)
- [ffprobe.exe](https://huggingface.co/lj1995/VoiceConversionWebUI/resolve/main/ffprobe.exe?download=true)

## ابدأ واجهة المستخدم المستندة إلى الويب

```bash
python webui.py
```

بالنسبة لخادم Ubuntu بدون شاشة (headless):

```bash
python webui.py --noautoopen
```

المنفذ الافتراضي هو `7865`. ضع نماذج `.pth` الشخصية في `assets/weights/` وملفات `.index` في `assets/indices/`.
## طاقم العمل
+ [ContentVec](https://github.com/auspicious3000/contentvec/)
+ [VITS](https://github.com/jaywalnut310/vits)
+ [HIFIGAN](https://github.com/jik876/hifi-gan)
+ [Gradio](https://github.com/gradio-app/gradio)
+ [FFmpeg](https://github.com/FFmpeg/FFmpeg)
+ [Ultimate Vocal Remover](https://github.com/Anjok07/ultimatevocalremovergui)
+ [pymss-project/pymss](https://github.com/pymss-project/pymss)
+ [audio-slicer](https://github.com/openvpi/audio-slicer)
+ [Vocal pitch extraction:RMVPE](https://github.com/Dream-High/RMVPE)
  + تم تدريب واختبار النموذج المُدرَّب مسبقاً بواسطة  [yxlllc](https://github.com/yxlllc/RMVPE) و [RVC-Boss](https://github.com/RVC-Boss).

## شكراً لجميع المساهمين على جهودهم.
<a href="https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/graphs/contributors" target="_blank">
  <img src="https://contrib.rocks/image?repo=RVC-Project/Retrieval-based-Voice-Conversion-WebUI" />
</a>
