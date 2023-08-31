package io.mardom

import android.content.Context
import android.content.Intent
import android.net.Uri

val Context.app: AndroidApp get() = this.applicationContext as AndroidApp

fun openWeb(context: Context, url: String) {
    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browserIntent)
}

fun shareAppLink(context: Context, link: String) {
    val sharingIntent = Intent(Intent.ACTION_SEND)
    sharingIntent.type = "text/plain"
    val shareBody = "هر نصب یک رأی منفی به جمهوری اسلامی.\n$link"
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "انقلاب مردمی ایران")
    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
    context.startActivity(Intent.createChooser(sharingIntent, "Share via"))
}