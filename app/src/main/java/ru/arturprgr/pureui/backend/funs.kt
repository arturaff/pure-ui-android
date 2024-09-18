package ru.arturprgr.pureui.backend

import android.content.pm.PackageInfo
import android.content.pm.PackageManager

fun getPackages(packageManager: PackageManager): MutableList<PackageInfo> {
    return packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        .filter { packageInfo ->
            packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null
        }.toMutableList()
}