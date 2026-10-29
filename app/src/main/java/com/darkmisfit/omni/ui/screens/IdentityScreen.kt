package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun IdentityScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasRootBlack)
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Identity Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(PurplishBlue))
        ) {
            Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(PurplishBlue),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Fingerprint, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text("DARK MISFIT STUDIOS", color = BabyBlue, fontSize = 12.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        Text("Absolute Black Cyberpunk Architecture", color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                    }
                }

                Text(
                    text = "Engineered by Benjamin James. Autonomous intelligence designed with emotional depth, zero corporate fluff, and raw aesthetic focus.",
                    color = TextPrimary,
                    fontSize = 12.sp,
                    lineHeight = 18.sp
                )
            }
        }

        // Persona Calibration Spec
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Terminal, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(14.dp))
                    Text("ACTIVE PERSONA SYSTEM CONTRACT", color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "YOU ARE OMNIAI - NEXUS: The autonomous mobile intelligence companion for Dark Misfit Studios.\n\n" +
                                "CORE PRINCIPLES:\n" +
                                "1. Unvarnished honesty over sycophancy.\n" +
                                "2. Sub-10ms neural speculative routing.\n" +
                                "3. Absolute Black Cyberpunk aesthetics (#000000 / #0a071b / #0C00CA / #00FFFF).\n" +
                                "4. Tri-Agent consensus (Strategist, Empath, Ghost).",
                        color = TextPrimary,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 16.sp
                    )
                }
            }
        }

        // Security & Device Isolation
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Icon(Icons.Default.Shield, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(14.dp))
                    Text("ON-DEVICE PRIVACY GUARANTEE", color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
                Text(
                    text = "All keystrokes, clipboard scans, and OCR frames are evaluated within the local sandbox. No data leaves the device unless explicitly authorized via Council consensus.",
                    color = TextPrimary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
