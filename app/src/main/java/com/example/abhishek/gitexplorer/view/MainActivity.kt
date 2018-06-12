package com.example.abhishek.gitexplorer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.data.DataCallBacks
import com.example.abhishek.gitexplorer.data.PRData
import com.example.abhishek.gitexplorer.data.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataCallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Repository.getData(this)
    }

    override fun onSuccess(prData: List<PRData>) {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = PRDataAdapter(this, prData)
    }

    override fun onFailure(e: Throwable) {

    }
}
