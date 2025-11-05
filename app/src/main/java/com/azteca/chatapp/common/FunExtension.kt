package com.azteca.chatapp.common

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun Context.xToast(msg: String, isLong: Boolean = false) {
    android.widget.Toast.makeText(
        this,
        msg,
        if (isLong) android.widget.Toast.LENGTH_LONG else android.widget.Toast.LENGTH_SHORT
    ).show()
}

fun ImageView.xLoadImg(url: String) {
    Glide.with(this.context)
        .load(url)
        .apply(RequestOptions.circleCropTransform())
        .into(this)
}