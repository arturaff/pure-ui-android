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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import ru.arturprgr.pureui.R
import ru.arturprgr.pureui.backend.data.Singleton
import ru.arturprgr.pureui.databinding.ActivitySettingsBinding
import ru.arturprgr.pureui.launcher.MainActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var appPreview: View
    private lateinit var iconParams: FrameLayout.LayoutParams
    private lateinit var cardParams: LinearLayout.LayoutParams
    private lateinit var label: TextView
    private lateinit var icon: ImageView
    private lateinit var card: CardView
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
        appPreview = View.inflate(this@SettingsActivity, R.layout.layout_app, null)
        label = appPreview.findViewById(R.id.label)
        icon = appPreview.findViewById(R.id.icon)
        card = appPreview.findViewById(R.id.card)
        iconParams = icon.layoutParams as FrameLayout.LayoutParams
        cardParams = card.layoutParams as LinearLayout.LayoutParams
        icon.setImageDrawable(packageManager.getApplicationIcon(Singleton.allAppsList[0].packageName))
        label.text = "${
            packageManager.getApplicationLabel(
                packageManager.getApplicationInfo(
                    Singleton.allAppsList[0].packageName, packageManager.getLaunchIntentForPackage(
                        Singleton.allAppsList[0].packageName
                    )!!.flags
                )
            )
        }"

        sharedPreferences = getSharedPreferences("sPrefs", Context.MODE_PRIVATE)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            preview.addView(appPreview)
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
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/advent_pro.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.Base_Theme_PureUI)
                                .putString("fontTypeface", "fonts/advent_pro.ttf")
                                .apply()
                        }

                        1 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/alegreya.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.alegreya)
                                .putString("fontTypeface", "fonts/alegreya.ttf").apply()
                        }

                        2 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/amatic_sc.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.amatic_sc)
                                .putString("fontTypeface", "fonts/amatic_sc.ttf").apply()
                        }

                        3 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/bad_script.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.bad_script)
                                .putString("fontTypeface", "fonts/bad_script.ttf")
                                .apply()
                        }

                        4 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/caveat.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.caveat)
                                .putString("fontTypeface", "fonts/caveat.ttf").apply()
                        }

                        5 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/comfortaa.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.comfotaa)
                                .putString("fontTypeface", "fonts/comfortaa.ttf").apply()
                        }

                        6 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/jost.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.jost)
                                .putString("fontTypeface", "fonts/jost.ttf").apply()
                        }

                        7 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/jura.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.jura)
                                .putString("fontTypeface", "fonts/jura.ttf").apply()
                        }

                        8 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/marck_script.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.marck_script)
                                .putString("fontTypeface", "fonts/marck_script.ttf")
                                .apply()
                        }

                        9 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/merriweather.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.merriweather)
                                .putString("fontTypeface", "fonts/merriweather.ttf")
                                .apply()
                        }

                        10 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/nunito.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.nunito)
                                .putString("fontTypeface", "fonts/nunito.ttf").apply()
                        }

                        11 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/pacifico.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.pacifico)
                                .putString("fontTypeface", "fonts/pacifico.ttf").apply()
                        }

                        12 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/pangolin.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.pangolin)
                                .putString("fontTypeface", "fonts/pangolin.ttf").apply()
                        }

                        13 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/poiret_one.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.poiret_one)
                                .putString("fontTypeface", "fonts/poiret_one.ttf")
                                .apply()
                        }

                        14 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/sofia_sans.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.sofia_sans)
                                .putString("fontTypeface", "fonts/sofia_sans.ttf")
                                .apply()
                        }

                        15 -> {
                            label.typeface = Typeface.createFromAsset(
                                this@SettingsActivity.assets,
                                "fonts/ubuntu.ttf"
                            )
                            sharedPreferences.edit().putInt("selectFont", R.style.ubuntu)
                                .putString("fontTypeface", "fonts/ubuntu.ttf").apply()
                        }

                        16 -> {
                            label.typeface = Typeface.createFromAsset(
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
                    //icon rounding
                    binding.valueIconRound.text = "$progress"
                    card.radius = progress.toFloat() * 3
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //not init
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    sharedPreferences.edit().putInt("selectIconRound", seekBar!!.progress).apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })
            sharedPreferences.getInt("selectIconRound", 25).also {
                selectIconRound.progress = it
                valueIconRound.text = "$it"
            }

            selectIconSize.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    //icon size
                    binding.selectIconRound.max = (progress / 2)
                    binding.valueIconSize.text = "$progress"
                    iconParams.width = (progress * 3)
                    iconParams.height = (progress * 3)
                    icon.setLayoutParams(iconParams)
                    cardParams.width = (progress * 3)
                    cardParams.height = (progress * 3)
                    card.setLayoutParams(cardParams)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //not init
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    sharedPreferences.edit().putInt("selectIconSize", seekBar?.progress!!).apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })
            sharedPreferences.getInt("selectIconSize", 50).also {
                selectIconSize.progress = it
                valueIconSize.text = "$it"
            }

            selectIndentation.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean,
                ) {
                    binding.valueIndentation.text = "$progress"
                    binding.info.updateLayoutParams<FrameLayout.LayoutParams> {
                        this.topMargin = progress * 3
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    binding.info.isVisible = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    binding.info.isVisible = false
                    sharedPreferences.edit().putInt("selectIndentation", seekBar?.progress!!)
                        .apply()
                    Log.d("Attempt", "${seekBar.id}: ${seekBar.progress}")
                }
            })
            sharedPreferences.getInt("selectIndentation", 200).also {
                selectIndentation.progress = it
                valueIndentation.text = "$it"
            }
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