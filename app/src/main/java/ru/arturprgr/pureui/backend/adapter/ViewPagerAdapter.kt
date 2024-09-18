@file:Suppress("DEPRECATION")

package ru.arturprgr.pureui.backend.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment) = fragments.add(fragment)

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]
}