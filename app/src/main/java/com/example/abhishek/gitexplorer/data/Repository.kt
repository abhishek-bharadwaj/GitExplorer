package com.example.abhishek.gitexplorer.data

import android.util.Log
import com.example.abhishek.gitexplorer.Util
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

object Repository {

    const val TAG = "Repository"

    fun getData() {
        Api.getPRs().compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<PRData>> {
                override fun onSuccess(t: List<PRData>) {
                    Log.d(TAG, t[0].title)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribed called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.toString())
                }
            })
    }
}