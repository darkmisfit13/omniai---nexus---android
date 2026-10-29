package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.darkmisfit.omni.model.VaultAsset
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun VaultScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    val filters = listOf("all", "audio", "video", "code")

    val filteredAssets = remember(uiState.vaultAssets, selectedFilter) {
        if (selectedFilter == "all") uiState.vaultAssets else uiState.vaultAssets.filter { it.type == selectedFilter }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CanvasRootBlack)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Top Header
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
                Column {
                    Text(
                        text = "GLOBAL SECURE ASSET VAULT",
                        color = BabyBlue,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Unified Studio Master Assets & Audio Tracks",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Text("${uiState.vaultAssets.size} STORED", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Filter Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            filters.forEach { filter ->
                val isSelected = selectedFilter == filter
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) PurplishBlue else ObsidianPurple)
                        .border(width = 1.dp, color = if (isSelected) BabyBlue else BorderPurple, shape = RoundedCornerShape(8.dp))
                        .clickable { selectedFilter = filter }
                        .padding(vertical = 6.dp)
                        .testTag("filter_$filter"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = filter.uppercase(),
                        color = if (isSelected) Color.White else TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }
        }

        // Vault Asset List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredAssets) { asset ->
                VaultAssetCard(asset = asset)
            }
        }
    }
}

@Composable
private fun VaultAssetCard(asset: VaultAsset) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(BorderPurple))
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val icon = when (asset.type) {
                        "audio" -> Icons.Default.MusicNote
                        "video" -> Icons.Default.Videocam
                        else -> Icons.Default.Code
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CanvasRootBlack)
                            .border(width = 1.dp, color = PurplishBlue, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(16.dp))
                    }
                    Column {
                        Text(asset.name, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${asset.type.uppercase()} • ${asset.createdAt}",
                            color = TextMuted,
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                asset.duration?.let { dur ->
                    Text(dur, color = BabyBlue, fontSize = 10.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }

            asset.lyrics?.let { lyrics ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CanvasRootBlack)
                        .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(lyrics, color = TextPrimary, fontSize = 10.sp, fontFamily = FontFamily.Monospace, maxLines = 3)
                }
            }

            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                asset.tags.forEach { tag ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(CanvasRootBlack)
                            .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(tag, color = BabyBlue, fontSize = 8.5.sp, fontFamily = FontFamily.Monospace)
                    }
                }
            }
        }
    }
}
