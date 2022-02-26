package com.project.flow.util

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Created by Federico Bal on 22/2/2022.
 */

fun ImageView.loadImage(url: String) {
    Glide.with(this)
        .load(url)
        .into(this)
}