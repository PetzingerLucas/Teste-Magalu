package com.petzinger.magalu.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.petzinger.magalu.databinding.RepositoryListItemBinding
import com.petzinger.magalu.model.RepositoryItem

class RepositoryAdapter(
    private val onItemClick: (RepositoryItem) -> Unit
) : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private val repositoryList = mutableListOf<RepositoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            RepositoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int = repositoryList.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(repositories: List<RepositoryItem>) {
        repositoryList.clear()
        repositoryList.addAll(repositories)
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(private val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(repository: RepositoryItem) {
            binding.repositoryName.text = repository.name
            binding.repositoryDescription.text =
                repository.description ?: "No description available"
            binding.repositoryStars.text = "★ ${repository.starCount}"

            binding.root.setOnClickListener {
                onItemClick(repository)
            }
        }

    }
}

