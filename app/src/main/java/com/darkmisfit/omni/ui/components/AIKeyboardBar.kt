package com.darkmisfit.omni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

@Composable
fun AIKeyboardBar(
    suggestions: List<String>,
    onSelectSuggestion: (String) -> Unit,
    isAutoPilotActive: Boolean,
    onToggleAutoPilot: () -> Unit,
    autoCountdown: Int?,
    lane1Ms: Int,
    lane2Ms: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(ObsidianPurple)
            .border(width = 1.dp, color = BorderPurple)
            .padding(vertical = 8.dp, horizontal = 10.dp)
            .testTag("ai_keyboard_bar")
    ) {
        // Status & Telemetry Strip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                HoloOrb(size = 20.dp, isProcessing = isAutoPilotActive)
                Text(
                    text = "OMNI DUAL-LANE AI",
                    color = BabyBlue,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "[L1: ${lane1Ms}ms | L2: ${lane2Ms}ms]",
                    color = BabyBlue,
                    fontSize = 10.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )

                // Auto-Pilot Toggle Pill
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isAutoPilotActive) PurplishBlue else CanvasRootBlack)
                        .border(
                            width = 1.dp,
                            color = if (isAutoPilotActive) BabyBlue else BorderPurple,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { onToggleAutoPilot() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .testTag("toggle_autopilot_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (autoCountdown != null) Icons.Default.Schedule else Icons.Default.Bolt,
                            contentDescription = "Auto Pilot",
                            tint = if (isAutoPilotActive) Color.White else BabyBlue,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = if (autoCountdown != null) "DISPATCH IN ${autoCountdown}S" else if (isAutoPilotActive) "AUTO ON" else "AUTO PILOT",
                            color = if (isAutoPilotActive) Color.White else BabyBlue,
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Horizontal Suggestions Pills
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            suggestions.forEachIndexed { index, suggestion ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = if (index == 0) PurplishBlue else BorderPurple, shape = RoundedCornerShape(10.dp))
                        .clickable { onSelectSuggestion(suggestion) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .testTag("suggestion_pill_$index")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        if (index == 0) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Recommended",
                                tint = BabyBlue,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Text(
                            text = suggestion,
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            maxLines = 1
                        )
                    }
                }
            }
        }
    }
}
