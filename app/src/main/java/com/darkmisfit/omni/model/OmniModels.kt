package com.darkmisfit.omni.model

import kotlinx.serialization.Serializable

@Serializable
data class LLMNode(
    val id: String,
    val name: String,
    val provider: String,
    val modelId: String,
    val role: String,
    val enabled: Boolean,
    val priority: Int,
    val status: String,
    val latencyMs: Int = 110
)

@Serializable
data class ScreenScenario(
    val id: String,
    val name: String,
    val appName: String,
    val recipient: String,
    val statusText: String,
    val defaultDraft: String,
    val screenType: String,
    val suggestions: List<String>
)

@Serializable
data class LiveCallCaller(
    val id: String,
    val name: String,
    val role: String,
    val context: String,
    val initialSpeech: String
)

@Serializable
data class LiveCallRecord(
    val id: String,
    val callerName: String,
    val callerRole: String,
    val speechToText: String,
    val activeTone: String,
    val strategistResponse: String,
    val empathResponse: String,
    val ghostResponse: String,
    val synthesisResponse: String,
    val hiddenIntent: String,
    val friction: String,
    val leverage: String,
    val catchMeUp: List<String>
)

@Serializable
data class StudioItem(
    val id: String,
    val name: String,
    val tag: String,
    val category: String,
    val description: String,
    val actionLabel: String
)

@Serializable
data class VaultAsset(
    val id: String,
    val name: String,
    val type: String, // "audio", "video", "image", "code"
    val duration: String? = null,
    val bpm: Int? = null,
    val lyrics: String? = null,
    val tags: List<String> = emptyList(),
    val createdAt: String
)

@Serializable
data class TelemetryMetrics(
    val latencyMs: Int = 12,
    val modelInference: String = "Gemini 3.8 Flash Core",
    val quorumScore: Int = 96,
    val activeEngine: String = "Omni Core v4.2",
    val memoryUsageMb: Int = 84,
    val dualLaneConfidence: Float = 0.98f
)

enum class AutoPilotTone(val label: String) {
    RAW_HONEST("Raw Honest"),
    WITTY("Witty & Sharp"),
    DIRECT_ACTION("Direct Action"),
    FLIRTY("Charming & Flirty")
}

enum class NavigationTab(val label: String) {
    KEYBOARD("Keyboard"),
    VISION("Screen Vision"),
    CALL("Live Call"),
    STUDIOS("Studios"),
    TELEMETRY("Telemetry"),
    VAULT("Vault"),
    IDENTITY("Identity")
}
