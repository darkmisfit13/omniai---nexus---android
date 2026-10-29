package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkmisfit.omni.model.AutoPilotTone
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.ui.components.AIKeyboardBar
import com.darkmisfit.omni.ui.components.OmniSoftKeyboard
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun KeyboardScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasRootBlack)
    ) {
        // 1. Scenario Selector Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ObsidianPurple)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            uiState.scenarios.forEach { scenario ->
                val isSelected = uiState.currentScenario?.id == scenario.id
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) PurplishBlue else CanvasRootBlack)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) BabyBlue else BorderPurple,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { viewModel.selectScenario(scenario) }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .testTag("scenario_tab_${scenario.id}")
                ) {
                    Text(
                        text = scenario.appName,
                        color = if (isSelected) Color.White else TextPrimary,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }

        // 2. Simulated App Conversation Viewport
        uiState.currentScenario?.let { scenario ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    // Header of simulated app
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(10.dp))
                            .background(CanvasRootBlack)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(PurplishBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Chat, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                            }
                            Column {
                                Text(scenario.recipient, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Text(scenario.statusText, color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                            }
                        }
                        Icon(Icons.Default.Phone, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(16.dp))
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Conversation Bubble
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CanvasRootBlack)
                            .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = "Incoming context: What's the latest milestone update?",
                            color = TextPrimary,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Auto Dispatched Notification Banner
                    uiState.autoDispatchedText?.let { dispatched ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(PurplishBlue.copy(alpha = 0.2f))
                                .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(8.dp))
                                .padding(8.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "AUTOPILOT DISPATCHED: $dispatched",
                                    color = BabyBlue,
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                    }

                    // Draft Input Field
                    OutlinedTextField(
                        value = uiState.draftText,
                        onValueChange = { viewModel.updateDraftText(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("keyboard_draft_input"),
                        placeholder = { Text("Draft response...", color = TextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PurplishBlue,
                            unfocusedBorderColor = BorderPurple,
                            focusedContainerColor = CanvasRootBlack,
                            unfocusedContainerColor = CanvasRootBlack,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }
        }

        // 3. AutoPilot Tone Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CanvasRootBlack)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Tune, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(12.dp))
            AutoPilotTone.entries.forEach { tone ->
                val isSelected = uiState.autoPilotTone == tone
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isSelected) PurplishBlue else ObsidianPurple)
                        .border(width = 1.dp, color = if (isSelected) BabyBlue else BorderPurple, shape = RoundedCornerShape(6.dp))
                        .clickable { viewModel.setAutoPilotTone(tone) }
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                        .testTag("tone_${tone.name}")
                ) {
                    Text(
                        text = tone.label,
                        color = if (isSelected) Color.White else TextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // 4. AI Keyboard Bar
        AIKeyboardBar(
            suggestions = uiState.suggestions,
            onSelectSuggestion = { viewModel.applySuggestion(it) },
            isAutoPilotActive = uiState.isAutoPilotActive,
            onToggleAutoPilot = { viewModel.toggleAutoPilot() },
            autoCountdown = uiState.autoCountdown,
            lane1Ms = uiState.dualLaneLane1LatencyMs,
            lane2Ms = uiState.dualLaneLane2LatencyMs
        )

        // 5. Omni Soft Keyboard
        OmniSoftKeyboard(
            onKeyPress = { viewModel.updateDraftText(uiState.draftText + it) },
            onBackspace = {
                if (uiState.draftText.isNotEmpty()) {
                    viewModel.updateDraftText(uiState.draftText.dropLast(1))
                }
            },
            onEnter = {
                // Submit draft
                viewModel.updateDraftText("")
            },
            onSpace = { viewModel.updateDraftText(uiState.draftText + " ") }
        )
    }
}
