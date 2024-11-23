package ru.arturprgr.pureui.backend.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.arturprgr.pureui.launcher.screens.AllAppsFragment
import ru.arturprgr.pureui.launcher.screens.MainFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private val fragments = mutableListOf(MainFragment(), AllAppsFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}