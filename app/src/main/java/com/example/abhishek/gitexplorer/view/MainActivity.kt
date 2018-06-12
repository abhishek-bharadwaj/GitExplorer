package com.example.abhishek.gitexplorer.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.data.DataCallBacks
import com.example.abhishek.gitexplorer.data.PRData
import com.example.abhishek.gitexplorer.data.Repository
import com.example.abhishek.gitexplorer.gone
import com.example.abhishek.gitexplorer.visible
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), DataCallBacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll_progress_container.visible()
        Repository.getData(this)
    }

    override fun onSuccess(prData: List<PRData>) {
        ll_progress_container.gone()
        rv.visible()
        setUpUI(prData)
    }

    override fun onFailure(e: Throwable) {
        ll_progress_container.gone()
    }

    private fun setUpUI(prData: List<PRData>) {
        val repo = prData[0].head.repo
        tv_repo_name.text = repo.name
        Glide.with(this).load(repo.owner.avatarUrl).into(iv_repo_icon)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = PRDataAdapter(this, prData)
    }
}
