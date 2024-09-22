package com.petzinger.magalu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petzinger.magalu.databinding.FragmentPullRequestListBinding
import com.petzinger.magalu.databinding.FragmentRepositoryListBinding
import com.petzinger.magalu.di.DaggerAppComponent
import com.petzinger.magalu.ui.RepositoryIntent
import com.petzinger.magalu.ui.adapter.PullRequestAdapter
import com.petzinger.magalu.ui.adapter.RepositoryAdapter
import com.petzinger.magalu.ui.viewmodel.MainViewModel
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

        setupRecyclerView()

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        arguments?.let {
            val args = PullRequestListFragmentArgs.fromBundle(it)
            viewModel.processIntent(RepositoryIntent.LoadPullRequests(args.owner, args.repo))
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.isLoading?.let { isLoading -> setLoadingProgressBar(isLoading) }
            state?.pullRequests?.let { pullRequests ->
                adapter.submitList(pullRequests)
            }
        }

    }

    private fun setLoadingProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        adapter = PullRequestAdapter { pullRequest ->
            // Todo
        }

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
