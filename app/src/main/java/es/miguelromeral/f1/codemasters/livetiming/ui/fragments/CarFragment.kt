package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import classes.toplayer.Standard
import com.google.android.material.tabs.TabLayout

import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentCarBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.MainActivity
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.LiveTimingViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.SetupViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.LiveTimingViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.SetupViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.TabsAdapter


class CarFragment : Fragment() {

    private lateinit var binding: FragmentCarBinding
    private lateinit var sharedViewModel: GameViewModel
    private lateinit var viewModel: SetupViewModel

    private var initialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car, container, false)

        sharedViewModel = (activity as MainActivity).viewModel
        viewModel = (activity as MainActivity).setupViewModel

        val viewPager = binding.viewpagerCar as ViewPager
        setupViewPager(viewPager)
        val tabs = binding.resultTabsCar as TabLayout
        tabs.setupWithViewPager(viewPager)

        binding.lifecycleOwner = this

        sharedViewModel.currentSession.players.observe(this, Observer {
            val items = viewModel.updateItems(it)
            if(items || !initialized){
                val spinnerAdapter = ArrayAdapter(
                    binding.spPlayers.context,
                    android.R.layout.simple_spinner_item,
                    viewModel.names.value!!
                )

                binding.spPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                        viewModel?.setSelectedItem(i, it[i])
                        binding.spPlayers.setBackgroundResource(Standard.TEAMS.getTeamColor(it[i].participant.value?.teamId?.value?.toInt()))
                    }
                    override fun onNothingSelected(adapterView: AdapterView<*>) {
                        //viewModel?.setSelectedItem()
                    }
                }

                binding.spPlayers.adapter = spinnerAdapter
                binding.spPlayers.isEnabled = true
                initialized = true
            }
        })

        return binding.root
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TabsAdapter(childFragmentManager)
        adapter.addFragment(StatusFragment.newInstance(), "Status")
        adapter.addFragment(TelemetryFragment.newInstance(), "Telemetry")
        viewPager.adapter = adapter
    }

    override fun onStop() {
        super.onStop()
        initialized = false
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            CarFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
