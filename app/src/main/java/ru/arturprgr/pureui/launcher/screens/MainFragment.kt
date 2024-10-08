package ru.arturprgr.pureui.launcher.screens

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.backend.adapter.MainAppsAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.backend.receiver.BatteryReceiver
import ru.arturprgr.pureui.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var packageManager: PackageManager
    private lateinit var typeface: Typeface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        Singleton.mainAppsAdapter = MainAppsAdapter()
        sharedPreferences = requireContext().getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        packageManager = requireContext().packageManager
        typeface = Typeface.createFromAsset(
            requireContext().assets,
            sharedPreferences.getString("fontTypeface", "fonts/advent_pro.ttf")
        )


        for (index in 0..19) {
            val app = "${sharedPreferences.getString("app$index", "")}"
            if (app != "") {
                addApp(
                    requireContext(),
                    sharedPreferences,
                    packageManager,
                    app,
                    index
                )
                Singleton.mainAppsList.add(app)
            }
        }

        binding.apply {
            clock.setOnLongClickListener {
                startActivity(Intent(Intent.ACTION_QUICK_CLOCK))
                true
            }
            date.setOnLongClickListener {
                startActivity(Intent(Intent.ACTION_QUICK_CLOCK))
                true
            }

            mainApps.layoutManager = LinearLayoutManager(requireContext())
            mainApps.adapter = Singleton.mainAppsAdapter

            requireContext().registerReceiver(
                BatteryReceiver(charge),
                IntentFilter(Intent.ACTION_BATTERY_CHANGED)
            )

            clock.typeface = typeface
            date.typeface = typeface
        }

        return binding.root
    }

    companion object {
        fun addApp(
            context: Context,
            sharedPreferences: SharedPreferences,
            packageManager: PackageManager,
            packageName: String,
            index: Int,
        ) {
            if (packageName != "" || packageName != "ru.arturprgr.pureui") Singleton.mainAppsAdapter.addApp(
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