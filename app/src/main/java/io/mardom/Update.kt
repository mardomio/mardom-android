package io.mardom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

@Composable
fun UpdateDialog() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!BuildConfig.DEBUG) {
            try {
                val client = HttpClient(Android)
                val url =
                    "https://raw.githubusercontent.com/avaarya/update-check-1790/main/mardom-android-version-code.txt"
                val text = client.get(url).bodyAsText()
                val version = text.trim().toInt()
                visible = version > BuildConfig.VERSION_CODE
            } catch (ex: Exception) {
                // ignore
            }
        }
    }

    if (!visible) return

    val context = LocalContext.current
    Dialog(onDismissRequest = { visible = false }) {
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "نسخه‌ی جدید با قابلیت‌های جدید منتشر شده است. از لینک‌های زیر می‌توانید به‌روز‌رسانی کنید.",
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        openWeb(
                            context,
                            "https://play.google.com/store/apps/details?id=io.mardom",
                        )
                    },
                ) {
                    Text("گوگل‌پلی")
                }

                Button(
                    onClick = {
                        openWeb(
                            context,
                            "https://mardom.io/app",
                        )
                    },
                ) {
                    Text("وب‌سایت mardom.io")
                }

                Button(
                    onClick = {
                        openWeb(
                            context,
                            "https://github.com/mardomio/mardom-android/releases/latest",
                        )
                    },
                ) {
                    Text("صفحه‌ی گیت‌هاب")
                }
            }
        }
    }
}