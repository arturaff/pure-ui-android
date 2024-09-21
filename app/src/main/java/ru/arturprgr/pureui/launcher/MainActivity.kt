package ru.arturprgr.pureui.launcher

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.adapter.ViewPagerAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.databinding.ActivityMainBinding
import ru.arturprgr.pureui.launcher.screens.AllAppsFragment
import ru.arturprgr.pureui.launcher.screens.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        Singleton.viewPagerAdapter = viewPagerAdapter
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (!sharedPreferences.getBoolean("is_default", false)) {
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
        }

        binding.apply {
            Singleton.viewPager = viewPager
            viewPagerAdapter.addFragment(MainFragment())
            viewPagerAdapter.addFragment(AllAppsFragment())
            viewPager.adapter = viewPagerAdapter

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int,
                ) {
                }

                override fun onPageSelected(position: Int) {
                    //not init
                }

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        viewPager.setCurrentItem(viewPager.currentItem, true)
                    }
                }
            })
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated(
        "Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.appcompat.app.AppCompatActivity")
    )
    override fun onBackPressed() = binding.viewPager.setCurrentItem(0, true)
}