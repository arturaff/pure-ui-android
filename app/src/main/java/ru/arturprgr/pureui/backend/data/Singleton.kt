package ru.arturprgr.pureui.backend.data

import android.app.Application
import android.content.pm.PackageInfo
import androidx.viewpager.widget.ViewPager
import ru.arturprgr.pureui.backend.adapter.MainAppsAdapter
import ru.arturprgr.pureui.backend.adapter.ViewPagerAdapter

class Singleton : Application() {
    companion object {
        lateinit var viewPager: ViewPager
        lateinit var viewPagerAdapter: ViewPagerAdapter
        lateinit var mainAppsAdapter: MainAppsAdapter
        val mainAppsList = mutableListOf<String>()
        lateinit var allAppsList: MutableList<PackageInfo>
    }
}