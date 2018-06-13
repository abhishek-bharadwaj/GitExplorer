package com.example.abhishek.gitexplorer.data

import android.util.Log
import com.example.abhishek.gitexplorer.Util
import com.example.abhishek.gitexplorer.interfaces.PRResultCallback
import com.example.abhishek.gitexplorer.interfaces.RepoResultCallback
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

object Repository {

    const val TAG = "Repository"

    fun getPRs(callBacks: PRResultCallback, repoFullName: String) {
        Api.getPRs(repoFullName).compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<PRData>> {
                override fun onSuccess(t: List<PRData>) {
                    callBacks.onSuccess(t)
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribed called")
                }

                override fun onError(e: Throwable) {
                    callBacks.onFailure(e)
                }
            })
    }

    fun getRepos(ownerName: String, callBacks: RepoResultCallback) {
        Api.getAllRepos(ownerName).compose(Util.applyIOSchedulers())
            .subscribe(object : SingleObserver<List<RepoData>> {
                override fun onSuccess(t: List<RepoData>) {
                    callBacks.onSuccess(t)
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