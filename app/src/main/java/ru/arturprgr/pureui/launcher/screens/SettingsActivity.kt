package ru.arturprgr.pureui.launcher.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.data.Params
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.databinding.ActivitySettingsBinding
import ru.arturprgr.pureui.launcher.MainActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var iconParams: FrameLayout.LayoutParams
    private lateinit var cardParams: LinearLayout.LayoutParams
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(
            getSharedPreferences("sPrefs", Context.MODE_PRIVATE).getInt(
                "selectFont", R.style.Base_Theme_PureUI
            )
        )
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            iconParams = preview.icon.layoutParams as FrameLayout.LayoutParams
            cardParams = preview.card.layoutParams as LinearLayout.LayoutParams
            Params.round.also {
                selectIconRound.progress = it
                valueIconRound.text = "$it"
                preview.card.radius = it.toFloat() * 3
                first.card.radius = it.toFloat() * 3
                second.card.radius = it.toFloat() * 3
            }
            Params.size.also {
                selectIconSize.progress = it
                valueIconSize.text = "$it"
                iconParams.width = it * 3
                iconParams.height = it * 3
                first.icon.setLayoutParams(iconParams)
                second.icon.setLayoutParams(iconParams)
                cardParams.width = it * 3
                cardParams.height = it * 3
                first.card.setLayoutParams(cardParams)
                second.card.setLayoutParams(cardParams)
            }
            Params.indentation.also {
                selectIndentation.progress = it
                valueIndentation.text = "$it"
            }
            Params.indentationBetweenApps.also {
                selectIndentationBetweenApps.progress = it
                valueIndentationBetweenApps.text = "$it"
            }

            packageManager.getApplicationIcon(Singleton.allAppsList[0].packageName).also {
                preview.icon.setImageDrawable(it)
                first.icon.setImageDrawable(it)
                second.icon.setImageDrawable(it)
            }
            "${
                packageManager.getApplicationLabel(
                    packageManager.getApplicationInfo(
                        Singleton.allAppsList[0].packageName,
                        packageManager.getLaunchIntentForPackage(
                            Singleton.allAppsList[0].packageName
                        )!!.flags
                    )
                )
            }".also {
                preview.label.text = it
                first.label.text = it
                second.label.text = it
            }

            openingTheSettingsMainScreen.setOnClickListener {
                startActivity(Intent(Settings.ACTION_HOME_SETTINGS))
            }

            selectFont.setSelection(sharedPreferences.getInt("selectIFontItem", 0))
            selectFont.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    sharedPreferences.edit().putInt("selectIFontItem", position).apply()
                    when (position) {
                        0 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/advent_pro.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.Base_Theme_PureUI)
                                .putString("fontTypeface", "fonts/advent_pro.ttf")
                                .apply()
                        }

                        1 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/alegreya.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.alegreya)
                                .putString("fontTypeface", "fonts/alegreya.ttf").apply()
                        }

                        2 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/amatic_sc.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.amatic_sc)
                                .putString("fontTypeface", "fonts/amatic_sc.ttf").apply()
                        }

                        3 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/bad_script.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.bad_script)
                                .putString("fontTypeface", "fonts/bad_script.ttf")
                                .apply()
                        }

                        4 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/caveat.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.caveat)
                                .putString("fontTypeface", "fonts/caveat.ttf").apply()
                        }

                        5 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/comfortaa.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.comfotaa)
                                .putString("fontTypeface", "fonts/comfortaa.ttf").apply()
                        }

                        6 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/jost.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.jost)
                                .putString("fontTypeface", "fonts/jost.ttf").apply()
                        }

                        7 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/jura.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.jura)
                                .putString("fontTypeface", "fonts/jura.ttf").apply()
                        }

                        8 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/marck_script.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.marck_script)
                                .putString("fontTypeface", "fonts/marck_script.ttf")
                                .apply()
                        }

                        9 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/merriweather.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.merriweather)
                                .putString("fontTypeface", "fonts/merriweather.ttf")
                                .apply()
                        }

                        10 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/nunito.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.nunito)
                                .putString("fontTypeface", "fonts/nunito.ttf").apply()
                        }

                        11 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/pacifico.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.pacifico)
                                .putString("fontTypeface", "fonts/pacifico.ttf").apply()
                        }

                        12 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/pangolin.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.pangolin)
                                .putString("fontTypeface", "fonts/pangolin.ttf").apply()
                        }

                        13 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/poiret_one.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.poiret_one)
                                .putString("fontTypeface", "fonts/poiret_one.ttf")
                                .apply()
                        }

                        14 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/sofia_sans.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.sofia_sans)
                                .putString("fontTypeface", "fonts/sofia_sans.ttf")
                                .apply()
                        }

                        15 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/ubuntu.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.ubuntu)
                                .putString("fontTypeface", "fonts/ubuntu.ttf").apply()
                        }

                        16 -> {
                            preview.label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/yanone_kaffeesatz.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.yanone_kaffeesatz)
                                .putString("fontTypeface", "fonts/yanone_kaffeesatz.ttf")
                                .apply()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //not init
                }
            }

            selectIconRound.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    valueIconRound.text = "$progress"
                    preview.card.radius = progress.toFloat() * 3
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //not init
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    first.card.radius = seekBar!!.progress.toFloat() * 3
                    second.card.radius = seekBar.progress.toFloat() * 3
                    sharedPreferences.edit().putInt("selectIconRound", seekBar.progress).apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })

            selectIconSize.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    //icon size
                    selectIconRound.max = (progress / 2)
                    valueIconSize.text = "$progress"
                    iconParams.width = (progress * 3)
                    iconParams.height = (progress * 3)
                    preview.icon.setLayoutParams(iconParams)
                    cardParams.width = (progress * 3)
                    cardParams.height = (progress * 3)
                    preview.card.setLayoutParams(cardParams)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //not init
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    iconParams.width = seekBar?.progress!! * 3
                    iconParams.height = seekBar.progress * 3
                    first.icon.setLayoutParams(iconParams)
                    second.icon.setLayoutParams(iconParams)
                    cardParams.width = seekBar.progress * 3
                    cardParams.height = seekBar.progress * 3
                    first.card.setLayoutParams(cardParams)
                    second.card.setLayoutParams(cardParams)
                    sharedPreferences.edit().putInt("selectIconSize", seekBar.progress).apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })

            selectIndentation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    valueIndentation.text = "$progress"
                    info.updateLayoutParams<FrameLayout.LayoutParams> {
                        this.topMargin = progress * 3
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    info.isVisible = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    info.isVisible = false
                    sharedPreferences.edit().putInt("selectIndentation", seekBar?.progress!!)
                        .apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })

            selectIndentationBetweenApps.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    valueIndentationBetweenApps.text = "$progress"
                    firstApp.updateLayoutParams<LinearLayout.LayoutParams> {
                        this.topMargin = progress * 3
                        this.bottomMargin = progress * 3
                    }
                    secondApp.updateLayoutParams<LinearLayout.LayoutParams> {
                        this.topMargin = progress * 3
                        this.bottomMargin = progress * 3
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    apps.isVisible = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    apps.isVisible = false
                    sharedPreferences.edit()
                        .putInt("selectIndentationBetweenApps", seekBar?.progress!!)
                        .apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })
        }
    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
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