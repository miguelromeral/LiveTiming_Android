package es.miguelromeral.f1.codemasters.livetiming

import android.app.Application
import android.content.Context
import timber.log.Timber
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MyApplication: Application() {


    override fun onCreate() {
        instance = this
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    companion object{
        private var instance: MyApplication? = null

        fun getContext(): Context? {
            return instance?.applicationContext
        }
    }
}