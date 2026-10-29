package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CenterFocusStrong
import androidx.compose.material.icons.filled.Visibility
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
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.ui.components.HoloOrb
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun VisionScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
    var isScanning by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasRootBlack)
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top HUD Header
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
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    HoloOrb(size = 32.dp, isProcessing = isScanning)
                    Column {
                        Text(
                            text = "REAL-TIME SCREEN COGNITION HUD",
                            color = BabyBlue,
                            fontSize = 11.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "OCR Intent Extraction & Visual Telemetry",
                            color = TextMuted,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text("LATENCY: 12ms", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Viewfinder Frame (with real-time safe-zone hazard outlines in Baby Blue #00FFFF)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasRootBlack),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BabyBlue))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // Crosshairs & Grid Lines
                Icon(
                    imageVector = Icons.Default.CenterFocusStrong,
                    contentDescription = "Crosshair",
                    tint = BabyBlue.copy(alpha = 0.5f),
                    modifier = Modifier.size(90.dp)
                )

                // Safe-Zone Telemetry Badges
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(ObsidianPurple)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "[SAFE-ZONE: 9:16 VERTICAL OK]",
                        color = BabyBlue,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(ObsidianPurple)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "CONFIDENCE: 99.4%",
                        color = BabyBlue,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Center OCR extracted text pill
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(ObsidianPurple)
                        .border(width = 1.dp, color = PurplishBlue, shape = RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "DETECTED CONVERSATION INTENT",
                            color = BabyBlue,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "\"${uiState.currentScenario?.defaultDraft ?: "Awaiting screen capture..."}\"",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = {
                    isScanning = !isScanning
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("scan_screen_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = PurplishBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(if (isScanning) "STOP VISION SCAN" else "ANALYZE SCREEN NOW", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = {
                    // Quick enhance
                    viewModel.updateDraftText("Verified screen analysis: zero SLA violation detected.")
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("extract_intent_btn"),
                colors = ButtonDefaults.buttonColors(containerColor = ObsidianPurple),
                shape = RoundedCornerShape(10.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
            ) {
                Icon(Icons.Default.Visibility, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("BIND TO DRAFT", color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Scene Scenarios Selector
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "ACTIVE SCENARIO FEEDS",
                    color = BabyBlue,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )

                uiState.scenarios.forEach { scenario ->
                    val isSelected = uiState.currentScenario?.id == scenario.id
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) PurplishBlue.copy(alpha = 0.3f) else CanvasRootBlack)
                            .border(width = 1.dp, color = if (isSelected) PurplishBlue else BorderPurple, shape = RoundedCornerShape(8.dp))
                            .clickable { viewModel.selectScenario(scenario) }
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(scenario.name, color = TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                Text("App: ${scenario.appName} • Recipient: ${scenario.recipient}", color = TextMuted, fontSize = 9.sp, fontFamily = FontFamily.Monospace)
                            }
                            if (isSelected) {
                                Text("ACTIVE", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
