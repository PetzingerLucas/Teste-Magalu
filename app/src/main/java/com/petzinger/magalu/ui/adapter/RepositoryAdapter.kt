package com.petzinger.magalu.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petzinger.magalu.R
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

        fun bind(repository: RepositoryItem) {
            with(binding) {
                repositoryName.text = repository.name
                repositoryDescription.text = repository.description ?: NO_DESCRIPTION_AVAILABLE
                starsCount.text = "${repository.starCount}"
                forksCount.text = "${repository.forksCount}"
                fullName.text = repository.repositoryOwner.login
                username.text = repository.repositoryOwner.login
                setUserImage(binding = this, url = repository.repositoryOwner.avatarUrl)

                root.setOnClickListener {
                    onItemClick(repository)
                }
            }
        }

        private fun setUserImage(binding: RepositoryListItemBinding, url: String?) {
            Glide.with(binding.root.context)
                .load(url)
                .placeholder(R.drawable.baseline_account_circle_120)
                .error(R.drawable.baseline_account_circle_120)
                .circleCrop()
                .into(binding.userAvatar)
        }

    }

    private companion object {
        const val NO_DESCRIPTION_AVAILABLE = "No description available"
    }
}

