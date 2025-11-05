package com.azteca.chatapp.common

import android.content.Context

fun Context.xToast(msg: String, isLong: Boolean = false) {
    android.widget.Toast.makeText(
        this,
        msg,
        if (isLong) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT
    ).show()
}