package com.mattylase.okctakehome.extras

/**
 * Little log tag convenience that's simpler than something like Timber for the size/scope of this
 * assignment
 */
fun Any.logTag(): String {
    return "okc-logs-${javaClass.simpleName}"
}
