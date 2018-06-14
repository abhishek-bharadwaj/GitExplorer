package com.example.abhishek.gitexplorer.data.interfaces

import com.example.abhishek.gitexplorer.data.model.RepoData

interface RepoResultCallback {
    fun onSuccess(repoData: List<RepoData>)

    fun onFailure(e: Throwable)
}