package com.appdeviser.tictactoe.utilities

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("app:setCustomVisibility")
    fun setCustomVisibility(view: View, value : Boolean) {
    if(value) {
        view.visibility = View.VISIBLE
    }else {
        view.visibility = View.INVISIBLE
    }
}