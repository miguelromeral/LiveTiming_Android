package es.miguelromeral.f1.codemasters.livetiming.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.TimesFragment
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.SessionFragment
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)



        bottom_navigation_view.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.action_music -> {
                    val fragment = SessionFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                R.id.action_films -> {
                    val fragment = TimesFragment.newInstance()
                    openFragment(fragment)
                    true
                }
                else -> false
            }
        }
        bottom_navigation_view.selectedItemId = R.id.action_films
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    override fun onStart() {
        super.onStart()
        viewModel.startListeningUDP()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopListeningUDP()
    }
}
