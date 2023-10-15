package io.mardom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ShareAppCard() {
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(
                top = 10.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 4.dp,
            ),
        ) {
            Text("اپلیکیشن را با دوستان خود به اشتراک بگذارید.")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
            ) {
                IconButton(
                    onClick = {
                        shareAppLink(
                            context,
                            "https://play.google.com/store/apps/details?id=io.mardom",
                        )
                    },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.google_play),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
                IconButton(
                    onClick = {
                        shareAppLink(
                            context,
                            "https://github.com/mardomio/mardom-android/releases/latest",
                        )
                    },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.github),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
                IconButton(
                    onClick = {
                        shareAppLink(
                            context,
                            "https://mardom.io/app",
                        )
                    },
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.web),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}