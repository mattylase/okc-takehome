package com.mattylase.okctakehome.extras

fun Any.LogTag(): String {
    return "okc-logs-${javaClass.simpleName}"
}
