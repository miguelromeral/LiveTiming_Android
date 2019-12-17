package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SeekBarPreference
import es.miguelromeral.f1.codemasters.livetiming.MyApplication
import es.miguelromeral.f1.codemasters.livetiming.R

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        var mycontext = context

        val myPref = findPreference(
            resources.getString(R.string.preference_id_restore_game_settings)) as Preference
        myPref.setOnPreferenceClickListener(object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick (preference: Preference): Boolean {

                mycontext?.let {// Build an AlertDialog
                    val builder = AlertDialog.Builder(it)
                    builder.setTitle(resources.getString(R.string.settings_set_default_game_title))
                    builder.setMessage(resources.getString(R.string.settings_set_default_game_summary))

                    // Set the alert dialog yes button click listener
                    builder.setPositiveButton(resources.getString(R.string.set_default),
                        DialogInterface.OnClickListener { dialog, which ->

                            (findPreference(resources.getString(R.string.preference_id_maxwait)) as SeekBarPreference)
                                .value = HomeViewModel.DEFAULT_MAX_WAIT.toInt()
                            (findPreference(resources.getString(R.string.preference_id_secondduration)) as SeekBarPreference)
                                .value = HomeViewModel.DEFAULT_SECOND_DURATION.toInt()

                            Toast.makeText(it, getString(R.string.game_settings_changed_ok), Toast.LENGTH_LONG).show()
                        })

                    // Set the alert dialog no button click listener
                    builder.setNegativeButton(resources.getString(R.string.set_custom), null)

                    val dialog = builder.create()
                    // Display the alert dialog on interface
                    dialog.show()
                }
                return true
            }
        })

        val stylepref = findPreference(
            resources.getString(R.string.preference_id_appstyle)) as Preference
        stylepref.setOnPreferenceClickListener(object : Preference.OnPreferenceClickListener {
            override fun onPreferenceClick (preference: Preference): Boolean {
                MyApplication.changeStyle()
                return true
            }
        })

        */

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