# Retrieval-based Voice Conversion WebUI - Android Integration

هذا الفرع يحتوي على هيكل مبدئي لنسخة الأندرويد + API سيرفر لتطبيق الـ Cloud-mode وملفات توضيحية لخطوات تحويل النماذج لتشغيلها محليًا (Local-mode).

الهدف: توفير مسار عملي لتنفيذ ما طلبته — تطبيق أندرويد يقدم نفس النتائج في وضعين: متصل (يستخدم السيرفر الحالي) وبدون نت (on-device) — بدءًا من MVP عملي.

ماذا يوجد هنا؟
- fastapi_server/: سيرفر FastAPI بسيط مع endpoint لاستقبال ملف WAV وإرجاع ملف WAV (حاليًا يعيد الملف كما هو؛ نقطة إدخال لربط نموذج التحويل لاحقًا).
- android-client/: هيكل مشروع أندرويد مبسط مع كود Kotlin لالتقاط صوت، إرساله للسيرفر، وتشغيل الصوت المستلم.
- scripts/convert_to_torchscript.py: سكربت مثال لتحويل نموذج PyTorch إلى TorchScript (نقطة بداية للتحويل المحلي).
- README.md: هذه الوثيقة (باللغة العربية) مع خطوات التنفيذ.

الخطوات الموصى بها لاختبار MVP (Cloud-mode) محليًا:
1) شغل السيرفر:
   cd fastapi_server
   python -m venv .venv
   source .venv/bin/activate   # أو .venv\Scripts\activate على ويندوز
   pip install -r requirements.txt
   uvicorn main:app --reload --host 0.0.0.0 --port 8000

2) افتح مشروع الأندرويد في Android Studio داخل android-client/ واتبع README هناك لاختبار التطبيق (أو استخدم curl لإرسال ملف WAV إلى السيرفر).

ملاحظات مهمة:
- حالياً لم تُضمّن أوزان أي نموذج في المستودع. إذا أردت أن أضمّن سكربتات للتحميل التلقائي أو أضع إصدارات من الأوزان عبر GitHub Releases، أخبرني و��أعد ذلك.
- لتحويل النماذج للعمل محليًا (Local-mode) سأحتاج أوزان النموذج ونوع الـ vocoder. بعد توفيرها أستطيع تجربة torchscript/onnx ورفع ملفات التحويل.

تم إنشاء هذا الهيكل على فرع "android/integration" ليتسنى لك مراجعة التغييرات وفتح PR للـ main عند الموافقة.
