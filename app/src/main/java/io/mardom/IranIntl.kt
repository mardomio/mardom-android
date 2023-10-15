package io.mardom

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import okhttp3.OkHttpClient

@Composable
fun IranIntlCard() {
    val context = LocalContext.current
    Card(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { context.app.screen.value = "iranintl" }
                .padding(vertical = 10.dp, horizontal = 16.dp),
        ) {
            Text("پخش زنده ایران اینترنشنال")
        }
    }
}

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun IranIntl() {
    val context = LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val callFactory = OkHttpClient.Builder()
                .proxy(context.app.proxy)
                .ignoreAllSSLErrors()
                .build()
            val defaultDataSourceFactory =
                DefaultDataSource.Factory(context, OkHttpDataSource.Factory(callFactory))
            val dataSourceFactory = DefaultDataSource.Factory(context, defaultDataSourceFactory)
            val source = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri("https://dev-live.livetvstream.co.uk/LS-63503-4/index.m3u8"),
            )
            setMediaSource(source)
            prepare()
            playWhenReady = true
        }
    }

    AndroidView(
        factory = {
            PlayerView(context).apply {
                hideController()
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                player = exoPlayer
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
            }
        },
        modifier = Modifier.background(Color.Black),
    )

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    FullscreenMode()

    BackHandler {
        context.app.screen.value = "home"
    }
}

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun FullscreenMode() {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val activity = context.findActivity()
        val window = activity.window ?: return@DisposableEffect onDispose {}
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)

        insetsController.apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        onDispose {
            insetsController.apply {
                show(WindowInsetsCompat.Type.statusBars())
                show(WindowInsetsCompat.Type.navigationBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_DEFAULT
            }
            activity.requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}