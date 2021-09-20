package com.catnip.rockpaperscissorgame.utils

import androidx.cardview.widget.CardView

class ButtonStatusUtils {

    fun btnChangeColor(color: Int, btn: CardView) {
        btn.setCardBackgroundColor(color)
    }
}