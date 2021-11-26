package com.vog.ccapitest

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.*
import timber.log.Timber

class CustomNetworkCallback(
    private val customOnAvailable: () -> Unit,
    private val customOnLost: () -> Unit
) : ConnectivityManager.NetworkCallback(), CoroutineScope by MainScope() {

    private var job: Job = Job()
    private var previousStateAvailable = false

    override fun onAvailable(network: Network) {
        Timber.tag("NetworkManager")
            .d("Network available, previous state was available: $previousStateAvailable")
        super.onAvailable(network)
        job.cancel()
        if (!previousStateAvailable)
            customOnAvailable()
        previousStateAvailable = true
    }

    override fun onUnavailable() {
        Timber.tag("NetworkManager").v("Network unavailable")
        super.onUnavailable()
    }

    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        Timber.tag("NetworkManager").v("Link properties changed")
        super.onLinkPropertiesChanged(network, linkProperties)
    }

    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        Timber.tag("NetworkManager").v("Capabilities changed")
        super.onCapabilitiesChanged(network, networkCapabilities)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        Timber.tag("NetworkManager").v("Losing network")
        super.onLosing(network, maxMsToLive)
    }

    override fun onLost(network: Network) {
        Timber.tag("NetworkManager")
            .d("Network lost, previous state was available: $previousStateAvailable")
        super.onLost(network)
        job.cancel()
        job = launch(Dispatchers.Default) {
            delay(1000)
            Timber.tag("NetworkManager").v("Calling custom lost")
            previousStateAvailable = false
            customOnLost()
        }
    }
}
