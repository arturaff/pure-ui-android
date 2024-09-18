package ru.arturprgr.pureui.launcher.screens

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.backend.adapter.MainAppsAdapter
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.backend.receiver.BatteryReceiver
import ru.arturprgr.pureui.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: MainAppsAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var packageManager: PackageManager
    private lateinit var typeface: Typeface
    private var quantity: Int = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        adapter = MainAppsAdapter()
        sharedPreferences = requireContext().getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        packageManager = requireContext().packageManager
        typeface = Typeface.createFromAsset(requireContext().assets, "fonts/advent_pro.ttf")

        quantity = sharedPreferences.getInt("quantity", 0)
        if (quantity != 0) for (index in 1..quantity) addApp(
            "${
                sharedPreferences.getString(
                    "app$index",
                    ""
                )
            }"
        )

        sharedPreferences.registerOnSharedPreferenceChangeListener { sPrefs, key ->
            Log.d("Attempt", "Обновление в ключе $key")
            if (key == "app$quantity") addApp("${sPrefs.getString(key, "")}")
            else if (key == "quantity") quantity = sPrefs.getInt(key, -1)
        }

        binding.apply {
            mainApps.layoutManager = LinearLayoutManager(requireContext())
            mainApps.adapter = adapter

            requireContext().registerReceiver(
                BatteryReceiver(info),
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )
            clock.typeface = typeface
            date.typeface = typeface
        }

        return binding.root
    }

    private fun addApp(packageName: String) {
        if (packageName != "") adapter.addApp(
            App(
                requireContext(),
                0,
                "${
                    requireContext().packageManager.getApplicationLabel(
                        requireContext().packageManager.getApplicationInfo(
                            packageName,
                            requireContext().packageManager.getLaunchIntentForPackage(
                                packageName
                            )!!.flags
                        )
                    )
                }",
                requireContext().packageManager.getApplicationIcon(packageName),
                packageName
            )
        )
    }
}