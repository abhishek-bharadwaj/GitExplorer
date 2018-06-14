package com.example.abhishek.gitexplorer.data

import com.example.abhishek.gitexplorer.Util
import com.example.abhishek.gitexplorer.data.interfaces.PRResultCallback
import com.example.abhishek.gitexplorer.data.model.PRData
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class PRDataRepository {

    private var disposable: Disposable? = null

    fun getPRs(callBacks: PRResultCallback, repoFullName: String) {
        Api.getPRs(repoFullName).compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<PRData>> {
                override fun onSuccess(t: List<PRData>) {
                    callBacks.onSuccess(t)
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onError(e: Throwable) {
                    callBacks.onFailure(e)
                }
            })
    }

    fun disposeSubscription() {
        disposable?.dispose()
    }
}