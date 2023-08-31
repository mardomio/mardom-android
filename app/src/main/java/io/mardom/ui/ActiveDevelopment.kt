package io.mardom.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

@Composable
fun ActiveDevelopment(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .alpha(0.75f)
    ) {
        Text(text = "👩‍💻👨‍💻", style = typography.titleLarge)
        Text(text = "در حال توسعه فعال،", style = typography.bodySmall)
        Text(text = "برنامه نویسان مشغول کارند.", style = typography.bodySmall)
    }
}