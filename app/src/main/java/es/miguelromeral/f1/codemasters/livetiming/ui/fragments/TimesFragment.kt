package es.miguelromeral.f1.codemasters.livetiming.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

import es.miguelromeral.f1.codemasters.livetiming.R
import androidx.viewpager.widget.ViewPager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentManager
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentTimesBinding
import es.miguelromeral.f1.codemasters.livetiming.ui.adapters.TabsAdapter


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TimesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TimesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimesFragment : Fragment() {

    private lateinit var binding: FragmentTimesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_times, container, false)

        val viewPager = binding.viewpagerCar as ViewPager
        setupViewPager(viewPager)
        // Set Tabs inside Toolbar
        val tabs = binding.resultTabsCar as TabLayout
        tabs.setupWithViewPager(viewPager)

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TabsAdapter(childFragmentManager)
        adapter.addFragment(LiveTimingFragment.newInstance(), "Times")
        //adapter.addFragment(Tab2(), "Tab 2")
        viewPager.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            TimesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
