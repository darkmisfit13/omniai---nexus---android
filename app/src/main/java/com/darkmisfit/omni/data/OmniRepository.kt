package com.darkmisfit.omni.data

import com.darkmisfit.omni.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OmniRepository {

    val scenarios = listOf(
        ScreenScenario(
            id = "scenario-whatsapp-urgent",
            name = "WhatsApp: Urgent Production Fix",
            appName = "WhatsApp",
            recipient = "Sarah (Lead Architect)",
            statusText = "Online • Audio Call ending...",
            defaultDraft = "Checking the auth latency drop, patching right now.",
            screenType = "messaging",
            suggestions = listOf(
                "SLA verified: deploying zero-downtime hotfix in 2 min.",
                "Latency spikes isolated to auth edge cache. Re-routing.",
                "Rollback completed. Telemetry back to 8ms baseline."
            )
        ),
        ScreenScenario(
            id = "scenario-instagram-collab",
            name = "Instagram DM: Studio Collab",
            appName = "Instagram",
            recipient = "@neon_director",
            statusText = "Active 5m ago",
            defaultDraft = "Loved the 9:16 vertical render from OmniMotion.",
            screenType = "social",
            suggestions = listOf(
                "Let's sync timelines: Freebeat 140BPM drop at 00:15.",
                "Exported 4K 60fps master with Cyberpunk LUT to your vault.",
                "Ready to run the multi-model quorum on the album promo."
            )
        ),
        ScreenScenario(
            id = "scenario-slack-exec",
            name = "Slack: Executive Review",
            appName = "Slack",
            recipient = "#exec-board-war-room",
            statusText = "14 members typing...",
            defaultDraft = "OmniAI v4.2 benchmark telemetry report ready.",
            screenType = "work",
            suggestions = listOf(
                "Dual-lane inference: 8ms heuristic lane, sub-100ms Gemini core.",
                "All 6 LLM cascade backups active with 99.98% quorum reliability.",
                "Customer token safety confirmed. Enterprise isolation intact."
            )
        ),
        ScreenScenario(
            id = "scenario-dating-opener",
            name = "Tinder: Smooth Opener",
            appName = "Tinder",
            recipient = "Aria • 3 miles away",
            statusText = "Listens to Synthwave & Cyberpunk",
            defaultDraft = "Hey Aria, saw your playlist.",
            screenType = "dating",
            suggestions = listOf(
                "Your playlist has top-tier taste. Midnight city drive vibes or neon coding?",
                "Bet you 10 credits our music tastes overlap 96% on OmniWave.",
                "Tell me the story behind that rooftop photo in the rain."
            )
        )
    )

    val callers = listOf(
        LiveCallCaller(
            id = "caller-alex",
            name = "Alex Rivera",
            role = "VP Engineering",
            context = "Staging deployment status and auth latency SLA verification.",
            initialSpeech = "Checking in on the status for the staging push. What is the current telemetry?"
        ),
        LiveCallCaller(
            id = "caller-elena",
            name = "Elena Rostova",
            role = "Studio Audio Lead",
            context = "OmniWave Suno-style track master review & album drop.",
            initialSpeech = "The 140 BPM phonk master needs heavy sub-bass at the drop. Did the quorum approve Track A?"
        ),
        LiveCallCaller(
            id = "caller-marcus",
            name = "Marcus Vance",
            role = "Lead Partner, CyberVentures",
            context = "Q4 runway and autonomous mobile companion traction.",
            initialSpeech = "We're presenting the companion interface to LP partners tomorrow. Can you demo the real-time screen vision?"
        )
    )

    val studios = listOf(
        StudioItem(
            id = "motion",
            name = "OmniMotion",
            tag = "CapCut Killer",
            category = "Video & Motion",
            description = "Multi-track vertical 9:16 editor with beat-synced cuts and cyber LUTs.",
            actionLabel = "Launch Timeline"
        ),
        StudioItem(
            id = "wave",
            name = "OmniWave",
            tag = "Suno Engine",
            category = "Music & Audio",
            description = "Generate 2 distinct studio-grade song variations with synchronized lyrics.",
            actionLabel = "Generate Tracks"
        ),
        StudioItem(
            id = "beat",
            name = "OmniBeat",
            tag = "Freebeat AI",
            category = "Music & Video",
            description = "Intelligent music video analyzer, beat drop storyboarder, and tempo matcher.",
            actionLabel = "Analyze Beats"
        ),
        StudioItem(
            id = "imagine",
            name = "OmniImagine",
            tag = "Visual Director",
            category = "Photo & Art",
            description = "Autonomous AI photo director with neural volumetric lighting and HDR grading.",
            actionLabel = "Auto-Edit Photo"
        ),
        StudioItem(
            id = "analyzer",
            name = "OmniAnalyzer",
            tag = "Audit Core",
            category = "Diagnostics",
            description = "Real-time social, SEO, UX, and security health scorecard with actionable audits.",
            actionLabel = "Run Audit"
        ),
        StudioItem(
            id = "code",
            name = "OmniCode",
            tag = "Kotlin & TS",
            category = "Engineering",
            description = "Multi-language code generator, AST optimizer, and reactive state architect.",
            actionLabel = "Open Studio"
        )
    )

    val initialCascade = listOf(
        LLMNode("llm-1", "Gemini 3.8 Flash (Omni Core)", "Google Gemini", "gemini-3.8-flash", "primary", true, 1, "connected", 110),
        LLMNode("llm-2", "Gemini 3.1 Flash-Lite (Failover)", "Google Gemini", "gemini-3.1-flash-lite", "secondary", true, 2, "connected", 65),
        LLMNode("llm-3", "Claude 3.5 Sonnet (Architecture)", "Anthropic", "claude-3-5-sonnet", "fallback", true, 3, "connected", 195),
        LLMNode("llm-4", "GPT-4o Omni (Generalist Guard)", "OpenAI", "gpt-4o", "fallback", true, 4, "connected", 210),
        LLMNode("llm-5", "DeepSeek R1 (Chain-of-Thought)", "DeepSeek", "deepseek-r1", "fallback", true, 5, "connected", 380)
    )

    private val _vaultAssets = MutableStateFlow(
        listOf(
            VaultAsset(
                id = "vault-1",
                name = "Cyberpunk Phonk Anthem (140 BPM)",
                type = "audio",
                bpm = 140,
                duration = "02:14",
                lyrics = "[Intro]\nBass reverberates in the neon fog...\n[Chorus]\nShadow companion in the night,\nFast lanes burning violet light.",
                tags = listOf("Phonk", "140 BPM", "Stereo Master"),
                createdAt = "2026-10-26T21:00:00Z"
            ),
            VaultAsset(
                id = "vault-2",
                name = "OmniMotion Vertical Promo 9:16",
                type = "video",
                duration = "00:30.0",
                tags = listOf("9:16", "Cyber Neon LUT", "Beat Matched"),
                createdAt = "2026-10-26T20:30:00Z"
            ),
            VaultAsset(
                id = "vault-3",
                name = "Neural HUD Glassmorphism Preset",
                type = "code",
                tags = listOf("Jetpack Compose", "M3", "Cyberpunk"),
                createdAt = "2026-10-26T18:15:00Z"
            )
        )
    )
    val vaultAssets: StateFlow<List<VaultAsset>> = _vaultAssets.asStateFlow()

    fun addVaultAsset(asset: VaultAsset) {
        _vaultAssets.value = listOf(asset) + _vaultAssets.value
    }
}
