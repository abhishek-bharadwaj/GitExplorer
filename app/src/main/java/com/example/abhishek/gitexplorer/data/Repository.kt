package com.example.abhishek.gitexplorer.data

import com.example.abhishek.gitexplorer.Util
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

object Repository {

    fun getData() {
        Api.getPRs().compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<PRData> {
                override fun onSuccess(t: PRData) {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {

                }
            })
    }
}