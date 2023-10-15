package io.mardom

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.mardom.ui.theme.AppColors
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant

val Context.actionStore: DataStore<Preferences> by preferencesDataStore(name = "action-data")
val LAST_READINESS_DECLARED_DATE = longPreferencesKey("LAST_READINESS_DECLARED_DATE")

@Composable
fun ActionCard() {
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { context.app.screen.value = "action" }
                .padding(10.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.fist),
                contentDescription = "",
                modifier = Modifier.size(50.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.align(Alignment.Center)) {
                Text(
                    text = "انقلاب اسلامی ایران",
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier
                        .alpha(0.5f)
                        .align(Alignment.CenterHorizontally),
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp,
                        Alignment.CenterHorizontally,
                    ),
                ) {
                    Text(
                        text = "انقلاب",
                        color = AppColors.FlagGreen,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "مردمی",
                        color = AppColors.FlagGold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "ایران",
                        color = AppColors.FlagRed,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }
    }
}

@Composable
fun ActionScreen() {
    val context = LocalContext.current

    val readyDate by remember {
        context.actionStore.data.map { data -> data[LAST_READINESS_DECLARED_DATE] ?: 0 }
    }.collectAsStateWithLifecycle(0)

    val diff = Instant.now().epochSecond - readyDate

    if (readyDate == 0L) {
        ActionAreYouReady()
    } else if (diff < 864000) { // 10 days
        AlreadyDeclared(diff)
    } else {
        DeclareReadiness()
    }

    BackHandler {
        context.app.screen.value = "home"
    }
}

@Composable
fun AlreadyDeclared(diff: Long) {
    val context = LocalContext.current

    val diffText = if (diff < 60) {
        "چند ثانیه قبل"
    } else if (diff < 3600) {
        val minutes = diff / 60
        "$minutes دقیقه قبل"
    } else if (diff < 86400) {
        val hours = diff / 3600
        "$hours ساعت قبل"
    } else {
        val days = diff / 86400
        "$days روز قبل"
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("مانند سایر بسیج‌های مردمی اعلام آمادگی برای اعضای فعال باید به طور منظم انجام شود.")
        Text("شما $diffText آمادگی خود را اعلام کرده‌اید، فعلا نیازی به اعلام دوباره نیست. در صورت نیاز به شما اطلاع‌رسانی خواهیم کرد.")

        Button(
            onClick = { context.app.screen.value = "home" },
            modifier = Modifier.padding(bottom = 20.dp),
        ) {
            Text(text = "بازگشت")
        }
    }
}

@Composable
fun DeclareReadiness() {
    val context = LocalContext.current
    var declareState by remember { mutableStateOf("none") }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (declareState == "doing") {
            Text(text = "در حال ثبت، لطفا کمی صبر کنید.")
            return
        } else if (declareState == "failure") {
            Text(text = "با عرض پوزش، اعلام آمادگی در این زمان مقدور نیست. لطفا بعدا دوباره امتحان کنید.")
            return
        }

        Text(text = "لطفا به روی دکمه‌ی زیر کلیک کنید تا آمادگی شما جهت در نظر گرفتن در برنامه‌ریزی‌ها و سازماندهی منظور شود.")

        Button(
            onClick = {
                declareState = "doing"
                scope.launch {
                    val success = declareReadiness(context)
                    declareState = if (success) "success" else "failure"
                }
            },
            modifier = Modifier.padding(bottom = 20.dp),
        ) {
            Text(text = "اعلام آمادگی")
        }
    }
}

suspend fun declareReadiness(context: Context): Boolean {
    // This is only a psychological button for now.
    // It magically affects brains of those who click it.
    return try {
        context.actionStore.edit { data ->
            data[LAST_READINESS_DECLARED_DATE] = Instant.now().epochSecond
        }
        true
    } catch (ex: Exception) {
        false
    }
}

@Composable
fun ActionAreYouReady() {
    val context = LocalContext.current
    var showNoResponse by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showNoResponse) {
            Text("پاسخ به جواب منفی")
            Button(
                onClick = { context.app.screen.value = "home" },
                modifier = Modifier.padding(bottom = 20.dp),
            ) {
                Text(text = "بازگشت")
            }
            return
        }

        Text("آیا آماده‌ی انقلاب هستید؟")

        Row {
            Button(
                onClick = {
                    MainScope().launch {
                        context.actionStore.edit { data ->
                            data[LAST_READINESS_DECLARED_DATE] = 1
                        }
                    }
                },
                modifier = Modifier.padding(bottom = 20.dp),
            ) {
                Text(text = "آماده‌ام")
            }

            Spacer(modifier = Modifier.width(20.dp))

            Button(
                onClick = { showNoResponse = true },
                modifier = Modifier.padding(bottom = 20.dp),
            ) {
                Text(text = "فعلا نه")
            }
        }
    }
}

@Composable
fun ActionSectionIntro() {
    var page by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
    ) {
        Spacer(modifier = Modifier.weight(1f))

        when (page) {
            0 -> {
                Text("مسئولین محترم تصور می‌کنند اتفاقات مرتبط با مرگ مهسا امینی، از فوت خود ایشان تا تظاهرات‌ها همه با برنامه‌ریزی قبلی بوده. چقدر خنده دار.")
                Text("اما حقیقت تلخ این است که ما هیچ گونه سازماندهی درست و حسابی نداشته‌ایم و ته برنامه‌ریزی انجام شده یک فراخوان در توییتر بوده.")
                Text("هدف از این قسمت، شروع سازماندهی و فعالیت‌های انقلابی با برنامه‌ریزی دقیق است.")
            }

            1 -> {
                Text("شروع هرگونه بسیج مردمی نیازمند عضو‌گیری و فعالیت‌های تمرینی است.")
                Text("قبل از آنکه بتوانیم به تیم حریف گل بزنیم، اول باید خود تیم تشکیل دهیم و تمرین کنیم، والا حتی حق بازی کردن را هم به ما نخواهند داد، گل زدن پیش‌کش.")
            }

            2 -> {
                Text("شروع هرگونه بسیج مردمی نیازمند عضو‌گیری و فعالیت‌های تمرینی است.")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = { page++ },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp),
        ) {
            Text(text = "خُب")
        }
    }
}