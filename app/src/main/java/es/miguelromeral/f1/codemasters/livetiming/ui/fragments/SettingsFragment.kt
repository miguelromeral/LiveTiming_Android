package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R
import timber.log.Timber

class SettingsFragment : PreferenceFragmentCompat(),  SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onSharedPreferenceChanged(preferences: SharedPreferences?, key: String?) {
        /*when(key){
            resources.getString(R.string.preference_id_appstyle) -> {
                MyApplication.changeStyle()
            }
        }*/
    }

    companion object {
        const val PORT_MIN = 1025
        const val PORT_MAX = 65535
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mycontext = context


            (findPreference(resources.getString(R.string.preference_key_udp_port)) as EditTextPreference).setOnPreferenceChangeListener(object : Preference.OnPreferenceChangeListener {

            override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                (newValue as String)?.let {
                    try {
                        val valor = it.toInt()
                        if (valor >= PORT_MIN && valor <= PORT_MAX) {
                            Timber.i("New Port: $valor")
                            Toast.makeText(mycontext, "Your changes will be applied in the next startup.", Toast.LENGTH_LONG).show()
                            return true
                        }
                    }catch (e: Exception){
                        Timber.i("No numbers entered.")
                    }
                }
                Toast.makeText(mycontext, "You have to specify a valid port, between $PORT_MIN and $PORT_MAX, inclusive", Toast.LENGTH_LONG).show()
                return false
            }
        })

    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}