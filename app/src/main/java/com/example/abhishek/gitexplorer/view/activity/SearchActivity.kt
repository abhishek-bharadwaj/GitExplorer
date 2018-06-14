package com.example.abhishek.gitexplorer.view.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.Util
import com.example.abhishek.gitexplorer.data.RepoDataRepository
import com.example.abhishek.gitexplorer.data.interfaces.RepoResultCallback
import com.example.abhishek.gitexplorer.data.model.RepoData
import com.example.abhishek.gitexplorer.gone
import com.example.abhishek.gitexplorer.view.adapter.RepoDataAdapter
import com.example.abhishek.gitexplorer.visible
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : AppCompatActivity(), View.OnClickListener, RepoResultCallback {

    companion object {
        const val KEY_REPO_DATA = "repo_data"
    }

    private val repository = RepoDataRepository()
    private var repoData: ArrayList<RepoData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        btn_search.setOnClickListener(this)

        et_owner_name.setOnEditorActionListener({ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                validateAndRequestData()
            }
            false
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        repository.disposeSubscription()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(KEY_REPO_DATA, repoData)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        repoData = savedInstanceState?.getParcelableArrayList<RepoData>(KEY_REPO_DATA)
        repoData?.let { setUpSuccessUI() }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onClick(v: View?) {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(this, getString(R.string.internet_not_available), Toast.LENGTH_LONG)
                .show()
            return
        }
        validateAndRequestData()
    }

    private fun validateAndRequestData() {
        val ownerName = et_owner_name.text.toString()
        if (TextUtils.isEmpty(ownerName)) {
            Toast.makeText(this, "Please enter valid name", Toast.LENGTH_LONG).show()
            return
        }
        cl_input_container.gone()
        ll_progress_container.visible()
        repository.getRepos(ownerName, this)
    }

    override fun onSuccess(repoData: List<RepoData>) {
        this.repoData = repoData as ArrayList<RepoData>
        Toast.makeText(this, "Found ${repoData.size} repos", Toast.LENGTH_LONG).show()
        setUpSuccessUI()
    }

    override fun onFailure(e: Throwable) {
        cl_input_container.visible()
        ll_progress_container.gone()
        Toast.makeText(this, "Something went wrong please try again.", Toast.LENGTH_LONG).show()
    }

    private fun setUpSuccessUI() {
        cl_input_container.gone()
        ll_progress_container.gone()
        if (repoData?.isEmpty() == true) {
            Toast.makeText(this,
                "No public repos exist for this user.\nPlease try again",
                Toast.LENGTH_LONG).show()
            cl_input_container.visible()
            ll_progress_container.gone()
            return
        }
        repoData?.let {
            rv_repo.visible()
            rv_repo.layoutManager = LinearLayoutManager(this)
            rv_repo.adapter =
                    RepoDataAdapter(this, it)
        }
    }
}