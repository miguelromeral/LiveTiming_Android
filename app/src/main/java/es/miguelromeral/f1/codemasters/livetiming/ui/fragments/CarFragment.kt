package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentCarBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.MainActivity
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.LiveTimingViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.SetupViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.LiveTimingViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.SetupViewModel


class CarFragment : Fragment() {

    private lateinit var binding: FragmentCarBinding
    private lateinit var sharedViewModel: GameViewModel
    private lateinit var viewModel: SetupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_car, container, false)
        sharedViewModel = (activity as MainActivity).viewModel

        viewModel = ViewModelProviders.of(this,
            SetupViewModelFactory(
                sharedViewModel.currentSession
            )
        ).get(SetupViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        sharedViewModel.currentSession.players.observe(this, Observer {
            val items = viewModel.updateItems(it)
            if(items.isEmpty()){
                binding.spPlayers.isEnabled = false
                binding.spPlayers.adapter = null
            }else {
                val spinnerAdapter = ArrayAdapter(
                    binding.spPlayers.context,
                    android.R.layout.simple_spinner_item,
                    items
                )

                binding.spPlayers.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                        binding.viewModel?.setSelectedItem(items[i])
                    }
                    override fun onNothingSelected(adapterView: AdapterView<*>) {
                        binding.viewModel?.setSelectedItem()
                    }
                }

                binding.spPlayers.adapter = spinnerAdapter
                binding.spPlayers.isEnabled = true
            }
        })

        return binding.root
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
