package es.miguelromeral.f1.codemasters.livetiming

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import timber.log.Timber
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.preference.PreferenceManager


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

        fun getPreferenceIntervalUpdate() = myPreferences.getInt(allResources.getString(R.string.preference_key_update_interval), 100).toLong()
    }
}