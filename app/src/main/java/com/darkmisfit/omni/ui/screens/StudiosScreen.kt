package com.darkmisfit.omni.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.darkmisfit.omni.model.StudioItem
import com.darkmisfit.omni.theme.*
import com.darkmisfit.omni.viewmodel.OmniUiState
import com.darkmisfit.omni.viewmodel.OmniViewModel

@Composable
fun StudiosScreen(
    viewModel: OmniViewModel,
    uiState: OmniUiState,
    modifier: Modifier = Modifier
) {
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
                        text = "OMNI CREATIVE STUDIOS SUITE",
                        color = BabyBlue,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "14 Autonomous Creation Engines & Master Renderers",
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
                    Text("14 ACTIVE", color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Output Status Banner
        uiState.studioOutputMessage?.let { msg ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(PurplishBlue.copy(alpha = 0.25f))
                    .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(10.dp))
                    .padding(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null, tint = BabyBlue, modifier = Modifier.size(16.dp))
                    Text(msg, color = TextPrimary, fontSize = 11.sp, fontFamily = FontFamily.Monospace)
                }
            }
        }

        // Active Studio Runner Hero
        val activeStudio = uiState.studios.find { it.id == uiState.activeStudioId } ?: uiState.studios.first()
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianPurple),
            border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(PurplishBlue))
        ) {
            Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(activeStudio.name, color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(CanvasRootBlack)
                                .border(width = 1.dp, color = BabyBlue, shape = RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(activeStudio.tag, color = BabyBlue, fontSize = 9.sp, fontFamily = FontFamily.Monospace, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(activeStudio.category, color = TextMuted, fontSize = 10.sp, fontFamily = FontFamily.Monospace)
                }

                Text(activeStudio.description, color = TextPrimary, fontSize = 12.sp, lineHeight = 17.sp)

                Button(
                    onClick = { viewModel.triggerStudioAction(activeStudio.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("studio_action_btn"),
                    colors = ButtonDefaults.buttonColors(containerColor = PurplishBlue),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Default.Bolt, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(activeStudio.actionLabel, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Studio Grid
        Text(
            text = "SELECT STUDIO WORKSPACE",
            color = BabyBlue,
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.studios) { studio ->
                val isSelected = studio.id == uiState.activeStudioId
                StudioGridCard(
                    studio = studio,
                    isSelected = isSelected,
                    onClick = { viewModel.selectStudio(studio.id) }
                )
            }
        }
    }
}

@Composable
private fun StudioGridCard(
    studio: StudioItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("studio_card_${studio.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) ObsidianPurple else CanvasRootBlack),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(if (isSelected) PurplishBlue else BorderPurple)
        )
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = studio.name,
                    color = if (isSelected) Color.White else TextPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.Launch,
                    contentDescription = null,
                    tint = if (isSelected) BabyBlue else TextMuted,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = studio.tag,
                color = BabyBlue,
                fontSize = 9.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = studio.description,
                color = TextMuted,
                fontSize = 10.sp,
                lineHeight = 14.sp,
                maxLines = 2
            )
        }
    }
}
