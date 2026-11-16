package com.coursework.data.network

// 10.0.2.2 is the computer the emulator runs on. On a real phone, run `adb reverse tcp:8080 tcp:8080` and use
// http://localhost:8080 instead.
internal actual val serverUrl: String = "http://10.0.2.2:8080"
