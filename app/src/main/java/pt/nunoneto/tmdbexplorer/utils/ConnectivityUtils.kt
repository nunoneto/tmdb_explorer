package pt.nunoneto.tmdbexplorer.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import pt.nunoneto.tmdbexplorer.TMDBApplication


class ConnectivityUtils {

    companion object {

        private var sConnectivityListeners = ArrayList<IConnectivityEvent>()
        private lateinit var sConnectivityReceiver: BroadcastReceiver

        fun isConnected() : Boolean {
            val connMgr = TMDBApplication.getContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connMgr.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

        fun subscribeConnectivityEvents(listener: IConnectivityEvent) {
            if (sConnectivityListeners.contains(listener)) {
                return
            }

            sConnectivityListeners.add(listener)

            if (sConnectivityReceiver == null) {
                sConnectivityReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context, intent: Intent) {
                        val connected = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)

                    }
                }

                val filter = IntentFilter()
                filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
                TMDBApplication.getContext().registerReceiver(sConnectivityReceiver, filter)
            }
        }

        fun unsubscribeConnectivityEvents(listener: IConnectivityEvent) {
            sConnectivityListeners.remove(listener)
        }

    }

    interface IConnectivityEvent {
        fun onConnectivityChanged(connected: Boolean)
    }
}