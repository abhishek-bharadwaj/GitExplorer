package com.example.abhishek.gitexplorer.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.data.RepoData
import kotlinx.android.synthetic.main.layout_repo_data_item.view.*

class RepoDataAdapter(private val context: Context, private val repoData: List<RepoData>) :
    RecyclerView.Adapter<RepoDataAdapter.RepoDataVH>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoDataVH {
        return RepoDataVH(inflater.inflate(R.layout.layout_repo_data_item, parent, false))
    }

    override fun getItemCount() = repoData.size

    override fun onBindViewHolder(holder: RepoDataVH, position: Int) {
        val repo = repoData[position]
        holder.itemView.tv_repo_name.text = repo.name
        holder.itemView.tv_language.text = repo.language
        holder.itemView.tag = repo.fullName
    }

    inner class RepoDataVH(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val repoFullName = (v?.tag ?: return) as? String ?: return
            MainActivity.startActivity(context, repoFullName)
        }
    }
}