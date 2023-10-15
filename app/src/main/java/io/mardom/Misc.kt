package io.mardom

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val Context.app: AndroidApp get() = this.applicationContext as AndroidApp

fun Context.findActivity(): MainActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is MainActivity) return context
        context = context.baseContext
    }
    throw RuntimeException("Did not find MainActivity")
}

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

// TODO: It's important to not trust everything, we should at least verify the CDN certificate.
fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
    val naiveTrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
    }

    val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
        val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
        init(null, trustAllCerts, SecureRandom())
    }.socketFactory

    sslSocketFactory(insecureSocketFactory, naiveTrustManager)
    hostnameVerifier { _, _ -> true }
    return this
}