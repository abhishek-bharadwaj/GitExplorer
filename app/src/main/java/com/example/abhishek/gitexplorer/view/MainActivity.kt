package com.example.abhishek.gitexplorer.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.abhishek.gitexplorer.*
import com.example.abhishek.gitexplorer.data.PRData
import com.example.abhishek.gitexplorer.data.Repository
import com.example.abhishek.gitexplorer.data.State
import com.example.abhishek.gitexplorer.interfaces.PRResultCallback
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, PRResultCallback {

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<LinearLayout>
    private var adapter: PRDataAdapter? = null
    private var prData: List<PRData>? = null
    private var repoFullName: String? = null

    companion object {
        private const val ARG_REPO_NAME = "repo_full_name"
        fun startActivity(context: Context, repoFullName: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(ARG_REPO_NAME, repoFullName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repoFullName = intent.extras.getString(ARG_REPO_NAME)
        if (TextUtils.isEmpty(repoFullName)) {
            Toast.makeText(this, "Something went wrong please retry!", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        setSupportActionBar(toolbar)
        bottomSheetBehaviour = BottomSheetBehavior.from<LinearLayout>(ll_filters)
        bottomSheetBehaviour.peekHeight = resources.getDimensionPixelSize(R.dimen.button_height)

        iv_back.setOnClickListener(this)
        tv_label.setOnClickListener(this)
        tv_all.setOnClickListener(this)
        tv_open.setOnClickListener(this)
        tv_close.setOnClickListener(this)
        btn_try_again.setOnClickListener(this)

        requestData()
    }

    override fun onSuccess(prData: List<PRData>) {
        ll_progress_container.gone()
        ll_error.gone()
        rv.visible()
        this.prData = prData
        setUpUI(prData)
        ll_filters.visible()
        tv_label.text = getString(R.string.showing_pull_requests, State.ALL)
    }

    override fun onFailure(e: Throwable) {
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
            btn_try_again -> {
                requestData()
            }
        }
    }

    private fun requestData() {
        ll_progress_container.visible()
        rv.gone()
        ll_error.gone()
        repoFullName?.let { Repository.getPRs(this, it) }
    }

    private fun setUpUI(prData: List<PRData>) {
        if (prData.isEmpty()) {
            Toast.makeText(this, "No pull requests found!", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val repo = prData[0].head.repo
        tv_repo_name.text = repo.name
        Glide.with(this).load(repo.owner.avatarUrl)
            .apply(RequestOptions.circleCropTransform()).into(iv_repo_icon)
        rv.layoutManager = LinearLayoutManager(this)
        adapter = PRDataAdapter(this, prData)
        rv.adapter = adapter
    }

    private fun showErrorUI() {
        ll_progress_container.gone()
        rv.gone()
        ll_error.visible()
    }
}