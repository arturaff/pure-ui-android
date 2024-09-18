package ru.arturprgr.pureui.backend.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.model.App
import ru.arturprgr.pureui.databinding.LayoutAppBinding

class AllAppsAdapter : RecyclerView.Adapter<AllAppsAdapter.ViewHolder>() {
    private val adapter = arrayListOf<App>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(app: App) = with(LayoutAppBinding.bind(itemView)) {
            label.text = app.label
            icon.setImageDrawable(app.drawable)
            click.setOnClickListener {
                val intent: Intent? =
                    app.context.packageManager.getLaunchIntentForPackage(app.packageName)
                if (intent != null) app.context.startActivity(intent)
            }
            click.setOnLongClickListener {
                val popupMenu = PopupMenu(app.context, it)
                popupMenu.inflate(R.menu.menu_all_apps)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.add_on_main -> {
                            val sharedPreferences =
                                app.context.getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
                            val quantity = sharedPreferences.getInt("quantity", 0)
                            if (quantity != 0) {
                                sharedPreferences.edit().putInt("quantity", quantity + 1).apply()
                                sharedPreferences.edit().putString("app${quantity + 1}", app.packageName)
                                    .apply()
                            } else {
                                sharedPreferences.edit().putInt("quantity", 1).apply()
                                sharedPreferences.edit().putString("app1", app.packageName).apply()
                            }
                            sharedPreferences.registerOnSharedPreferenceChangeListener { sPrefs, key ->
                            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_app, parent, false
            )
        )

    override fun getItemCount(): Int = adapter.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(adapter[position])


    fun addApp(app: App) {
        adapter.add(app)
        notifyItemInserted(app.index)
        notifyItemRangeChanged(app.index, adapter.size)
    }
}