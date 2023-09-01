package io.mardom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.mardom.ui.ActiveDevelopment
import io.mardom.ui.AppTitle
import io.mardom.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            AppTheme {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "انقلاب اسلامی ایران",
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough,
                        modifier = Modifier
                            .alpha(0.5f)
                            .align(Alignment.CenterHorizontally),
                    )
                    AppTitle(modifier = Modifier.align(Alignment.CenterHorizontally))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "نه به جمهوری اسلامی/دیکتاتوری",
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "هر بار نصب این اپلیکیشن به معنای یک رأی منفی به حکومت دیکتاتوری است. اپلیکیشن را با دوستان خود به اشتراک بگذارید تا آن‌ها هم رأی خود را ثبت کنند.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = {
                            shareAppLink(
                                context,
                                "https://play.google.com/store/apps/details?id=io.mardom",
                            )
                        },
                    ) {
                        Text("اشتراک‌گذاری از طریق گوگل‌پلی")
                    }
                    OutlinedButton(
                        onClick = {
                            shareAppLink(
                                context,
                                "https://mardom.io/app",
                            )
                        },
                    ) {
                        Text("اشتراک‌گذاری از طریق وب‌سایت mardom.io")
                    }
                    OutlinedButton(
                        onClick = {
                            shareAppLink(
                                context,
                                "https://github.com/mardomio/mardom-android/releases/latest",
                            )
                        },
                    ) {
                        Text("اشتراک‌گذاری از طریق مخزن گیت‌هاب")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "برای حفظ امنیت، اپلیکیشن را تنها از گوگل‌پلی، وب‌سایت مردم، یا مخزن گیت‌هاب دانلود کنید. ما نمی‌توانیم امنیت منابع دیگر را تضمین کنیم. دانلود و نصب از منابع نامعتبر می‌تواند امنیت شما و دوستان شما را به خطر بیاندازد.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "به زودی قابلیت‌هایی برای مقابله با نیروی سرکوب، سازماندهی صحیح اعتراضات، و ایجاد اتحاد لازم حتی در مواقع قطعی اینترنت به این اپلیکیشن اضافه خواهد شد.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "توسعه‌ی تکنولوژی می‌تواند به پیروزی انقلاب سرعت دهد، ولی پر‌هزینه‌ و زمان‌بر است. با اشتراک‌گذاری این اپلیکیشن به برنامه‌نویسان آزادی‌خواه نیرو دهید تا بتوانند شما را در مسیر ساخت ایرانی بهتر همراهی کنند.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    ActiveDevelopment()
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(
                        onClick = {
                            openWeb(
                                context,
                                "https://github.com/mardomio/mardom-android",
                            )
                        },
                    ) {
                        Text(
                            text = "متخصصین می‌توانند با کلیک بر روی این متن، سورس کد اپلیکیشن را در گیت‌هاب بررسی کنند.",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }
                UpdateDialog()
            }
        }
    }
}

@Composable
fun UpdateDialog() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
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