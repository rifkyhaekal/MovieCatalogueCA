package com.albro.alfamoviecatalogue.favorite.ui.favoritemovie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.ui.MovieAdapter
import com.albro.alfamoviecatalogue.core.utils.gone
import com.albro.alfamoviecatalogue.core.utils.startActivity
import com.albro.alfamoviecatalogue.core.utils.visible
import com.albro.alfamoviecatalogue.favorite.databinding.FragmentFavoriteMovieBinding
import com.albro.alfamoviecatalogue.favorite.di.favoriteModule
import com.albro.alfamoviecatalogue.ui.detail.moviedetail.MovieDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteMovieFragment : Fragment() {

    private var _favoriteBinding: FragmentFavoriteMovieBinding? = null
    private val binding get() = _favoriteBinding

    private val movieAdapter: MovieAdapter by lazy { MovieAdapter() }
    private val viewModel: FavoriteMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _favoriteBinding = FragmentFavoriteMovieBinding.inflate(
            layoutInflater,
            container, false
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        binding?.progressBar?.visible()
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner) { movies ->
            showDataFavorite(movies)
        }

        movieAdapter.onItemClick = { selectedMovie ->
            requireActivity().startActivity<MovieDetailActivity>(
                MovieDetailActivity.EXTRA_MOVIE to selectedMovie.movieId
            )
        }
    }

    private fun showDataFavorite(movies: List<Movie>) {
        movieAdapter.setData(movies)

        if (movieAdapter.itemCount == 0 || movies.isEmpty()) {
            binding?.apply {
                progressBar.gone()
                rvMovieFavorite.gone()
                emptyInfo.visible()
            }
        } else {
            binding?.apply {
                progressBar.gone()
                rvMovieFavorite.visible()
                emptyInfo.gone()
            }
        }

        with(binding?.rvMovieFavorite) {
            this?.layoutManager = GridLayoutManager(view?.context, 3)
            this?.setHasFixedSize(true)
            this?.adapter = movieAdapter
        }
    }

    override fun onDestroyView() {
        binding?.rvMovieFavorite?.adapter = null
        _favoriteBinding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteBinding = null
    }
}