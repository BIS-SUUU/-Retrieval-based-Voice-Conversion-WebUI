<div align="center">

<h1>Retrieval-based-Voice-Conversion-WebUI</h1>
إطار عمل بسيط وسهل الاستخدام لتحويل جرس الصوت / تغيير الصوت.<br><br>

[![صُنع بـ ❤️](https://img.shields.io/badge/صُنع_بـ-%E2%9D%A4-red?style=for-the-badge&labelColor=orange
)](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI)

<img src="https://counter.seku.su/cmoe?name=rvc&theme=r34" /><br>

[![الترخيص](https://img.shields.io/github/license/RVC-Project/Retrieval-based-Voice-Conversion-WebUI?style=for-the-badge)](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/blob/main/LICENSE)
[![Huggingface](https://img.shields.io/badge/🤗%20-النماذج-yellow.svg?style=for-the-badge)](https://huggingface.co/lj1995/VoiceConversionWebUI/tree/main/)


[**سجل التغييرات**](./Changelog_EN.md) | [**الأسئلة الشائعة**](https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/wiki/FAQ-(Frequently-Asked-Questions))

[**الإنجليزية**](../en/README.en.md) | [**الصينية المبسطة**](../../README.md) | [**اليابانية**](../jp/README.ja.md) | [**الكورية**](../kr/README.ko.md) ([**الکورية (الهانجا)**](../kr/README.ko.han.md)) | [**الفرنسية**](../fr/README.fr.md) | [**التركية**](../tr/README.tr.md) | [**البرتغالية**](../pt/README.pt.md)

</div>

> شاهد [فيديو العرض التوضيحي](https://www.bilibili.com/video/BV1pm4y1z7Gm/) هنا!

<table>
   <tr>
		<td align="center">واجهة الويب للتدريب والاستدلال</td>
		<td align="center">الواجهة الرسومية لتغيير الصوت في الوقت الفعلي</td>
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
    <td align="center">يمكنك اختيار الإجراء الذي تريد تنفيذه بحرية.</td>
		<td align="center">لقد حققنا زمن انتقال شامل يبلغ 170 مللي ثانية. مع استخدام أجهزة الإدخال والإخراج ASIO، تمكنا من تحقيق زمن انتقال شامل يبلغ 90 مللي ثانية، لكنه يعتمد بشكل كبير على دعم برامج تشغيل الجهاز.</td>
	</tr>
</table>

> تستخدم مجموعة بيانات النموذج المدرب مسبقًا ما يقرب من 50 ساعة من الصوت عالي الجودة من مجموعة البيانات مفتوحة المصدر VCTK.

> ستتم إضافة مجموعات بيانات الأغاني المرخصة عالية الجودة إلى مجموعة التدريب بشكل متكرر لاستخدامك، دون الحاجة إلى القلق بشأن انتهاك حقوق النشر.

> ترقبوا النموذج الأساسي المدرب مسبقًا من RVCv3، الذي يتميز بمعاملات أكبر، وبيانات تدريب أكثر، ونتائج أفضل، وسرعة استدلال دون تغيير، ويتطلب بيانات تدريب أقل للتدريب.

## الميزات:
+ تقليل تسرب النغمة عن طريق استبدال الميزة المصدر بميزة مجموعة التدريب باستخدام استرجاع top1؛
+ تدريب سهل وسريع، حتى على بطاقات الرسومات الضعيفة؛
+ التدريب بكميات صغيرة من البيانات (يوصى بـ >=10 دقائق من الكلام منخفض الضوضاء)؛
+ دمج النماذج لتغيير الأجراس (باستخدام علامة تبويب معالجة ckpt -> دمج ckpt)؛
+ واجهة ويب سهلة الاستخدام؛
+ نموذج pymss/MSST لفصل الصوت والآلات الموسيقية بسرعة؛
+ خوارزمية استخراج النغمة عالية الدقة [InterSpeech2023-RMVPE](#الإشادات) لمنع مشكلة كتم الصوت. توفر أفضل النتائج (بشكل ملحوظ) وهي أسرع مع استهلاك أقل للموارد من Crepe_full؛
+ تستخدم أنظمة AMD/Intel مجموعة التبعيات المعتمدة على CPU؛ قد يستخدم Windows DirectML بينما يستخدم Linux CPU؛

## إعداد البيئة

هذا الفرع موجه لـ **Python 3.12 x64**. قم بتشغيل كل أمر من جذر المستودع. يُوصى باستخدام Ubuntu 24.04 x86_64.

### Ubuntu 24.04

```bash
sudo apt update
sudo apt install -y python3.12 python3.12-venv python3.12-dev ffmpeg unzip libsndfile1 libportaudio2

python3.12 -m venv .venv
source .venv/bin/activate
python -m pip install --upgrade pip setuptools wheel
```

## Windows

قم أولاً بتثبيت **Python 3.12 x64**، ثم أنشئ بيئة افتراضية:

```powershell
py -3.12 -m venv .venv
.venv\Scripts\activate
python -m pip install --upgrade pip setuptools wheel
```

# اختيار الحزم المناسبة حسب نوع العتاد

| نوع العتاد | طريقة التثبيت |
|------------|---------------|
| CPU / AMD / Intel | استخدم `requirments_cpu_py312.txt` |
| بطاقات RTX 50 | ثبّت Torch الخاص بـ CUDA 12.8 ثم `requirments_cu128_py312.txt` |
| بطاقات NVIDIA الأقدم | ثبّت Torch الخاص بـ CUDA 11.8 ثم `requirments_cu118_py312.txt` |

#### CPU، AMD، Intel

```bash
python -m pip install -r requirments_cpu_py312.txt
```

#### بطاقات NVIDIA RTX من سلسلة 50: على مرحلتين

```bash
python -m pip install torch==2.7.1+cu128 torchaudio==2.7.1+cu128 \
  --index-url https://download.pytorch.org/whl/cu128 \
  --extra-index-url https://pypi.org/simple
python -m pip install -r requirments_cu128_py312.txt
```

#### بطاقات NVIDIA الأقدم من سلسلة RTX 50: على مرحلتين

```bash
python -m pip install torch==2.7.1+cu118 torchaudio==2.7.1+cu118 \
  --index-url https://download.pytorch.org/whl/cu118 \
  --extra-index-url https://pypi.org/simple
python -m pip install -r requirments_cu118_py312.txt
```

تحقق من تثبيت Torch وCUDA:

```bash
python -c "import torch; print('torch:', torch.__version__); print('cuda:', torch.version.cuda); print('cuda available:', torch.cuda.is_available())"
```

### فهارس الحزم

تحدد ملفات `requirments_*.txt` الثلاثة فهارس الحزم الخاصة بها في بداية كل ملف. احتفظ بالمرايا (Mirrors) الافتراضية داخل الصين. وإذا كنت ترغب في استخدام المصادر الرسمية، فاستبدل فقط القيم الخاصة بـ `--index-url` و `--extra-index-url`، مع الإبقاء على إصدارات الحزم، ولاحقة CUDA، وترتيب التثبيت على المرحلتين دون أي تغيير.

| المرآة الافتراضية | المصدر الرسمي |
| --- | --- |
| `https://mirrors.pku.edu.cn/pypi/simple` | `https://pypi.org/simple` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cpu` | `https://download.pytorch.org/whl/cpu` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cu118` | `https://download.pytorch.org/whl/cu118` |
| `https://mirrors.nju.edu.cn/pytorch/whl/cu128` | `https://download.pytorch.org/whl/cu128` |

## النماذج ومجلدات التشغيل

يقوم WebUI بإنشاء مجلدات التشغيل تلقائيًا. قم بتنزيل النماذج من مستودع نماذج Hugging Face، ثم احتفظ ببنية المجلدات التالية:

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
├── weights/        # نماذج RVC الخاصة بالمستخدم بصيغة .pth
└── indices/        # ملفات .index الخاصة بالمستخدم
logs/
└── mute/           # عينات الصمت المستخدمة أثناء التدريب

# المسارات الدقيقة التي يستخدمها الكود
assets/hubert_base/config.json
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

# مطلوب للاستدلال واستخراج الخصائص
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "hubert_base/*" --local-dir assets
hf download lj1995/VoiceConversionWebUI rmvpe.pt --revision main \
  --local-dir assets/rmvpe

# مطلوب لتدريب الإصدارين v1 وv2
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "pretrained/*" "pretrained_v2/*" --local-dir assets
hf download lj1995/VoiceConversionWebUI mute.zip --revision main \
  --local-dir .model-downloads
python -m zipfile -e .model-downloads/mute.zip logs

# مطلوب فقط لفصل الغناء باستخدام pymss/MSST
hf download lj1995/VoiceConversionWebUI --revision main \
  --include "pymss_weights/*" --local-dir assets
```

تحتاج بيئات Windows التي تستخدم AMD أو Intel مع DirectML أيضًا إلى:

```bash
hf download lj1995/VoiceConversionWebUI rmvpe.onnx --revision main \
  --local-dir assets/rmvpe
```

### FFmpeg

يقوم أمر إعداد Ubuntu المذكور أعلاه بتثبيت **FFmpeg** تلقائيًا. أما في نظام Windows، فقم بوضع الملفات التالية داخل المجلد الرئيسي للمستودع (Repository):

- [ffmpeg.exe](https://huggingface.co/lj1995/VoiceConversionWebUI/resolve/main/ffmpeg.exe?download=true)
- [ffprobe.exe](https://huggingface.co/lj1995/VoiceConversionWebUI/resolve/main/ffprobe.exe?download=true)

## تشغيل WebUI

```bash
python webui.py
```

بالنسبة لخادم Ubuntu الذي يعمل بدون واجهة رسومية (Headless):

```bash
python webui.py --noautoopen
```

المنفذ (Port) الافتراضي هو `7865`.

ضع نماذج `.pth` الخاصة بك داخل `assets/weights/`، وضع ملفات `.index` داخل `assets/indices/`.

## الاعتمادات (Credits)

+ [ContentVec](https://github.com/auspicious3000/contentvec/)
+ [VITS](https://github.com/jaywalnut310/vits)
+ [HIFIGAN](https://github.com/jik876/hifi-gan)
+ [Gradio](https://github.com/gradio-app/gradio)
+ [FFmpeg](https://github.com/FFmpeg/FFmpeg)
+ [Ultimate Vocal Remover](https://github.com/Anjok07/ultimatevocalremovergui)
+ [pymss-project/pymss](https://github.com/pymss-project/pymss)
+ [audio-slicer](https://github.com/openvpi/audio-slicer)
+ [Vocal pitch extraction:RMVPE](https://github.com/Dream-High/RMVPE)
  + تم تدريب النموذج المُسبق واختباره بواسطة [yxlllc](https://github.com/yxlllc/RMVPE) و [RVC-Boss](https://github.com/RVC-Boss).

## شكرًا لجميع المساهمين على جهودهم

<a href="https://github.com/RVC-Project/Retrieval-based-Voice-Conversion-WebUI/graphs/contributors" target="_blank">
  <img src="https://contrib.rocks/image?repo=RVC-Project/Retrieval-based-Voice-Conversion-WebUI" />
</a>
