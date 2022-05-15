package com.example.githubusersearch.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusersearch.R
import com.example.githubusersearch.data.database.model.User
import com.example.githubusersearch.databinding.ItemSearchRepositoryBinding

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var users: List<User>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = users?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search_repository,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.repo = users!![position]
        holder.binding.searchItem.setOnClickListener {
            val action =
                SearchFragmentDirections.actionDetails(
                    users!![position].userName
                )
            Navigation.findNavController(it).navigate(action)
        }

    }

    class SearchViewHolder(val binding: ItemSearchRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)
}