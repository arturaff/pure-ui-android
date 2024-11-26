package ru.arturprgr.pureui.launcher.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.adapter.AllAppsAdapter
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.databinding.FragmentAllAppsBinding
import java.util.Locale

class AllAppsFragment : Fragment() {
    private lateinit var binding: FragmentAllAppsBinding
    private lateinit var menuMore: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAllAppsBinding.inflate(inflater, container, false)
        menuMore = PopupMenu(requireContext(), binding.more)
        menuMore.inflate(R.menu.menu_more)
        menuMore.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.settings -> startActivity(
                    Intent(
                        requireContext(), SettingsActivity::class.java
                    )
                )
            }
            true
        }
        binding.apply {
            allApps.adapter = Singleton.allAppsAdapter

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
                        if ("${requireContext().packageManager.getApplicationLabel(pack.applicationInfo)}".lowercase(
                                Locale.getDefault()
                            ).contains("$s".lowercase(Locale.getDefault()))
                        ) {
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
                    }
                }
            })

            more.setOnClickListener {
                menuMore.show()
            }

            allApps.layoutManager = LinearLayoutManager(requireContext())
        }

        return binding.root
    }
}