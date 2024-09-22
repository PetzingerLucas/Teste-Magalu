package com.petzinger.magalu.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[MainViewModel::class.java]

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state?.repositories?.let { repositories ->
                adapter.submitList(repositories)
            }
        }

        viewModel.processIntent(RepositoryIntent.LoadRepositories)

    }

    private fun setupRecyclerView() {
        adapter = RepositoryAdapter { repository ->
            // Todo
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
