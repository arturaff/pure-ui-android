package ru.arturprgr.pureui.launcher.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.backend.adapter.AllAppsAdapter
import ru.arturprgr.pureui.databinding.FragmentAllAppsBinding
import ru.arturprgr.pureui.backend.getPackages
import ru.arturprgr.pureui.backend.model.App

class AllAppsFragment : Fragment() {
    private lateinit var binding: FragmentAllAppsBinding
    private lateinit var adapter: AllAppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAllAppsBinding.inflate(inflater, container, false)
        adapter = AllAppsAdapter()

        getPackages(requireContext().packageManager)
        for (pack in getPackages(requireContext().packageManager)) {
            adapter.addApp(
                App(
                    requireContext(),
                    0,
                    "${requireContext().packageManager.getApplicationLabel(pack.applicationInfo)}",
                    requireContext().packageManager.getApplicationIcon(pack.applicationInfo),
                    pack.packageName
                )
            )
        }

        binding.apply {
            allApps.layoutManager = LinearLayoutManager(requireContext())
            allApps.adapter = adapter
        }

        return binding.root
    }
}