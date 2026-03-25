# Peumax Calculate Tools

Android app to calculate the angle of truncated cones.

## Requirements

- Android Studio (any recent version)
- Android SDK installed
- A physical Android device or emulator with Android 8.0+

---

## Build

```bash
cd peuxmax-calculate-tools
./gradlew assembleDebug
```

The APK will be at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

## Install on device

1. Enable **Developer Options** on your phone:
   - Go to **Settings → About phone**
   - Tap **Build number** 7 times
   - Go back to **Settings → Developer options**
   - Enable **USB debugging**

2. Connect the phone via USB and accept the prompt on the device.

3. Install the APK:

```bash
adb install -r -d app/build/outputs/apk/debug/app-debug.apk
```

> If `adb` is not found, add Android SDK platform-tools to your PATH or use the full path:
> `~/Library/Android/sdk/platform-tools/adb install ...`

4. Open **Peumax Calculate Tools** on the device.
