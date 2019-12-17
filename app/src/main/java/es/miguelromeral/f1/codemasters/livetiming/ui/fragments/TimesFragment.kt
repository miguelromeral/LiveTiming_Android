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
import es.miguelromeral.f1.codemasters.livetiming.databinding.FragmentBlankBinding
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentManager


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TimesFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TimesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimesFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false)

        val viewPager = binding.viewpager as ViewPager
        setupViewPager(viewPager)
        // Set Tabs inside Toolbar
        val tabs = binding.resultTabs as TabLayout
        tabs.setupWithViewPager(viewPager)



        // Inflate the layout for this fragment
        return binding.root
    }


    private fun setupViewPager(viewPager: ViewPager) {


        val adapter = Adapter(childFragmentManager)
        adapter.addFragment(LiveTimingFragment.newInstance(), "Times")
        //adapter.addFragment(Tab2(), "Tab 2")
        viewPager.adapter = adapter


    }


    internal class Adapter(manager: FragmentManager
    ) : FragmentPagerAdapter(manager) {
        private val mFragmentList = mutableListOf<Fragment>()
        private val mFragmentTitleList = mutableListOf<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList.get(position)
        }
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
