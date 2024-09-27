package ru.arturprgr.pureui.backend

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import ru.arturprgr.pureui.R

fun getPackages(packageManager: PackageManager): MutableList<PackageInfo> {
    return packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        .filter { packageInfo ->
            packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null
        }.toMutableList()
}