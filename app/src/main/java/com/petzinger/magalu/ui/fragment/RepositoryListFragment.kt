package com.petzinger.magalu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.navigateUp
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.petzinger.magalu.R
import com.petzinger.magalu.databinding.FragmentRepositoryListBinding
import com.petzinger.magalu.di.DaggerAppComponent
import com.petzinger.magalu.ui.RepositoryIntent
import com.petzinger.magalu.ui.adapter.RepositoryAdapter
import com.petzinger.magalu.ui.viewmodel.MainViewModel
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

        setupRecyclerView()

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        viewModel.processIntent(RepositoryIntent.LoadRepositories)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.isLoading?.let { isLoading -> setLoadingProgressBar(isLoading) }
            state?.repositories?.let { repositories ->
                adapter.submitList(repositories)
            }
        }
    }

    private fun setLoadingProgressBar(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        adapter = RepositoryAdapter { repository ->
            val action = RepositoryListFragmentDirections
                .actionRepositoryListFragmentToPullRequestListFragment(
                    repository.repositoryOwner.login,
                    repository.name
                )
            findNavController().navigate(action)
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
