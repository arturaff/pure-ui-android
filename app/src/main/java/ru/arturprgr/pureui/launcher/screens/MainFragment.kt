package ru.arturprgr.pureui.launcher.screens

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.data.Params
import ru.arturprgr.pureui.backend.receiver.BatteryReceiver
import ru.arturprgr.pureui.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var typeface: Typeface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        sharedPreferences = requireContext().getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        typeface = Typeface.createFromAsset(
            requireContext().assets,
            sharedPreferences.getString("fontTypeface", "fonts/advent_pro.ttf")
        )

        binding.apply {
            layout.updateLayoutParams<FrameLayout.LayoutParams> {
                this.topMargin = Params.indentation * 3
            }

            info.clock.setOnLongClickListener {
                startActivity(Intent(Intent.ACTION_QUICK_CLOCK))
                true
            }
            info.date.setOnLongClickListener {
                startActivity(Intent(Intent.ACTION_QUICK_CLOCK))
                true
            }

            mainApps.layoutManager = LinearLayoutManager(requireContext())
            mainApps.adapter = Singleton.mainAppsAdapter

            requireContext().registerReceiver(
                BatteryReceiver(info.charge),
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            info.clock.typeface = typeface
            info.date.typeface = typeface
        }

        return binding.root
    }
}