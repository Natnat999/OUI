package com.natnat999.oui

/**
 * Interface JNI pour communiquer avec llama.cpp (compilé en C++).
 * En production, cette classe charge la bibliothèque native 'llama-android'.
 */
class LlamaInterface {
    init {
        try {
            System.loadLibrary("llama-android")
        } catch (e: UnsatisfiedLinkError) {
            // Gérer le cas où la lib n'est pas encore compilée
        }
    }

    /**
     * Initialise le modèle GGUF à partir du chemin local.
     */
    external fun initModel(modelPath: String): Boolean

    /**
     * Génère une réponse à partir d'un prompt.
     */
    external fun generateResponse(prompt: String): String

    /**
     * Libère les ressources du modèle.
     */
    external fun freeModel()
}
