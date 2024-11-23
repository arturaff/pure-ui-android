package ru.arturprgr.pureui.backend.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.databinding.LayoutAppBinding
import ru.arturprgr.pureui.launcher.MainActivity

class AllAppsAdapter : RecyclerView.Adapter<AllAppsAdapter.ViewHolder>() {
    private val adapter = arrayListOf<App>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(app: App) = with(LayoutAppBinding.bind(itemView)) {
            label.text = app.label
            icon.setImageDrawable(app.drawable)
            val iconParams = icon.layoutParams as FrameLayout.LayoutParams
            iconParams.width = (app.size * 3)
            iconParams.height = (app.size * 3)
            icon.setLayoutParams(iconParams)
            val cardViewParams = card.layoutParams as LinearLayout.LayoutParams
            cardViewParams.width = (app.size * 3)
            cardViewParams.height = (app.size * 3)
            card.setLayoutParams(cardViewParams)
            card.radius = app.round.toFloat() * 3
            root.setOnClickListener {
                val intent: Intent? =
                    app.context.packageManager.getLaunchIntentForPackage(app.packageName)
                if (intent != null) app.context.startActivity(intent)
            }
            root.setOnLongClickListener {
                val popupMenu = PopupMenu(app.context, it)
                popupMenu.inflate(R.menu.menu_all_apps)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_on_main -> {
                            val sharedPreferences =
                                app.context.getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
                            Singleton.mainAppsList.add(app.packageName)
                            for (index in 0..19) {
                                try {
                                    Singleton.mainAppsList[index]
                                } catch (_: IndexOutOfBoundsException) {
                                    MainActivity.addApp(
                                        app.context,
                                        sharedPreferences,
                                        app.context.packageManager,
                                        app.packageName,
                                        index
                                    )
                                    break
                                }
                            }
                            Log.d("Attempt", "app${Singleton.mainAppsAdapter.itemCount}")
                            sharedPreferences.edit().putString(
                                "app${Singleton.mainAppsAdapter.itemCount}", app.packageName
                            ).apply()
                            true
                        }

                        R.id.app_info -> {
                            val intent =
                                Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.parse("package:${app.packageName}")
                            app.context.startActivity(intent)
                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.layout_app, parent, false
        )
    )

    override fun getItemCount(): Int = adapter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(adapter[position])

    fun addApp(app: App) {
        adapter.add(app)
        notifyItemRangeInserted(app.index, adapter.size)
    }
}