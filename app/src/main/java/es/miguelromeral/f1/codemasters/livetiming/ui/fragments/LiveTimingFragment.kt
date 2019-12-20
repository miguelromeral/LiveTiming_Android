package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.SimpleItemAnimator

import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.GameViewModel
import timber.log.Timber
import androidx.recyclerview.widget.RecyclerView
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentLiveTimingBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.activities.MainActivity
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.DataItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.LiveTimingAdapter
import es.miguelromeral.f1.codemasters.livetiming.ui.factories.LiveTimingViewModelFactory
import es.miguelromeral.f1.codemasters.livetiming.ui.models.ItemLiveTiming
import es.miguelromeral.f1.codemasters.livetiming.ui.viewmodels.LiveTimingViewModel
import java.lang.ref.WeakReference


class LiveTimingFragment : Fragment() {

    private lateinit var binding: FragmentLiveTimingBinding
    private var viewModel: LiveTimingViewModel? = null
    private lateinit var sharedViewModel: GameViewModel
    private lateinit var uiHandler: UiHandler
    private lateinit var adapter: LiveTimingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, es.miguelromeral.f1.codemasters.livetiming.R.layout.fragment_live_timing, container, false)
        sharedViewModel = (activity as MainActivity).viewModel

        adapter = LiveTimingAdapter()
        binding.rvLiveTiming.adapter = adapter

        viewModel = ViewModelProviders.of(this,
            LiveTimingViewModelFactory(
                sharedViewModel.currentSession
            )
        ).get(
            LiveTimingViewModel::class.java)
        binding.viewModel = viewModel

        val lifecycleOwner = this
        binding.lifecycleOwner = lifecycleOwner



        (binding.rvLiveTiming.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false


        viewModel?.let{ vm ->
            vm.items.observe(this, Observer {
                it?.let {
                    Timber.i("Submitting the new list.")
                    adapter.addHeaderAndSubmitList(it)
                }
            })}

        return binding.root

    }


    override fun onStart() {
        super.onStart()
        uiHandler =
            UiHandler(
                adapter,
                binding.rvLiveTiming
            )
        viewModel?.startRefreshing(uiHandler)
    }

    override fun onStop() {
        super.onStop()
        viewModel?.stopRefreshing()
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel?.stopHandlerThread()
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

    class UiHandler(listAdapter: LiveTimingAdapter, recyclerView: RecyclerView) : Handler(){

        private var wrListAdapter = WeakReference(listAdapter)
        private var wrRecyclerView = WeakReference(recyclerView)

        private fun notifyAdapter(itemLiveTiming: ItemLiveTiming){
            //wrListAdapter.get()?.get
            wrRecyclerView.get()?.adapter?.let{

                Timber.i("Actualizado: ${itemLiveTiming.position} -> ${itemLiveTiming.time}")
                it.notifyDataSetChanged()
            }
        }

        override fun handleMessage(msg: Message?){
            super.handleMessage(msg)

            val item = (msg?.obj as ItemLiveTiming)

            notifyAdapter(item)
        }
    }
}


