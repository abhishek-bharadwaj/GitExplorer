package com.example.abhishek.gitexplorer.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.data.PRData
import kotlinx.android.synthetic.main.layout_pr_data_item.view.*


class PRDataAdapter(private val context: Context, private val prData: List<PRData>) :
    RecyclerView.Adapter<PRDataAdapter.PRDataVH>() {

    private val inflater = LayoutInflater.from(context)
    private val circleTransform = RequestOptions.circleCropTransform()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRDataVH {
        return PRDataVH(inflater.inflate(R.layout.layout_pr_data_item, parent, false))
    }

    override fun getItemCount() = prData.size

    override fun onBindViewHolder(holder: PRDataVH, position: Int) {
        val prItem = prData[position]
        val itemView = holder.itemView
        val prTitle = context.getString(R.string.pr_title, prItem.prNumber, prItem.title)
        itemView.tv_pr_title.text = prTitle
        itemView.tag = prItem.htmlUrl

        val user = prItem.user
        itemView.tv_user_name.text = user.login
        Glide.with(context).load(user.avatarUrl).apply(circleTransform)
            .into(itemView.iv_user_avatar)
    }

    inner class PRDataVH(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val url = (v?.tag ?: return) as? String ?: return
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}