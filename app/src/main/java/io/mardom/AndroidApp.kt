package io.mardom

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import manet.Manet
import java.net.InetSocketAddress
import java.net.Proxy

class AndroidApp : Application() {
    private lateinit var _proxy: Proxy
    val proxy get() = _proxy

    override fun onCreate() {
        super.onCreate()

        val proxyServer = Manet.newMitmProxyServer(0)
        proxyServer.startBackground()
        _proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("127.0.0.1", proxyServer.port().toInt()))
    }

    var screen = mutableStateOf("home")
}