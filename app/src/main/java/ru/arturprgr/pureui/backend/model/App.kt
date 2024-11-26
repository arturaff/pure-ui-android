package ru.arturprgr.pureui.backend.model

import android.content.Context
import android.graphics.drawable.Drawable
import java.io.Serializable

data class App(
    val context: Context,
    val index: Int,
    val label: String,
    val icon: Drawable,
    val packageName: String,
) : Serializable