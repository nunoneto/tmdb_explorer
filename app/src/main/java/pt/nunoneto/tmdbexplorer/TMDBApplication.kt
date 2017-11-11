package pt.nunoneto.tmdbexplorer

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class TMDBApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        sContext = applicationContext
    }

    companion object {
        private lateinit var sContext: Context

        fun getContext() : Context {
            return sContext
        }
    }

}