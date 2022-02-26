package com.project.flow.util

import android.view.View

/**
 * Created by Federico Bal on 22/2/2022.
 */

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}