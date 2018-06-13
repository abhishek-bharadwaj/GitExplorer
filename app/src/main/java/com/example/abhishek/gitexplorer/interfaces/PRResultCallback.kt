package com.example.abhishek.gitexplorer.interfaces

import com.example.abhishek.gitexplorer.data.PRData

interface PRResultCallback {

    fun onSuccess(prData: List<PRData>)

    fun onFailure(e: Throwable)
}