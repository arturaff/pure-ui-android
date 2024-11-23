package ru.arturprgr.pureui.launcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.adapter.ViewPagerAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.databinding.ActivityMainBinding
import ru.arturprgr.pureui.launcher.screens.AllAppsFragment
import ru.arturprgr.pureui.launcher.screens.MainFragment

class MainActivity : AppCompatActivity() {
    companion object {
        fun addApp(
            context: Context,
            sharedPreferences: SharedPreferences,
            packageManager: PackageManager,
            packageName: String,
            index: Int,
        ) {
            if (packageName != "" || packageName != "ru.arturprgr.pureui") {
                Singleton.mainAppsAdapter.addApp(
                    App(
                        context,
                        index,
                        sharedPreferences.getInt("selectIconRound", 25),
                        sharedPreferences.getInt("selectIconSize", 50),
                        "${
                            packageManager.getApplicationLabel(
                                packageManager.getApplicationInfo(
                                    packageName,
                                    packageManager.getLaunchIntentForPackage(
                                        packageName
                                    )!!.flags
                                )
                            )
                        }",
                        packageManager.getApplicationIcon(packageName),
                        packageName
                    )
                )
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setTheme(getSharedPreferences("sPrefs", Context.MODE_PRIVATE).getInt("selectFont", R.style.Base_Theme_PureUI))
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
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


        binding.apply {
            root.adapter = ViewPagerAdapter(this@MainActivity)
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() = binding.root.setCurrentItem(0, true)
}