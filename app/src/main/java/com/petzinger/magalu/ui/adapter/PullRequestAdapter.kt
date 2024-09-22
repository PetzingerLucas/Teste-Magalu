package com.petzinger.magalu.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petzinger.magalu.R
import com.petzinger.magalu.databinding.PullRequestListItemBinding
import com.petzinger.magalu.model.PullRequest
import com.petzinger.magalu.utils.parsedDate

class PullRequestAdapter(
    private val onItemClick: (PullRequest) -> Unit
) : RecyclerView.Adapter<PullRequestAdapter.PullRequestViewHolder>() {

    private val repositoryList = mutableListOf<PullRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding =
            PullRequestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PullRequestViewHolder(binding)
    }

    override fun getItemCount(): Int = repositoryList.size

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(repositoryList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(pullRequests: List<PullRequest>) {
        repositoryList.clear()
        repositoryList.addAll(pullRequests)
        notifyDataSetChanged()
    }

    inner class PullRequestViewHolder(private val binding: PullRequestListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pullRequest: PullRequest) {
            with(binding) {
                prTitle.text = pullRequest.title
                prBody.text = pullRequest.body
                fullName.text = pullRequest.user.login
                username.text = pullRequest.user.login
                createdAt.text = pullRequest.createdAt?.parsedDate().orEmpty()
                setUserImage(binding = this, url = pullRequest.user.avatarUrl)

                root.setOnClickListener {
                    onItemClick(pullRequest)
                }
            }
        }

        private fun setUserImage(binding: PullRequestListItemBinding, url: String?) {
            Glide.with(binding.root.context)
                .load(url)
                .placeholder(R.drawable.baseline_account_circle_120)
                .error(R.drawable.baseline_account_circle_120)
                .circleCrop()
                .into(binding.userAvatar)
        }

    }
}

