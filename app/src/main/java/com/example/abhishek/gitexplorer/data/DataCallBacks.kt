package com.example.abhishek.gitexplorer.data

interface DataCallBacks {

    fun onSuccess(prData: List<PRData>)

    fun onFailure(e: Throwable)
}