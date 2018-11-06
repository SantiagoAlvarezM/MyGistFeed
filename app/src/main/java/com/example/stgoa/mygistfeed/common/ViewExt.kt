package com.example.stgoa.mygistfeed.common

import android.graphics.PorterDuff
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.stgoa.mygistfeed.R

fun Fragment.errorToast(stringRes: Int) {
    val toast = Toast.makeText(this.activity, getString(stringRes), Toast.LENGTH_SHORT)
    toast.view.background.setColorFilter(
        ContextCompat.getColor(toast.view.context, android.R.color.holo_red_light),
        PorterDuff.Mode.SRC_IN
    )
    toast.show()
}

fun Fragment.toast(stringRes: Int) {
    val toast = Toast.makeText(this.activity, getString(stringRes), Toast.LENGTH_SHORT)
    toast.view.background.setColorFilter(
        ContextCompat.getColor(toast.view.context, R.color.colorPrimary),
        PorterDuff.Mode.SRC_IN
    )
    toast.show()
}