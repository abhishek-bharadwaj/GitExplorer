package com.example.abhishek.gitexplorer.data

import com.example.abhishek.gitexplorer.Util
import com.example.abhishek.gitexplorer.data.interfaces.RepoResultCallback
import com.example.abhishek.gitexplorer.data.model.RepoData
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class RepoDataRepository {

    private var disposable: Disposable? = null

    fun getRepos(ownerName: String, callBacks: RepoResultCallback) {
        Api.getAllRepos(ownerName).compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<RepoData>> {
                override fun onSuccess(t: List<RepoData>) {
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