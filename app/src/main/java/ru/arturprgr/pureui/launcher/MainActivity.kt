package ru.arturprgr.pureui.launcher

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.ViewPager
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.adapter.ViewPagerAdapter
import ru.arturprgr.pureui.databinding.ActivityMainBinding
import ru.arturprgr.pureui.launcher.screens.AllAppsFragment
import ru.arturprgr.pureui.launcher.screens.MainFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            viewPagerAdapter.addFragment(MainFragment())
            viewPagerAdapter.addFragment(AllAppsFragment())
            viewPager.adapter = viewPagerAdapter

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {

                }

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        viewPager.setCurrentItem(viewPager.currentItem, true)
                    }
                }
            })
        }
    }
}