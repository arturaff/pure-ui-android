package ru.arturprgr.pureui.launcher.screens

import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.backend.adapter.AllAppsAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.getPackages
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.databinding.FragmentAllAppsBinding
import java.util.Locale

class AllAppsFragment : Fragment() {
    private lateinit var binding: FragmentAllAppsBinding
    private lateinit var packageManager: PackageManager
    private var isSearch: Boolean = false
    private lateinit var allAppsAdapter: AllAppsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAllAppsBinding.inflate(inflater, container, false)
        packageManager = requireContext().packageManager
        allAppsAdapter = AllAppsAdapter()

        Singleton.allAppsList = getPackages(requireContext().packageManager)
        viewAllApps()

        binding.apply {
            editSearch.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                    //not init
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    //not init
                }

                override fun afterTextChanged(s: Editable?) {
                    val adapter = AllAppsAdapter()
                    allApps.adapter = adapter
                    for (pack in Singleton.allAppsList) {
                        if ("${packageManager.getApplicationLabel(pack.applicationInfo)}"
                                .lowercase(Locale.getDefault())
                                .contains("$s".lowercase(Locale.getDefault()))
                        ) {
                            adapter.addApp(
                                App(
                                    requireContext(),
                                    0,
                                    "${packageManager.getApplicationLabel(pack.applicationInfo)}",
                                    packageManager.getApplicationIcon(pack.applicationInfo),
                                    pack.packageName
                                )
                            )
                        }
                    }
                }
            })
            search.setOnClickListener {
                isSearch = !isSearch
                editSearch.isVisible = isSearch
                if (!isSearch) {
                    editSearch.setText("")
                    viewAllApps()
                }
            }
            allApps.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }

    private fun viewAllApps() {
        binding.allApps.adapter = allAppsAdapter
        for (pack in Singleton.allAppsList) {
            allAppsAdapter.addApp(
                App(
                    requireContext(),
                    0,
                    "${packageManager.getApplicationLabel(pack.applicationInfo)}",
                    packageManager.getApplicationIcon(pack.applicationInfo),
                    pack.packageName
                )
            )
        }
    }
}