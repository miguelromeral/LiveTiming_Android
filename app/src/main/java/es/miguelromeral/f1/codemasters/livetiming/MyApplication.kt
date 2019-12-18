package es.miguelromeral.f1.codemasters.livetiming

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import timber.log.Timber
import androidx.preference.PreferenceManager
import es.miguelromeral.f1.codemasters.livetiming.classes.listener.Controller
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.LiveTimingViewModel
import java.lang.Exception


class MyApplication: Application() {


    override fun onCreate() {
        instance = this
        allResources = resources
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this )
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object{
        lateinit var myPreferences : SharedPreferences
        lateinit var allResources : Resources
        private var instance: MyApplication? = null

        fun getContext(): Context? {
            return instance?.applicationContext
        }

        fun getPreferencePortUDP(): Int {
            val port = myPreferences.getString(allResources.getString(R.string.preference_key_udp_port), Controller.DEFAULT_PORT.toString())
            try{
                return port?.toInt() ?: Controller.DEFAULT_PORT
            }catch (e: Exception){
                return Controller.DEFAULT_PORT
            }
        }
        fun getPreferenceIntervalUpdate() = myPreferences.getInt(allResources.getString(R.string.preference_key_update_interval), LiveTimingViewModel.DEFAULT_DELAY_TIME).toLong()
    }
}