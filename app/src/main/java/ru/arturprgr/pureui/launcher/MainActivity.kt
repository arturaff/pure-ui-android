package ru.arturprgr.pureui.launcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.adapter.AllAppsAdapter
import ru.arturprgr.pureui.backend.adapter.MainAppsAdapter
import ru.arturprgr.pureui.backend.adapter.ViewPagerAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.backend.data.Params
import ru.arturprgr.pureui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        fun addAppInMainAdapter(
            context: Context,
            packageName: String,
            index: Int,
        ) {
            Singleton.mainAppsAdapter.addApp(
                App(
                    context,
                    index,
                    "${
                        context.packageManager.getApplicationLabel(
                            context.packageManager.getApplicationInfo(
                                packageName,
                                context.packageManager.getLaunchIntentForPackage(
                                    packageName
                                )!!.flags
                            )
                        )
                    }",
                    context.packageManager.getApplicationIcon(packageName),
                    packageName
                )
            )
        }

        fun addAppInAllAppsAdapter(
            context: Context,
            packageInfo: PackageInfo,
        ) {
            Singleton.allAppsAdapter.addApp(
                App(
                    context,
                    0,
                    "${context.packageManager.getApplicationLabel(packageInfo.applicationInfo)}",
                    context.packageManager.getApplicationIcon(packageInfo.applicationInfo),
                    packageInfo.packageName
                )
            )
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        Singleton.mainAppsAdapter = MainAppsAdapter()
        Singleton.allAppsAdapter = AllAppsAdapter()
        Singleton.allAppsList =
            this@MainActivity.packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
                .filter { packageInfo ->
                    this@MainActivity.packageManager.getLaunchIntentForPackage(packageInfo.packageName) != null
                }.toMutableList()
        Params.round = sharedPreferences.getInt("selectIconRound", 25)
        Params.size = sharedPreferences.getInt("selectIconSize", 50)
        Params.indentation = sharedPreferences.getInt("selectIndentation", 200)
        Params.indentationBetweenApps = sharedPreferences.getInt("selectIndentationBetweenApps", 8)
        for (index in 0..19) {
            val app = "${sharedPreferences.getString("app$index", "")}"
            if (app != "") {
                addAppInMainAdapter(
                    this@MainActivity,
                    app,
                    index
                )
                Singleton.mainAppsList.add(app)
            }
        }
        for (pack in Singleton.allAppsList) addAppInAllAppsAdapter(this@MainActivity, pack)

        enableEdgeToEdge()
        setTheme(
            getSharedPreferences("sPrefs", Context.MODE_PRIVATE).getInt(
                "selectFont",
                R.style.Base_Theme_PureUI
            )
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!sharedPreferences.getBoolean("is_default", false))
            AlertDialog.Builder(this@MainActivity)
                .setTitle(resources.getString(R.string.select_your_default_home_screen))
                .setMessage(resources.getString(R.string.to_use_the_launcher_you_need_to_select_the_PureUI_application_as_your_default_home_screen))
                .setPositiveButton(resources.getString(R.string.сhoose)) { _, _ ->
                    sharedPreferences.edit().putBoolean("is_default", true).apply()
                    startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
                }
                .setNegativeButton(resources.getString(R.string.no)) { _, _ -> }
                .create()
                .show()

        binding.root.adapter = ViewPagerAdapter(this@MainActivity)
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() = binding.root.setCurrentItem(0, true)
}