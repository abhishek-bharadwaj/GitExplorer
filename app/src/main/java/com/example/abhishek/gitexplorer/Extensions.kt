package com.example.abhishek.gitexplorer

import android.support.design.widget.BottomSheetBehavior
import android.view.View

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun BottomSheetBehavior<*>.open() {
    this.state = BottomSheetBehavior.STATE_EXPANDED
}

fun BottomSheetBehavior<*>.close() {
    this.state = BottomSheetBehavior.STATE_COLLAPSED
}

fun BottomSheetBehavior<*>.isOpen() = this.state == BottomSheetBehavior.STATE_EXPANDED