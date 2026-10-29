package com.darkmisfit.omni.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.darkmisfit.omni.model.NavigationTab
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.ui.components.HoloOrb
import com.darkmisfit.omni.ui.screens.*
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun OmniApp(
    viewModel: OmniViewModel,
    uiState: OmniUiState
) {
    // Handle back button on secondary tabs
    if (uiState.currentTab != NavigationTab.KEYBOARD) {
        BackHandler {
            viewModel.selectTab(NavigationTab.KEYBOARD)
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasRootBlack),
        containerColor = CanvasRootBlack,
        topBar = {
            OmniTopBar(
                currentTab = uiState.currentTab,
                latencyMs = uiState.telemetry.latencyMs
            )
        },
        bottomBar = {
            OmniBottomNav(
                currentTab = uiState.currentTab,
                onSelectTab = { viewModel.selectTab(it) }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CanvasRootBlack)
        ) {
            when (uiState.currentTab) {
                NavigationTab.KEYBOARD -> KeyboardScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.VISION -> VisionScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.CALL -> LiveCallScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.STUDIOS -> StudiosScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.TELEMETRY -> TelemetryScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.VAULT -> VaultScreen(viewModel = viewModel, uiState = uiState)
                NavigationTab.IDENTITY -> IdentityScreen(viewModel = viewModel, uiState = uiState)
            }
        }
    }
}

@Composable
private fun OmniTopBar(
    currentTab: NavigationTab,
    latencyMs: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = CanvasRootBlack,
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HoloOrb(size = 28.dp, isProcessing = true)
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(
                            text = "OMNIAI",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "• NEXUS",
                            color = BabyBlue,
                            fontSize = 13.sp,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = currentTab.label.uppercase(),
                        color = BabyBlue,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(ObsidianPurple)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text("${latencyMs}ms", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(PurplishBlue)
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text("GEMINI 3.8", color = Color.White, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun OmniBottomNav(
    currentTab: NavigationTab,
    onSelectTab: (NavigationTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        color = ObsidianPurple,
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                val icon = when (tab) {
                    NavigationTab.KEYBOARD -> Icons.Default.Keyboard
                    NavigationTab.VISION -> Icons.Default.CenterFocusStrong
                    NavigationTab.CALL -> Icons.Default.Phone
                    NavigationTab.STUDIOS -> Icons.Default.AutoAwesome
                    NavigationTab.TELEMETRY -> Icons.Default.Speed
                    NavigationTab.VAULT -> Icons.Default.Lock
                    NavigationTab.IDENTITY -> Icons.Default.Fingerprint
                }

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onSelectTab(tab) }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                        .testTag("nav_${tab.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = tab.label,
                        tint = if (isSelected) BabyBlue else TextMuted,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = when (tab) {
                            NavigationTab.KEYBOARD -> "Keys"
                            NavigationTab.VISION -> "Vision"
                            NavigationTab.CALL -> "Call"
                            NavigationTab.STUDIOS -> "Studios"
                            NavigationTab.TELEMETRY -> "Stats"
                            NavigationTab.VAULT -> "Vault"
                            NavigationTab.IDENTITY -> "ID"
                        },
                        color = if (isSelected) BabyBlue else TextMuted,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .height(2.dp)
                                .background(PurplishBlue)
                        )
                    }
                }
            }
        }
    }
}
