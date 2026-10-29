package com.darkmisfit.omni.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkmisfit.omni.data.OmniRepository
import com.darkmisfit.omni.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class OmniUiState(
    val currentTab: NavigationTab = NavigationTab.KEYBOARD,
    val scenarios: List<ScreenScenario> = emptyList(),
    val currentScenario: ScreenScenario? = null,
    val draftText: String = "",
    val dualLaneLane1LatencyMs: Int = 8,
    val dualLaneLane2LatencyMs: Int = 92,
    val dualLaneConfidence: Float = 0.98f,
    val suggestions: List<String> = emptyList(),
    val isAutoPilotActive: Boolean = false,
    val autoPilotTone: AutoPilotTone = AutoPilotTone.DIRECT_ACTION,
    val autoCountdown: Int? = null,
    val autoDispatchedText: String? = null,
    // Live Call State
    val callers: List<LiveCallCaller> = emptyList(),
    val currentCaller: LiveCallCaller? = null,
    val callerSpeech: String = "",
    val activeCallTone: String = "Professional",
    val callRecord: LiveCallRecord? = null,
    val isCallDeliberating: Boolean = false,
    // Studios State
    val studios: List<StudioItem> = emptyList(),
    val activeStudioId: String = "motion",
    val studioOutputMessage: String? = null,
    // Telemetry & Cascade
    val telemetry: TelemetryMetrics = TelemetryMetrics(),
    val llmCascade: List<LLMNode> = emptyList(),
    // Vault
    val vaultAssets: List<VaultAsset> = emptyList(),
    // Identity
    val manifestoTagline: String = "Dark Misfit Studios • Absolute Black Cyberpunk Engine",
    val userPersonaNuance: String = "High density, zero corporate fluff, architectural precision"
)

class OmniViewModel(
    private val repository: OmniRepository = OmniRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(OmniUiState())
    val uiState: StateFlow<OmniUiState> = _uiState.asStateFlow()

    private var autoCountdownJob: Job? = null

    init {
        val initialScenario = repository.scenarios.first()
        val initialCaller = repository.callers.first()
        _uiState.value = _uiState.value.copy(
            scenarios = repository.scenarios,
            currentScenario = initialScenario,
            draftText = initialScenario.defaultDraft,
            suggestions = initialScenario.suggestions,
            callers = repository.callers,
            currentCaller = initialCaller,
            callerSpeech = initialCaller.initialSpeech,
            studios = repository.studios,
            llmCascade = repository.initialCascade,
            vaultAssets = repository.vaultAssets.value
        )
        refreshCallCouncil(initialCaller.initialSpeech, "Professional")
    }

    fun selectTab(tab: NavigationTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab)
    }

    fun selectScenario(scenario: ScreenScenario) {
        _uiState.value = _uiState.value.copy(
            currentScenario = scenario,
            draftText = scenario.defaultDraft,
            suggestions = scenario.suggestions,
            autoDispatchedText = null
        )
    }

    fun updateDraftText(newText: String) {
        _uiState.value = _uiState.value.copy(draftText = newText)
        recomputeDualLane(newText)
    }

    fun applySuggestion(suggestion: String) {
        _uiState.value = _uiState.value.copy(
            draftText = suggestion,
            autoDispatchedText = null
        )
    }

    private fun recomputeDualLane(text: String) {
        val baseSuggestions = _uiState.value.currentScenario?.suggestions ?: emptyList()
        val dynamicSuggestion = if (text.isNotBlank()) {
            "⚡ Enhanced: [Omni v4.2] ${text.trim()} — verified with zero latency."
        } else {
            "⚡ Quick prompt: optimize latency SLA pipeline."
        }
        _uiState.value = _uiState.value.copy(
            suggestions = listOf(dynamicSuggestion) + baseSuggestions.take(2),
            dualLaneLane1LatencyMs = 6 + (text.length % 5),
            dualLaneLane2LatencyMs = 85 + (text.length % 20)
        )
    }

    fun toggleAutoPilot() {
        val willBeActive = !_uiState.value.isAutoPilotActive
        _uiState.value = _uiState.value.copy(isAutoPilotActive = willBeActive)

        if (willBeActive) {
            startAutoPilotCountdown()
        } else {
            autoCountdownJob?.cancel()
            _uiState.value = _uiState.value.copy(autoCountdown = null)
        }
    }

    fun setAutoPilotTone(tone: AutoPilotTone) {
        _uiState.value = _uiState.value.copy(autoPilotTone = tone)
    }

    private fun startAutoPilotCountdown() {
        autoCountdownJob?.cancel()
        autoCountdownJob = viewModelScope.launch {
            for (sec in 3 downTo 1) {
                _uiState.value = _uiState.value.copy(autoCountdown = sec)
                delay(1000)
            }
            // Auto dispatch!
            val tone = _uiState.value.autoPilotTone
            val chosen = when (tone) {
                AutoPilotTone.RAW_HONEST -> "Let's be direct: the telemetry verifies the patch is locked in. Shipping now."
                AutoPilotTone.WITTY -> "Latency is down to 8ms. If it drops any lower it will start arriving yesterday."
                AutoPilotTone.DIRECT_ACTION -> "Acknowledged. Hotfix deployed to staging. Telemetry dashboard is 100% green."
                AutoPilotTone.FLIRTY -> "Handled before you even had to worry. You can thank me later."
            }
            _uiState.value = _uiState.value.copy(
                autoCountdown = null,
                draftText = chosen,
                autoDispatchedText = chosen
            )
        }
    }

    // Live Call Methods
    fun selectCaller(caller: LiveCallCaller) {
        _uiState.value = _uiState.value.copy(
            currentCaller = caller,
            callerSpeech = caller.initialSpeech
        )
        refreshCallCouncil(caller.initialSpeech, _uiState.value.activeCallTone)
    }

    fun setCallTone(tone: String) {
        _uiState.value = _uiState.value.copy(activeCallTone = tone)
        refreshCallCouncil(_uiState.value.callerSpeech, tone)
    }

    fun refreshCallCouncil(speech: String, tone: String) {
        val caller = _uiState.value.currentCaller ?: repository.callers.first()
        val strategist: String
        val empath: String
        val ghost: String
        val synthesis: String

        when (tone) {
            "Flirty" -> {
                strategist = "You know I always deliver before deadlines—especially when you're the one asking."
                empath = "Don't stress your pretty mind over the staging bugs, I've got your back completely."
                ghost = "Wouldn't leave you hanging on a critical push. Watch how fast I make this green."
                synthesis = "Don't stress for a second—I've got this completely handled. The patch is verified and I'll make sure it's green before you even have to ask."
            }
            "Casual" -> {
                strategist = "All good, spinning up the staging build right now. We'll have this wrapped up in ten."
                empath = "Totally get the crunch, man. Relax, we're on top of it and pinging you shortly."
                ghost = "Gotcha. Handled and green. Don't sweat it."
                synthesis = "Totally get the crunch. Build is spinning up now, we're on top of it and we'll have it locked in ten minutes."
            }
            "Funny" -> {
                strategist = "If this staging push fails, I'll personally apologize in interpretive dance. Deploying now!"
                empath = "Take a deep breath and grab coffee—no servers were harmed in the making of this hotfix."
                ghost = "Houston, we had a problem, but I already kicked it out the airlock. Staging is safe."
                synthesis = "Grab another coffee and relax—the bug has already been kicked out the airlock and staging is 100% green."
            }
            else -> {
                strategist = "Telemetry verified. Deploying staging immediately to safeguard SLA metrics and eliminate latency."
                empath = "I understand the priority on this rollout; our engineering leads are handling the fix right now."
                ghost = "Acknowledged. Staging push is queued. Telemetry logs will be delivered upon completion."
                synthesis = "Understood. The fix is verified and staging deployment is actively queued to safeguard your SLA metrics. Telemetry logs follow immediately."
            }
        }

        val record = LiveCallRecord(
            id = "rec-${System.currentTimeMillis()}",
            callerName = caller.name,
            callerRole = caller.role,
            speechToText = speech,
            activeTone = tone,
            strategistResponse = strategist,
            empathResponse = empath,
            ghostResponse = ghost,
            synthesisResponse = synthesis,
            hiddenIntent = "Caller requires concrete validation to de-escalate executive stakeholder pressure.",
            friction = "Anxiety over deadline slippage and SLA compliance.",
            leverage = "Presenting hard verification logs eliminates speculative pushback.",
            catchMeUp = listOf(
                "1. Live inquiry regarding ${caller.role} status for ${caller.name}.",
                "2. Persona tone calibrated to $tone with sub-10ms neural routing.",
                "3. Tri-Agent consensus recommends immediate verifiable confirmation."
            )
        )

        _uiState.value = _uiState.value.copy(callRecord = record)
    }

    // Studios Actions
    fun selectStudio(studioId: String) {
        _uiState.value = _uiState.value.copy(activeStudioId = studioId, studioOutputMessage = null)
    }

    fun triggerStudioAction(studioId: String) {
        val msg = when (studioId) {
            "motion" -> "OmniMotion: Rendered 9:16 vertical clip with Cyber Neon LUT & 140BPM beat markers to Vault!"
            "wave" -> "OmniWave: Generated 2 studio-grade phonk tracks with complete synchronized lyrics."
            "beat" -> "OmniBeat: Analyzed audio tempo (140 BPM) and created 3-scene dystopian storyboard."
            "imagine" -> "OmniImagine: Volumetric HDR grading & cyber tint applied to active asset."
            "analyzer" -> "OmniAnalyzer: Audit completed. Score: 96/100 (sub-800ms LCP on Edge CDN)."
            else -> "Studio action completed and synchronized across Omni pipeline."
        }
        _uiState.value = _uiState.value.copy(studioOutputMessage = msg)

        // Also add new asset to vault if applicable
        if (studioId == "motion" || studioId == "wave") {
            val newAsset = VaultAsset(
                id = "vault-${System.currentTimeMillis()}",
                name = if (studioId == "motion") "OmniMotion 9:16 Cyber Cut" else "OmniWave Phonk Master Track",
                type = if (studioId == "motion") "video" else "audio",
                duration = if (studioId == "motion") "00:30.0" else "02:30",
                bpm = 140,
                tags = listOf("Studio Generated", "Dark Misfit v4.2"),
                createdAt = "Just now"
            )
            repository.addVaultAsset(newAsset)
            _uiState.value = _uiState.value.copy(vaultAssets = listOf(newAsset) + _uiState.value.vaultAssets)
        }
    }
}
