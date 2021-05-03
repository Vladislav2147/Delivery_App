package by.bstu.vs.stpms.courier_application.model.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.context
import by.bstu.vs.stpms.courier_application.model.network.NetworkRepository.isOnline
import by.bstu.vs.stpms.courier_application.model.service.OrderService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object ConnectionListener {
    private const val TAG = "ConnectionListener"
    lateinit var connectivityManager: ConnectivityManager

    fun init(context: Context) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (!isOnline(context)) onLost()

        connectivityManager.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onAvailable()
            }

            override fun onLost(network: Network) {
                onLost()
            }
        })
    }

    fun onAvailable() {
        Log.d(TAG, "Available")
        CoroutineScope(Dispatchers.IO).launch {
            OrderService.sendDecline()
        }
    }

    fun onLost() {
        Log.d(TAG, "Lost")
    }
}