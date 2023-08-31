package io.mardom.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.mardom.ui.theme.AppColors

@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        modifier = modifier,
    ) {
        Text(text = "انقلاب", color = AppColors.FlagGreen, fontWeight = FontWeight.Medium)
        Text(text = "مردمی", color = AppColors.FlagGold, fontWeight = FontWeight.Medium)
        Text(text = "ایران", color = AppColors.FlagRed, fontWeight = FontWeight.Medium)
    }
}