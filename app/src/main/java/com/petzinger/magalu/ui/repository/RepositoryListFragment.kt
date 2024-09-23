package com.petzinger.magalu.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.petzinger.magalu.databinding.FragmentRepositoryListBinding
import com.petzinger.magalu.di.DaggerAppComponent
import com.petzinger.magalu.ui.viewmodel.MainViewModel
import com.petzinger.magalu.utils.setVisibility
import javax.inject.Inject

class RepositoryListFragment : Fragment() {

    private var _binding: FragmentRepositoryListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: RepositoryAdapter

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
        _binding = FragmentRepositoryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        setupRecyclerView()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.isLoading?.let { isLoading -> setLoadingProgressBar(isLoading) }
            state?.repositories?.let { repositories ->
                adapter.submitList(repositories)
            }
        }
        if (viewModel.state.value?.repositories.isNullOrEmpty()) {
            viewModel.processIntent(RepositoryIntent.LoadRepositories())
        }
    }

    private fun setLoadingProgressBar(isLoading: Boolean) {
        if (viewModel.currentPage != 1) return
        binding.progressBar.setVisibility(!isLoading)
        binding.recyclerView.setVisibility(isLoading)
    }

    private fun setupRecyclerView() {
        startAdapter()
        val dividerItemDecoration =
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        setupListener()
    }

    private fun setupListener() {
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition + 5 >= totalItemCount) {
                    viewModel.processIntent(RepositoryIntent.LoadRepositories())
                }
            }
        })
    }

    private fun startAdapter() {

        adapter = RepositoryAdapter { repository ->
            val action = RepositoryListFragmentDirections
                .actionRepositoryListFragmentToPullRequestListFragment(
                    repository.repositoryOwner.login,
                    repository.name
                )
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
