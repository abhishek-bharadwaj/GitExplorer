package com.example.abhishek.gitexplorer.view

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.abhishek.gitexplorer.*
import com.example.abhishek.gitexplorer.data.DataCallBacks
import com.example.abhishek.gitexplorer.data.PRData
import com.example.abhishek.gitexplorer.data.Repository
import com.example.abhishek.gitexplorer.data.State
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, DataCallBacks {

    private var adapter: PRDataAdapter? = null
    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<LinearLayout>
    private var prData: List<PRData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        ll_progress_container.visible()
        bottomSheetBehaviour = BottomSheetBehavior.from<LinearLayout>(ll_filters)
        bottomSheetBehaviour.peekHeight = resources.getDimensionPixelSize(R.dimen.button_height)

        iv_back.setOnClickListener(this)
        tv_label.setOnClickListener(this)
        tv_all.setOnClickListener(this)
        tv_open.setOnClickListener(this)
        tv_close.setOnClickListener(this)

        Repository.getData(this)
    }

    override fun onSuccess(prData: List<PRData>) {
        this.prData = prData
        ll_progress_container.gone()
        rv.visible()
        setUpUI(prData)
        ll_filters.visible()
        tv_label.text = getString(R.string.showing_pull_requests, State.ALL)
    }

    override fun onFailure(e: Throwable) {
        ll_progress_container.gone()
        showErrorUI()
    }

    override fun onClick(v: View?) {
        when (v) {
            tv_label -> {
                if (bottomSheetBehaviour.isOpen()) bottomSheetBehaviour.close()
                else bottomSheetBehaviour.open()
            }
            tv_all -> {
                adapter?.applyFilter(State.ALL)
                tv_label.text = getString(R.string.showing_pull_requests, State.ALL)
                bottomSheetBehaviour.close()
            }
            tv_open -> {
                adapter?.applyFilter(State.OPEN)
                tv_label.text = getString(R.string.showing_pull_requests, State.OPEN)
                bottomSheetBehaviour.close()
            }
            tv_close -> {
                adapter?.applyFilter(State.CLOSED)
                tv_label.text = getString(R.string.showing_pull_requests, State.CLOSED)
                bottomSheetBehaviour.close()
            }
            iv_back -> {
                finish()
            }
        }
    }

    private fun setUpUI(prData: List<PRData>) {
        val repo = prData[0].head.repo
        tv_repo_name.text = repo.name
        Glide.with(this).load(repo.owner.avatarUrl).into(iv_repo_icon)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = PRDataAdapter(this, prData)
        rv.adapter = adapter
    }

    private fun showErrorUI() {

    }
}
