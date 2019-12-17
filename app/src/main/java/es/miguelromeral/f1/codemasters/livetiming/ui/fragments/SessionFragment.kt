package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer

import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentSessionBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.Main2Activity
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import kotlinx.android.synthetic.main.fragment_session.*
import timber.log.Timber


class SessionFragment : Fragment() {

    private lateinit var binding: FragmentSessionBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_session, container, false)
        viewModel = (activity as Main2Activity).viewModel
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        viewModel.currentSession.sessionData?.trackTemperature?.observe(this, Observer {
            tvTrackTemperature.text = "Track Temperature listened: " + it
        })
        viewModel.currentSession.sessionData?.gamePaused?.observe(this, Observer {
            tvSessionType.text = "GamePaused: "+it.toString()
        })


        viewModel.currentSession.sessionData?.weather?.observe(this, Observer {
            tvWeather.text = "Weather: "+viewModel.currentSession.sessionData?.weather()
        })

        viewModel.currentSession.sessionData?.trackId?.observe(this, Observer {
            tvTrackId.text = "Track: "+viewModel.currentSession.sessionData?.track()
        })
        viewModel.currentSession.sessionData?.era?.observe(this, Observer {
            tvEra.text = "Era: "+viewModel.currentSession.sessionData?.era()
        })




        return binding.root
    }




    companion object {

        @JvmStatic
        fun newInstance(): SessionFragment {
            return SessionFragment().apply {
                arguments = Bundle().apply {
                    //putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
