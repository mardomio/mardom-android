package io.mardom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.mardom.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screen = app.screen

        setContent {
            AppTheme {
                when (screen.value) {
                    "home" -> Home()
                    "action" -> ActionScreen()
                    "logic" -> Logic()
                    "iranintl" -> IranIntl()
                }
            }
        }
    }
}