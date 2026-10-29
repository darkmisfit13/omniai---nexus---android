package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.ui.components.HoloOrb
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun LiveCallScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
    val tones = listOf("Professional", "Casual", "Flirty", "Funny")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasRootBlack)
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 1. Caller Switcher Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.callers.forEach { caller ->
                val isSelected = uiState.currentCaller?.id == caller.id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isSelected) PurplishBlue else ObsidianPurple)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) BabyBlue else BorderPurple,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clickable { viewModel.selectCaller(caller) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .testTag("caller_tab_${caller.id}")
                ) {
                    Column {
                        Text(
                            text = caller.name,
                            color = if (isSelected) Color.White else TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = caller.role,
                            color = BabyBlue,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

        // 2. Live Call Active HUD Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    HoloOrb(size = 36.dp, isProcessing = true)
                    Column {
                        Text(
                            text = "LIVE AUDIO CALL ACTIVE",
                            color = BabyBlue,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${uiState.currentCaller?.name} • ${uiState.currentCaller?.role}",
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("02:45", color = BabyBlue, fontSize = 11.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }

        // 3. Speech-to-Text Transcription Box
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REAL-TIME SPEECH RECONSTRUCTION",
                        color = BabyBlue,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text("STREAMING", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(10.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "\"${uiState.callerSpeech}\"",
                        color = TextPrimary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // 4. Tone Shifter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("TONE:", color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
            tones.forEach { tone ->
                val isSelected = uiState.activeCallTone == tone
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) PurplishBlue else ObsidianPurple)
                        .border(width = 1.dp, color = if (isSelected) BabyBlue else BorderPurple, shape = RoundedCornerShape(8.dp))
                        .clickable { viewModel.setCallTone(tone) }
                        .padding(vertical = 6.dp)
                        .testTag("call_tone_$tone"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tone,
                        color = if (isSelected) Color.White else TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // 5. Synthesis Card (One-Touch Recommendation)
        uiState.callRecord?.let { record ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("synthesis_card"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(PurplishBlue))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "⚡ OPTIMAL HYBRID SYNTHESIS",
                            color = BabyBlue,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(PurplishBlue)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("QUORUM 98%", color = Color.White, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(
                        text = record.synthesisResponse,
                        color = Color.White,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 6. Tri-Agent Deliberation Cards (THE STRATEGIST, THE EMPATH, THE GHOST)
            Text(
                text = "TRI-AGENT COUNCIL PERSPECTIVES",
                color = BabyBlue,
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )

            // Strategist
            AgentCard(
                agentName = "THE STRATEGIST",
                roleDetail = "SLA Protection & Contractual Leverage",
                content = record.strategistResponse,
                testTag = "pill_strategist"
            )

            // Empath
            AgentCard(
                agentName = "THE EMPATH",
                roleDetail = "De-escalation & Tension Softening",
                content = record.empathResponse,
                testTag = "pill_empath"
            )

            // Ghost
            AgentCard(
                agentName = "THE GHOST",
                roleDetail = "Cloned Shadow Self & Engineering Cadence",
                content = record.ghostResponse,
                testTag = "pill_ghost"
            )

            // 7. Subtext Analysis
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "NEURAL SUBTEXT ANALYSIS",
                        color = BabyBlue,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    SubtextRow("Hidden Intent", record.hiddenIntent)
                    SubtextRow("Friction Vector", record.friction)
                    SubtextRow("Conversational Leverage", record.leverage)
                }
            }

            // 8. Executive Catch-Me-Up
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "EXECUTIVE BRIEFING (CATCH-ME-UP)",
                        color = BabyBlue,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    record.catchMeUp.forEach { point ->
                        Text(
                            text = point,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AgentCard(
    agentName: String,
    roleDetail: String,
    content: String,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BabyBlue.copy(alpha = 0.5f), shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = agentName,
                        color = BabyBlue,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(roleDetail, color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
            }
            Text(content, color = TextPrimary, fontSize = 12.sp, lineHeight = 17.sp)
        }
    }
}

@Composable
private fun SubtextRow(title: String, detail: String) {
    Column {
        Text(title, color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
        Text(detail, color = TextPrimary, fontSize = 11.sp)
    }
}
