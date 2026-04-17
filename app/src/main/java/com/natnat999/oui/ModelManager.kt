package com.natnat999.oui

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

class ModelManager(private val context: Context) {
    private val client = OkHttpClient()
    private val modelUrl = "https://huggingface.co/HauhauCS/Gemma-4-E4B-Uncensored-HauhauCS-Aggressive/resolve/main/Gemma-4-E4B-Uncensored-HauhauCS-Aggressive-Q4_K_M.gguf"
    private val modelFileName = "model.gguf"

    fun getModelPath(): String {
        return File(context.filesDir, modelFileName).absolutePath
    }

    fun isModelDownloaded(): Boolean {
        return File(context.filesDir, modelFileName).exists()
    }

    suspend fun downloadModel(onProgress: (Float) -> Unit) = withContext(Dispatchers.IO) {
        val request = Request.Builder().url(modelUrl).build()
        val response = client.newCall(request).execute()
        
        if (!response.isSuccessful) throw Exception("Failed to download model")

        val body = response.body ?: throw Exception("Empty response body")
        val totalBytes = body.contentLength()
        val file = File(context.filesDir, modelFileName)
        
        body.byteStream().use { input ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(8192)
                var bytesRead: Int
                var totalBytesRead = 0L
                
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    totalBytesRead += bytesRead
                    if (totalBytes > 0) {
                        onProgress(totalBytesRead.toFloat() / totalBytes)
                    }
                }
            }
        }
    }
}
