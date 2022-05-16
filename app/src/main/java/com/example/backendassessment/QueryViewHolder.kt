package com.example.backendassessment

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.backendassessment.databinding.ItemQueryBinding
import com.squareup.picasso.Picasso


class QueryViewHolder(val view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemQueryBinding.bind(view)
    val titleText = view.findViewById<TextView>(R.id.tvQueryTitle)
    val usernameText = view.findViewById<TextView>(R.id.tvQueryUsername)
    val answercountText = view.findViewById<TextView>(R.id.tvQueryAnswerCount)


    fun bind(item:ResponseItem){
        titleText.text=item.title
        usernameText.text=item.owner.display_name
        answercountText.text=item.answer_count.toString()
        Picasso.get().load(item.owner.profile_image).into(binding.ivQuery)
        binding.ivQuery


    }


}
