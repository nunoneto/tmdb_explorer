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
        private var sConnectivityReceiver: BroadcastReceiver

        init {
            sConnectivityReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val connected = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)
                    notifyListeners(connected)
                }
            }

            val filter = IntentFilter()
            filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION)
            TMDBApplication.getContext().registerReceiver(sConnectivityReceiver, filter)
        }


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
        }

        fun unsubscribeConnectivityEvents(listener: IConnectivityEvent) {
            sConnectivityListeners.remove(listener)
        }

        private fun notifyListeners(connected: Boolean) {
            for (listener in sConnectivityListeners)
                listener.onConnectivityChanged(connected)
        }

    }

    interface IConnectivityEvent {
        fun onConnectivityChanged(connected: Boolean)
    }
}