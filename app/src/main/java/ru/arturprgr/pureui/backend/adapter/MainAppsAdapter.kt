package ru.arturprgr.pureui.backend.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
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

class MainAppsAdapter : RecyclerView.Adapter<MainAppsAdapter.ViewHolder>() {
    private val adapter = arrayListOf<App>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(app: App) = with(LayoutAppBinding.bind(itemView)) {
            Log.d("Attempt", "Добавление на главный экран. Индекс: ${app.index}")
            label.text = app.label
            val iconParams = icon.layoutParams as FrameLayout.LayoutParams
            iconParams.width = (app.size * 3)
            iconParams.height = (app.size * 3)
            icon.setLayoutParams(iconParams)
            val cardViewParams = card.layoutParams as LinearLayout.LayoutParams
            cardViewParams.width = (app.size * 3)
            cardViewParams.height = (app.size * 3)
            card.setLayoutParams(cardViewParams)
            card.radius = app.round.toFloat() * 3
            icon.setImageDrawable(app.drawable)
            root.setOnClickListener {
                val intent: Intent? =
                    app.context.packageManager.getLaunchIntentForPackage(app.packageName)
                if (intent != null) app.context.startActivity(intent)
            }
            root.setOnLongClickListener {
                val popupMenu = PopupMenu(app.context, it)
                popupMenu.inflate(R.menu.menu_main)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.remove -> {
                            Log.d(
                                "Attempt",
                                "Удаление элемента с главного экрана. Индекс: ${app.index}"
                            )
                            val index = Singleton.mainAppsList.indexOf(app.packageName)
                            app.context.getSharedPreferences("sPrefs", Context.MODE_PRIVATE).edit()
                                .remove("app${app.index}").apply()
                            if (index != -1) {
                                Singleton.mainAppsAdapter.removeAtIndex(index)
                                Singleton.mainAppsList.removeAt(index)
                            }
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

    @SuppressLint("NotifyDataSetChanged")
    fun removeAtIndex(index: Int) {
        adapter.removeAt(index)
        notifyDataSetChanged()
    }
}