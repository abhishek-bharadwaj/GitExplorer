package com.example.abhishek.gitexplorer.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.abhishek.gitexplorer.*
import com.example.abhishek.gitexplorer.data.PRDataRepository
import com.example.abhishek.gitexplorer.data.interfaces.PRResultCallback
import com.example.abhishek.gitexplorer.data.model.PRData
import com.example.abhishek.gitexplorer.data.model.State
import com.example.abhishek.gitexplorer.view.adapter.PRDataAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PRViewActivity : AppCompatActivity(), View.OnClickListener, PRResultCallback {

    private lateinit var bottomSheetBehaviour: BottomSheetBehavior<LinearLayout>

    private val repository = PRDataRepository()
    private var adapter: PRDataAdapter? = null
    private var prData: ArrayList<PRData>? = null
    private var repoFullName: String? = null

    companion object {
        private const val ARG_REPO_NAME = "repo_full_name"
        private const val KEY_PR_DATA = "pr_data"

        fun startActivity(context: Context, repoFullName: String) {
            val intent = Intent(context, PRViewActivity::class.java)
            intent.putExtra(ARG_REPO_NAME, repoFullName)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        }

        repoFullName = intent.extras.getString(ARG_REPO_NAME)
        if (TextUtils.isEmpty(repoFullName)) {
            Toast.makeText(this, "Something went wrong please retry!", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_LONG)
                .show()
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

        prData = savedInstanceState?.getParcelableArrayList(KEY_PR_DATA)
        if (prData == null) requestData()
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.disposeSubscription()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_PR_DATA, prData)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        prData = savedInstanceState?.getParcelableArrayList<PRData>(KEY_PR_DATA)
        prData?.let { setUpSuccessUI(prData as ArrayList<PRData>) }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSuccess(prData: List<PRData>) {
        this.prData = prData as ArrayList<PRData>
        setUpSuccessUI(prData)
    }

    override fun onFailure(e: Throwable) {
        showErrorUI()
    }

    private fun setUpSuccessUI(prData: List<PRData>) {
        ll_progress_container.gone()
        ll_error.gone()
        rv.visible()
        prData.let { setUpUI(it) }
        ll_filters.visible()
        tv_label.text = getString(R.string.showing_pull_requests, State.ALL)
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
        repoFullName?.let { repository.getPRs(this, it) }
    }

    private fun setUpUI(prData: List<PRData>) {
        if (isFinishing) return
        if (prData.isEmpty()) {
            Toast.makeText(this, "No pull requests found!", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val repo = prData[0].head.repo
        tv_repo_name.text = repo?.name ?: getString(R.string.app_name)
        repo?.owner?.avatarUrl?.let {
            Glide.with(this).load(it)
                .apply(RequestOptions.circleCropTransform()).into(iv_repo_icon)
        }
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