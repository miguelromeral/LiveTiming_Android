package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentTelemetryBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.MainActivity
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.SetupViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.SetupViewModel
import kotlinx.android.synthetic.main.fragment_telemetry.*

class TelemetryFragment : Fragment() {

    private lateinit var binding: FragmentTelemetryBinding
    private lateinit var viewModel: SetupViewModel
    private lateinit var sharedViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = androidx.databinding.DataBindingUtil.inflate(inflater, R.layout.fragment_telemetry, container, false)

        sharedViewModel = (activity as MainActivity).viewModel
        viewModel = (activity as MainActivity).setupViewModel

        val lifecycleOwner = this
        binding.lifecycleOwner = lifecycleOwner

        viewModel.monitoring.observe(this, Observer {
            binding.player = it
        })

        return binding.root
    }



    companion object {

        @JvmStatic
        fun newInstance(): TelemetryFragment {
            return TelemetryFragment()
                .apply {
                    arguments = Bundle().apply {
                        //putInt(ARG_SECTION_NUMBER, sectionNumber)
                    }
                }
        }
    }
}