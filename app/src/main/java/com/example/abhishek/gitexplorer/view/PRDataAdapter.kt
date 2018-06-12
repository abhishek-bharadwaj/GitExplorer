package com.example.abhishek.gitexplorer.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.abhishek.gitexplorer.R
import com.example.abhishek.gitexplorer.data.PRData
import kotlinx.android.synthetic.main.layout_pr_data_item.view.*

class PRDataAdapter(private val context: Context, private val prData: List<PRData>) :
    RecyclerView.Adapter<PRDataAdapter.PRDataVH>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PRDataVH {
        return PRDataVH(inflater.inflate(R.layout.layout_pr_data_item, parent, false))
    }

    override fun getItemCount() = prData.size

    override fun onBindViewHolder(holder: PRDataVH, position: Int) {
        val prItem = prData[position]
        holder.itemView.tv_pr_title.text = prItem.title
    }

    inner class PRDataVH(view: View) : RecyclerView.ViewHolder(view)
}