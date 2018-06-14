package com.example.abhishek.gitexplorer.data.interfaces

import com.example.abhishek.gitexplorer.data.model.PRData

interface PRResultCallback {

    fun onSuccess(prData: List<PRData>)

    fun onFailure(e: Throwable)
}