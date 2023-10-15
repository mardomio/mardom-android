package io.mardom

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.media3.common.MimeTypes
import coil.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.mardom.ui.ActiveDevelopment
import io.sanghun.compose.video.RepeatMode
import io.sanghun.compose.video.VideoPlayer
import io.sanghun.compose.video.uri.VideoPlayerMediaItem
import org.json.JSONException
import org.json.JSONObject

@Composable
fun LogicCard() {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { context.app.screen.value = "logic" }
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.cognition),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
            Column {
                Text(
                    text = "عقل سلیم",
                    style = MaterialTheme.typography.titleSmall,
                )
                Text("چرا می‌خواهیم انقلاب کنیم و آیا می‌توانیم؟")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Logic() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("در حال آماده‌سازی")
            Spacer(modifier = Modifier.height(20.dp))
            ActiveDevelopment()
        }
    }

//    var posts by remember { mutableStateOf(emptyList<PostModel>()) }
//
//    LaunchedEffect(Unit) {
//        posts = fetchPosts()
//    }
//
//    val pagerState = rememberPagerState(
//        pageCount = { Int.MAX_VALUE },
//    )
//
//    VerticalPager(state = pagerState) { page ->
//        val post = posts.getOrNull(page)
//        if (post == null) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.Black),
//            ) {
//                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
//            }
//        } else {
//            PostPage(model = post)
//        }
//    }

    val context = LocalContext.current

    BackHandler {
        context.app.screen.value = "home"
    }
}

@Composable
fun PostPage(model: PostModel) {
    var playVideo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = model.title, color = Color.White,
            modifier = Modifier.padding(8.dp),
        )
        if (model.preview != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
            ) {
                if (!playVideo || model.video == null) {
                    AsyncImage(
                        model = model.preview,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .clickable { playVideo = true },
                    )

                    if (model.video != null) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .alpha(0.6f)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.Black)
                                .align(Alignment.Center),
                        )
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp),
                            tint = Color.White,
                        )
                    }
                }

                if (model.video != null && model.vRatio != null && playVideo) {
                    VideoPlayer(
                        mediaItems = listOf(
                            VideoPlayerMediaItem.NetworkMediaItem(
                                url = model.video,
                                mimeType = MimeTypes.APPLICATION_M3U8,
                            ),
                        ),
                        usePlayerController = false,
                        repeatMode = RepeatMode.ONE,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .aspectRatio(model.vRatio)
                            .clickable { playVideo = false },
                    )
                }
            }
        }
    }
}

class PostModel(
    val title: String,
    val preview: String?,
    val video: String?,
    val vRatio: Float?,
)

suspend fun fetchPosts(): List<PostModel> {
    val result = mutableListOf<PostModel>()

    val client = HttpClient(Android)
    val url = "https://old.reddit.com/r/NewIran/search.json?q=عقل+سلیم&restrict_sr=on&raw_json=1"
    val body = JSONObject(client.get(url).bodyAsText())
    val posts = body.getJSONObject("data").getJSONArray("children")

    for (i in 0 until posts.length()) {
        val post = posts.getJSONObject(i).getJSONObject("data")
        val preview = try {
            post.getJSONObject("preview").getJSONArray("images").getJSONObject(0)
                .getJSONArray("resolutions").getJSONObject(1).getString("url")
        } catch (t: JSONException) {
            null
        }
        val video = try {
            post.getJSONObject("media").getJSONObject("reddit_video").getString("hls_url")
        } catch (t: JSONException) {
            null
        }
        val vRatio = try {
            val w = post.getJSONObject("media").getJSONObject("reddit_video").getInt("width")
            val h = post.getJSONObject("media").getJSONObject("reddit_video").getInt("height")
            w.toFloat() / h.toFloat()
        } catch (t: JSONException) {
            null
        }
        val model = PostModel(
            title = post.getString("title"),
            preview = preview,
            video = video,
            vRatio = vRatio,
        )
        result.add(model)
    }

    return result
}