package com.example.abhishek.gitexplorer.interfaces

import com.example.abhishek.gitexplorer.data.RepoData

interface RepoResultCallback {
    fun onSuccess(repoData: List<RepoData>)

    fun onFailure(e: Throwable)
}