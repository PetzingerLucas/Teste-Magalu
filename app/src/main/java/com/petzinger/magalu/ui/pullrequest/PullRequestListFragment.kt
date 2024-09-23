package com.petzinger.magalu.ui.pullrequest

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petzinger.magalu.databinding.FragmentPullRequestListBinding
import com.petzinger.magalu.di.DaggerAppComponent
import com.petzinger.magalu.ui.repository.RepositoryIntent
import com.petzinger.magalu.ui.viewmodel.MainViewModel
import com.petzinger.magalu.utils.setVisibility
import javax.inject.Inject

class PullRequestListFragment : Fragment() {

    private var _binding: FragmentPullRequestListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PullRequestAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPullRequestListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startAdapter()
        setupRecyclerView()

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.isLoading?.let { isLoading -> setLoadingProgressBar(isLoading) }
            state?.pullRequests?.let { pullRequests ->
                adapter.submitList(pullRequests)
            }
        }

        arguments?.let {
            val args = PullRequestListFragmentArgs.fromBundle(it)
            viewModel.processIntent(RepositoryIntent.LoadPullRequests(args.owner, args.repo))
        }
    }

    private fun setLoadingProgressBar(isLoading: Boolean) {
        binding.progressBar.setVisibility(!isLoading)
        binding.recyclerView.setVisibility(isLoading)

    }

    private fun startAdapter() {
        adapter = PullRequestAdapter { pullRequest ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(pullRequest.url))
            activity?.startActivity(browserIntent)
        }
    }

    private fun setupRecyclerView() {
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
