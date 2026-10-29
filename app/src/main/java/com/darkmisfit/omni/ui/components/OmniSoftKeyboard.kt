package com.darkmisfit.omni.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material.icons.filled.SpaceBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkmisfit.omni.theme.*

@Composable
fun OmniSoftKeyboard(
    onKeyPress: (String) -> Unit,
    onBackspace: () -> Unit,
    onEnter: () -> Unit,
    onSpace: () -> Unit,
    modifier: Modifier = Modifier
) {
    val row1 = listOf("Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P")
    val row2 = listOf("A", "S", "D", "F", "G", "H", "J", "K", "L")
    val row3 = listOf("Z", "X", "C", "V", "B", "N", "M")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CanvasRootBlack)
            .padding(horizontal = 6.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            row1.forEach { char ->
                SoftKey(
                    label = char,
                    onClick = { onKeyPress(char) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Row 2
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            row2.forEach { char ->
                SoftKey(
                    label = char,
                    onClick = { onKeyPress(char) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Row 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Shift
            Box(
                modifier = Modifier
                    .weight(1.3f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianPurple)
                    .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
                    .clickable { /* Toggle shift */ }
                    .testTag("key_shift"),
                contentAlignment = Alignment.Center
            ) {
                Text("⇧", color = TextPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            row3.forEach { char ->
                SoftKey(
                    label = char,
                    onClick = { onKeyPress(char) },
                    modifier = Modifier.weight(1f)
                )
            }

            // Backspace
            Box(
                modifier = Modifier
                    .weight(1.3f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianPurple)
                    .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
                    .clickable { onBackspace() }
                    .testTag("key_backspace"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Backspace,
                    contentDescription = "Backspace",
                    tint = TextPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Row 4: Space & Action Keys
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 123 Switcher
            Box(
                modifier = Modifier
                    .weight(1.2f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianPurple)
                    .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
                    .clickable { onKeyPress("?") }
                    .testTag("key_123"),
                contentAlignment = Alignment.Center
            ) {
                Text("?123", color = BabyBlue, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }

            // Comma
            SoftKey(label = ",", onClick = { onKeyPress(",") }, modifier = Modifier.weight(0.9f))

            // Spacebar with volumetric Purplish-Blue halo
            Box(
                modifier = Modifier
                    .weight(4f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianPurple)
                    .border(width = 1.dp, color = PurplishBlue, shape = RoundedCornerShape(8.dp))
                    .clickable { onSpace() }
                    .testTag("key_spacebar"),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "OMNI SHADOW",
                    color = TextPrimary.copy(alpha = 0.6f),
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Period
            SoftKey(label = ".", onClick = { onKeyPress(".") }, modifier = Modifier.weight(0.9f))

            // Enter / Dispatch Key in signature Purplish-Blue
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .height(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PurplishBlue)
                    .border(width = 1.dp, color = BabyBlue.copy(alpha = 0.5f), shape = RoundedCornerShape(8.dp))
                    .clickable { onEnter() }
                    .testTag("key_enter"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardReturn,
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun SoftKey(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(42.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianPurple)
            .border(width = 1.dp, color = BorderPurple, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .testTag("key_$label"),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = TextPrimary,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}
