package io.mardom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch

@Composable
fun NotificationsCard() {
    var result by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        result = check()
    }

    val scope = rememberCoroutineScope()

    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (result != null) {
                        result = null
                        scope.launch { result = check() }
                    }
                }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            when (result) {
                null -> {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp),
                    )
                    Text("در حال بررسی سیستم اطلاع‌رسانی ...")
                }

                true -> {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Text("ارتباط با سیستم اطلاع‌رسانی با موفقیت انجام شد. شما از اخبارها و اطلاعیه‌های مهم مطلع خواهید شد.")
                }

                false -> {
                    Icon(
                        Icons.Filled.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    Text("ارتباط با سیستم اطلاع‌رسانی برقرار نشد. لطفا این موضوع را در گیت‌هاب ما گزارش دهید. ما به طور مداوم در حال بهبود عملکرد این سیستم هستیم تا حتی در مواقع قطعی کامل اینترنت نیز کار کند.")
                }
            }
        }
    }
}

suspend fun check(): Boolean {
    return try {
        val client = HttpClient(Android)
        val url = "https://cloudflare.com/cdn-cgi/trace"
        val text = client.get(url).bodyAsText()
        text.contains("h=cloudflare.com")
    } catch (ex: Exception) {
        false
    }
}