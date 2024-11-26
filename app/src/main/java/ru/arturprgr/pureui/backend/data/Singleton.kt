package ru.arturprgr.pureui.backend.data

import android.app.Application
import android.content.pm.PackageInfo
import ru.arturprgr.pureui.backend.adapter.AllAppsAdapter
import ru.arturprgr.pureui.backend.adapter.MainAppsAdapter

class Singleton : Application() {
    companion object {
        val mainAppsList = mutableListOf<String>()
        lateinit var mainAppsAdapter: MainAppsAdapter
        lateinit var allAppsAdapter: AllAppsAdapter
        lateinit var allAppsList: MutableList<PackageInfo>
    }
}