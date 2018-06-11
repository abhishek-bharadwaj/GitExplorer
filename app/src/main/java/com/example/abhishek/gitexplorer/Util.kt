package com.example.abhishek.gitexplorer

import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Util {
    fun <T> applyIOSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer<T, T> { upstream: Single<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}