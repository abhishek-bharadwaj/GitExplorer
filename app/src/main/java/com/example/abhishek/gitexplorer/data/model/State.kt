package com.example.abhishek.gitexplorer.data.model

import android.support.annotation.StringDef

object State {
    @StringDef(OPEN, CLOSED, ALL)
    @Retention(AnnotationRetention.SOURCE)
    annotation class PrState

    const val OPEN = "open"
    const val CLOSED = "closed"
    const val ALL = "all"
}