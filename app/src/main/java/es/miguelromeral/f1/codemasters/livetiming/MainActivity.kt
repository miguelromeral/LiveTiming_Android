package es.miguelromeral.f1.codemasters.livetiming

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import es.miguelromeral.f1.codemasters.livetiming.ui.main.SectionsPagerAdapter
import es.miguelromeral.f1.codemasters.livetiming.ui.main.shared.GameViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)
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