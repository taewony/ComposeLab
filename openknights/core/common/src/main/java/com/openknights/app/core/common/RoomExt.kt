package com.openknights.app.core.common

import androidx.annotation.StringRes
import com.openknights.app.core.model.Room
import com.openknights.app.core.common.R

val Room.textRes: Int
    @StringRes
    get() = when (name) {
        "TRACK1" -> R.string.track1
        "TRACK2" -> R.string.track2
        else -> R.string.etc
    }