package org.michaelbel.moviemade.presentation.features.main

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.michaelbel.core.adapter.ListAdapter
import org.michaelbel.moviemade.R
import org.michaelbel.moviemade.core.local.BuildUtil
import org.michaelbel.moviemade.databinding.FragmentMoviesBinding
import org.michaelbel.moviemade.ktx.argumentDelegate
import org.michaelbel.moviemade.ktx.assistedViewModels
import org.michaelbel.moviemade.ktx.launchAndRepeatWithViewLifecycle
import org.michaelbel.moviemade.ktx.startActivity
import org.michaelbel.moviemade.presentation.ContainerActivity.Companion.EXTRA_MOVIE
import org.michaelbel.moviemade.presentation.common.GridSpacingItemDecoration
import org.michaelbel.moviemade.presentation.common.base.BaseFragment
import org.michaelbel.moviemade.presentation.features.movie.MovieActivity
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment: BaseFragment(R.layout.fragment_movies) {

    @Inject lateinit var factory: MoviesModel.Factory

    private val list: String? by argumentDelegate()
    private val binding: FragmentMoviesBinding by viewBinding()
    private val viewModel: MoviesModel by assistedViewModels {
        factory.create(list)
    }

    private val listAdapter: ListAdapter = ListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spans: Int = resources.getInteger(R.integer.movies_span_layout_count)

        binding.recyclerView.run {
            adapter = listAdapter
            layoutManager = GridLayoutManager(requireContext(), spans)
            addItemDecoration(GridSpacingItemDecoration(spans, resources.getDimension(R.dimen.movies_list_spacing).toInt()))
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && adapter?.itemCount != 0) {
                        viewModel.movies()
                    }
                }
            })
        }
        binding.emptyView.setOnClickListener {
            binding.emptyView.isGone = true
            viewModel.movies()
        }

        launchAndRepeatWithViewLifecycle {
            launch { viewModel.loading.collect { binding.progressBar.isVisible = it } }
            launch { viewModel.content.collect {

                listAdapter.setItems(it) }
            }
            launch { viewModel.error.collect {
                binding.emptyView.isVisible = true
                binding.emptyView.setMode(it)

                if (BuildUtil.isApiKeyEmpty) {
                    binding.emptyView.setValue(R.string.error_empty_api_key)
                }
            } }
            launch { viewModel.click.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
            launch { viewModel.longClick.collect {
                requireActivity().startActivity<MovieActivity> {
                    putExtra(EXTRA_MOVIE, it)
                }
            } }
        }
    }

    override fun onScrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0)
    }

    companion object {
        fun newInstance(list: String): MoviesFragment {
            return MoviesFragment().apply {
                arguments = bundleOf("list" to list)
            }
        }
    }
}