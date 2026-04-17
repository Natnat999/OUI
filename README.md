# OUI - Local AI Chat for Android 📱🤖

**OUI** is a professional Android application designed to run Large Language Models (LLM) locally on your device's CPU. No data leaves your phone.

## Features
- 🚀 **100% Local Inference**: Uses `llama.cpp` to run GGUF models on mobile CPU.
- 💎 **Material You Design**: Modern UI with dynamic colors (Material 3).
- 📦 **Gemma-4B Uncensored**: Optimized for the powerful `Gemma-4-E4B-Uncensored` model.
- 🛠️ **Auto-Release**: Compiled and released via GitHub Actions.

## Installation
1. Download the latest APK from the [Releases](https://github.com/Natnat999/OUI/releases) section.
2. At first launch, the app will download the `model.gguf` (approx. 2.6GB) from HuggingFace.
3. Chat freely and privately!

## GitHub Actions & APK Signing
To enable automatic APK signing, add the following secrets to your GitHub repository:
- `SIGNING_KEY`: Your `.jks` file encoded in Base64 (`base64 -w0 your_key.jks`).
- `ALIAS`: Your key alias.
- `KEY_STORE_PASSWORD`: Your keystore password.
- `KEY_PASSWORD`: Your key password.

## Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose (Material 3)
- **Engine**: llama.cpp (JNI)
- **Networking**: OkHttp

## License
MIT
