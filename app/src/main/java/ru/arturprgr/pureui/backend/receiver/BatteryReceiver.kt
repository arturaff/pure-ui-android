package ru.arturprgr.pureui.backend.receiver

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.TextView

class BatteryReceiver(private val view: TextView) : BroadcastReceiver() {
    @SuppressLint("SetTextI18n")
    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        view.text = "$level%"
    }
}