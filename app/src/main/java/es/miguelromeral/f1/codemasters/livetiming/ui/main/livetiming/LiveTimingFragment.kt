package es.miguelromeral.f1.codemasters.livetiming.ui.main.livetiming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.SimpleItemAnimator
import es.miguelromeral.f1.codemasters.livetiming.MainActivity

import es.miguelromeral.f1.codemasters.livetiming.R
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentLiveTimingBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.main.shared.GameViewModel
import timber.log.Timber
import androidx.recyclerview.widget.RecyclerView



class LiveTimingFragment : Fragment() {

    private lateinit var binding: FragmentLiveTimingBinding
    private lateinit var viewModel: LiveTimingViewModel
    private lateinit var sharedViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, es.miguelromeral.f1.codemasters.livetiming.R.layout.fragment_live_timing, container, false)
        sharedViewModel = (activity as MainActivity).viewModel

        viewModel = ViewModelProviders.of(this, LiveTimingViewModelFactory(sharedViewModel.currentSession)).get(LiveTimingViewModel::class.java)
        binding.viewModel = viewModel

        val lifecycleOwner = this
        binding.lifecycleOwner = lifecycleOwner


        val adapter =
            LiveTimingAdapter()
        binding.rvLiveTiming.adapter = adapter

        (binding.rvLiveTiming.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        /*sharedViewModel.currentSession.players.observe(this, Observer {
            it?.let{
                Timber.i("Submitting the new list.")

                it.forEach{
                    it.currentLap.observe(lifecycleOwner, Observer {

                    })
                }

                adapter.submitList(it)
            }
        })*/

        viewModel.items.observe(this, Observer {
            it?.let{
                Timber.i("Submitting the new list.")
                adapter.submitList(it)
            }
        })
        viewModel.modifiedItems.observe(this, Observer {
            if(it){
                viewModel.items.value?.let{ list ->
                    //adapter.notifyItemChanged(0)

                    var count = 0
                    while(count < list.size){
                        adapter.notifyItemChanged(count)
                        count++
                    }
                }
                viewModel.endUpdate()
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRefreshing()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRefreshing()
    }



    companion object {

        @JvmStatic
        fun newInstance(): LiveTimingFragment {
            return LiveTimingFragment()
                .apply {
                arguments = Bundle().apply {
                    //putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}
