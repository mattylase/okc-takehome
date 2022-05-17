package com.mattylase.okctakehome.extras

import android.view.View
import androidx.fragment.app.Fragment

/**
 * Little log tag convenience that's simpler than something like Timber for the size/scope of this
 * assignment
 */
fun Any.logTag(): String {
    return "okc-logs-${javaClass.simpleName}"
}

// adding some minor conveniences for toggling view visibility
fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.invisible() {
    this?.visibility = View.INVISIBLE
}

fun View?.visible() {
    this?.visibility = View.VISIBLE
}
