package com.siddarth.geoquizfinal

import android.os.Build
import android.view.View
import android.view.WindowInsets

/** Insets belong to the outer scroll view; XML supplies the content spacing. */
internal fun View.keepSystemBarsClear() {
    setOnApplyWindowInsetsListener { root, insets ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val margins = insets.getInsets(WindowInsets.Type.systemBars() or WindowInsets.Type.displayCutout())
            root.setPadding(margins.left, margins.top, margins.right, margins.bottom)
        } else {
            @Suppress("DEPRECATION")
            root.setPadding(insets.systemWindowInsetLeft, insets.systemWindowInsetTop,
                insets.systemWindowInsetRight, insets.systemWindowInsetBottom)
        }
        insets
    }
    requestApplyInsets()
}
