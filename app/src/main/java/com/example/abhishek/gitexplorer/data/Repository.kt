package com.example.abhishek.gitexplorer.data

import android.util.Log
import com.example.abhishek.gitexplorer.Util
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

object Repository {

    const val TAG = "Repository"

    fun getData(callBacks: DataCallBacks) {
        Api.getPRs().compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<PRData>> {
                override fun onSuccess(t: List<PRData>) {
                    callBacks.onSuccess(prData = t)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribed called")
                }

                override fun onError(e: Throwable) {
                    callBacks.onFailure(e)
                }
            })
    }
}