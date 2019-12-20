package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentStatusBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.MainActivity
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.SetupViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.SetupViewModel
import kotlinx.android.synthetic.main.fragment_status.*
import timber.log.Timber
import java.lang.Exception

class StatusFragment : Fragment() {

    private lateinit var binding: FragmentStatusBinding
    private lateinit var viewModel: SetupViewModel
    private lateinit var sharedViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = androidx.databinding.DataBindingUtil.inflate(inflater, R.layout.fragment_status, container, false)

        sharedViewModel = (activity as MainActivity).viewModel
        viewModel = (activity as MainActivity).setupViewModel

        binding.viewModel = viewModel

        val lifecycleOwner = this
        binding.lifecycleOwner = lifecycleOwner

/*
        viewModel.updateRequired.observe(this, Observer {update ->
            if(update){
                val player = viewModel.monitoring.value
                player?.let {
                    tvTestCarStatus.text = it.carStatus?.value?.tyreCompound?.value?.toString()
                    return@Observer
                }
                tvTestCarStatus.text = "----"
            }
        })*/


        viewModel.monitoring.observe(this, Observer {
            tvTestCarStatus.text = it?.carStatus?.value?.tyreCompound?.value?.toString() ?: "???"
        })


        return binding.root
    }



    companion object {

        @JvmStatic
        fun newInstance(): StatusFragment {
            return StatusFragment()
                .apply {
                    arguments = Bundle().apply {
                        //putInt(ARG_SECTION_NUMBER, sectionNumber)
                    }
                }
        }
    }
}