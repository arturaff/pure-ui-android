package ru.arturprgr.pureui.backend.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.databinding.LayoutAppBinding
import ru.arturprgr.pureui.backend.model.App

class MainAppsAdapter : RecyclerView.Adapter<MainAppsAdapter.ViewHolder>() {
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
                popupMenu.inflate(R.menu.menu_main)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            true
                        }

                        R.id.move_up -> {
                            true
                        }

                        R.id.move_down -> {
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