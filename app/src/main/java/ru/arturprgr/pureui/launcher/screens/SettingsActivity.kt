package ru.arturprgr.pureui.launcher.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.SpinnerAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.databinding.ActivitySettingsBinding
import ru.arturprgr.pureui.launcher.MainActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var editTint: EditText
    private lateinit var onSeekBarChangeListener: OnSeekBarChangeListener
    private lateinit var fontsAdapter: SpinnerAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        editTint = EditText(this@SettingsActivity)
        editTint.hint = resources.getString(R.string.hex_color_code)
        onSeekBarChangeListener = object : OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean,
            ) {
                when (seekBar!!.id) {
                    2131362209 ->  binding.valueIconRound.text = "$progress"
                    2131362210 -> {
                        binding.selectIconRound.max = (progress / 2)
                        binding.valueIconSize.text = "$progress"
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                //not init
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                sharedPreferences.edit().putFloat("${seekBar!!.id}", seekBar.progress.toFloat()).apply()
                Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
            }
        }

        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            openingTheSettingsMainScreen.setOnClickListener {
                startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
            }

            selectIconRound.setOnSeekBarChangeListener(onSeekBarChangeListener)
            selectIconSize.setOnSeekBarChangeListener(onSeekBarChangeListener)
            selectIconRound.progress = sharedPreferences.getFloat("2131362209", 75F).toInt()
            selectIconSize.progress = sharedPreferences.getFloat("2131362210", 150F).toInt()
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "startActivity(Intent(this@SettingsActivity, MainActivity::class.java))",
        "android.content.Intent",
        "ru.arturprgr.pureui.launcher.MainActivity"
    )
    )
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        startActivity(Intent(this@SettingsActivity, MainActivity::class.java))
    }
}