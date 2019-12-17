package es.miguelromeral.f1.codemasters.livetiming.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.Tab2
import es.miguelromeral.f1.codemasters.livetiming.ui.fragments.Tab1
import androidx.fragment.app.FragmentStatePagerAdapter


class Pager//Constructor to the class
    (fm: FragmentManager, //integer to count number of tabs
    internal var tabCount: Int
)//Initializing tab count
    : FragmentStatePagerAdapter(fm) {

    //Overriding method getItem
    override fun getItem(position: Int): Fragment? {
        //Returning the current tabs
        when (position) {
            0 -> {
                return Tab1()
            }
            1 -> {
                return Tab2()
            }
            else -> return null
        }
    }

    //Overriden method getCount to get the number of tabs
    override fun getCount(): Int {
        return tabCount
    }
}