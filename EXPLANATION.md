# Project: Connectify - Learning Bluetooth, Camera, QR, and Image Upload

Welcome! This app is designed as a learning tool for beginners to understand how to implement core Android features.

## How to Proceed as a Newbie

### 1. Project Structure
The project is organized into packages by feature:
- `com.example.connectify.bluetooth`: Contains logic for scanning and listing paired Bluetooth devices.
- `com.example.connectify.camera`: Demonstrates how to use **CameraX** to display a live camera preview.
- `com.example.connectify.qr`: Uses **ML Kit** and **CameraX** to scan QR codes in real-time.
- `com.example.connectify.image`: Shows how to pick an image from the gallery and display it using **Coil**.

### 2. Permissions
Most of these features require permissions. Check `AndroidManifest.xml` to see:
- `CAMERA`: For camera and QR scanning.
- `BLUETOOTH_CONNECT` / `BLUETOOTH_SCAN`: For Bluetooth operations (varies by Android version).
- `INTERNET`: For uploading (simulated) and loading remote images.

We use `rememberLauncherForActivityResult` in Compose to request these permissions at runtime.

### 3. Key Libraries Used
- **CameraX**: The modern way to handle camera in Android.
- **ML Kit**: Google's library for Barcode/QR scanning.
- **Coil**: The recommended image loading library for Jetpack Compose.
- **Material 3**: For modern UI components like `Scaffold`, `NavigationBar`, and `Button`.

### 4. Implementation Details
- **Bluetooth**: We use `BluetoothAdapter` to get bonded devices. Note that for Android 12+, specific permissions are required.
- **Camera/QR**: We use `AndroidView` to embed a `PreviewView` from CameraX into our Compose UI.
- **Image Upload**: We use the `GetContent` contract to open the system photo picker.

### 5. Next Steps for You
1. **Run the App**: Try each tab. Grant permissions when prompted.
2. **Modify the Code**: Try changing the UI in `MainActivity.kt` or adding a real upload logic in `ImageUploaderScreen.kt`.
3. **Debug**: Check Logcat if something doesn't work as expected.

Happy Coding!
