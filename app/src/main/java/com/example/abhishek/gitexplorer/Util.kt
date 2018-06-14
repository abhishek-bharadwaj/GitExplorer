package com.example.abhishek.gitexplorer

import android.content.Context
import android.net.ConnectivityManager
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


object Util {

    private val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val displayFormat = SimpleDateFormat("hh:mm a, EEE dd MMM", Locale.getDefault())

    fun <T> applyIOSchedulers(): SingleTransformer<T, T> {
        return SingleTransformer<T, T> { upstream: Single<T> ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    fun getDateDisplayString(dateString: String): String? {
        val date = apiDateFormat.parse(dateString)
        return displayFormat.format(date)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}