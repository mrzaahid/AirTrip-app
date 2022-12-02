package com.binarfp.airtrip.presentation

import android.widget.EditText

object Utils {
    fun isempty(text : EditText): Boolean {
        val str : CharSequence = text.text.toString()
        return str.isEmpty()
    }
}