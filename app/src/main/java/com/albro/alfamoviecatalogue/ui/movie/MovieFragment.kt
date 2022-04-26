package com.albro.alfamoviecatalogue.ui.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.albro.alfamoviecatalogue.R
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.ui.MovieAdapter
import com.albro.alfamoviecatalogue.core.utils.gone
import com.albro.alfamoviecatalogue.core.utils.showToastShort
import com.albro.alfamoviecatalogue.core.utils.startActivity
import com.albro.alfamoviecatalogue.core.utils.visible
import com.albro.alfamoviecatalogue.databinding.FragmentMovieBinding
import com.albro.alfamoviecatalogue.ui.detail.moviedetail.MovieDetailActivity
import com.albro.alfamoviecatalogue.core.utils.SortUtils
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    internal val binding get() = _fragmentMovieBinding

    private val viewModel: MovieViewModel by viewModel()
    private val moviesAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding?.appBar?.toolbar
        toolbar?.title = getString(R.string.app_name)

        binding?.rvMovie?.gone()
        setMovies(SortUtils.NEWEST)

        moviesAdapter.onItemClick = { selectedMovie ->
            requireActivity().startActivity<MovieDetailActivity>(
                MovieDetailActivity.EXTRA_MOVIE to selectedMovie.movieId
            )
        }

        binding?.svMovie?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding?.progressBar?.gone()
                    binding?.rvMovie?.visible()
                    setSearchMovies(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    binding?.progressBar?.gone()
                    binding?.rvMovie?.visible()
                    setSearchMovies(newText.orEmpty())
                    return true
                }

            })
        }

        with(binding?.rvMovie) {
            this?.setHasFixedSize(true)
            this?.adapter = moviesAdapter
        }

        onClickFAB()

    }

    @SuppressLint("CheckResult")
    private fun onClickFAB() {
        binding?.apply {
            fabNewest.clicks().subscribe { setMovies(SortUtils.NEWEST) }
            fabOldest.clicks().subscribe { setMovies(SortUtils.OLDEST) }
            fabRandom.clicks().subscribe { setMovies(SortUtils.RANDOM) }
        }
    }

    private fun setMovies(sort: String) {
        viewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
    }

    internal fun setSearchMovies(title: String) {
        val titleQuery = "%$title%"

        viewModel.searchMovies(titleQuery).observe(this) {
            moviesAdapter.setData(it)
            if (it.isNullOrEmpty()) {
                binding?.apply {
                    movieNotFound.visible()
                    rvMovie.gone()
                    progressBar.gone()
                }
            } else {
                binding?.apply {
                    rvMovie.visible()
                    movieNotFound.gone()
                    progressBar.gone()
                }
            }
        }
    }

    private val movieObserver = Observer<Resource<List<Movie>>> { movies ->
        if (movies !== null) {
            when (movies) {
                is Resource.Loading -> binding?.progressBar?.visible()
                is Resource.Success -> {
                    binding?.progressBar?.gone()
                    binding?.rvMovie?.visible()
                    moviesAdapter.setData(movies.data)
                }
                is Resource.Error -> {
                    binding?.progressBar?.gone()
                    binding?.rvMovie?.gone()
                    movies.message?.let { requireActivity().showToastShort(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding?.rvMovie?.adapter = null
        _fragmentMovieBinding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentMovieBinding = null
    }
}