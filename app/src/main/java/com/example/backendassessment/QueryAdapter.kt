package com.example.backendassessment

import android.app.DownloadManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class QueryAdapter(private val images:List<ResponseItem>):RecyclerView.Adapter<QueryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        Log.i("Test","onCreateViewHolder")
        return QueryViewHolder(layoutInflater.inflate(R.layout.item_query,parent,false))
    }

    override fun onBindViewHolder(holder: QueryViewHolder, position: Int) {
        val item = images[position]
        holder.bind(item)
        Log.i("Test","onBindViewHolder")
    }

    override fun getItemCount(): Int = images.size



}