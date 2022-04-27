package com.albro.alfamoviecatalogue.ui.detail.moviedetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.albro.alfamoviecatalogue.R
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.Movie
import com.albro.alfamoviecatalogue.core.utils.*
import com.albro.alfamoviecatalogue.databinding.ActivityMovieDetailBinding
import com.albro.alfamoviecatalogue.databinding.ContentDetailMovieBinding
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var activityMovieDetailBinding: ActivityMovieDetailBinding
    private lateinit var contentDetailMovieBinding: ContentDetailMovieBinding

    private val viewModel: MovieDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMovieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        contentDetailMovieBinding = activityMovieDetailBinding.detailContent

        activityMovieDetailBinding.appBar.toolbar.title =
            getString(R.string.title_activity_detail_movie)

        setSupportActionBar(activityMovieDetailBinding.appBar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(activityMovieDetailBinding.root)

        val extras = intent.extras
        if (extras !== null) {
            val movieId = extras.getInt(EXTRA_MOVIE)
            viewModel.getMovieDetail(movieId).observe(this, movieDetail)
        }
    }

    private val movieDetail = Observer<Resource<Movie>> { movieDetail ->
        if (movieDetail != null) {
            when (movieDetail) {
                is Resource.Loading -> activityMovieDetailBinding.progressBar.visible()
                is Resource.Success -> if (movieDetail.data != null) {
                    activityMovieDetailBinding.progressBar.gone()
                    setMovieDetail(movieDetail.data)
                }
                is Resource.Error -> {
                    activityMovieDetailBinding.progressBar.gone()
                    this.showToastShort(getString(R.string.error))
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun setMovieDetail(movie: Movie?) {
        contentDetailMovieBinding.apply {
            tvTitleMovie.text = movie?.title
            tvDurationMovie.text = movie?.movieDetail?.duration
            tvGenreMovie.text = movie?.movieDetail?.genre
            tvOverviewMovie.text = movie?.movieDetail?.overview
            tvReleasedate.text = movie?.movieDetail?.releaseDate
            ratingMovie.rating = movie?.movieDetail?.userScore ?: 0F
            ratingValueMovie.text = movie?.movieDetail?.userScore.toString()
            tvStatusMovie.text = movie?.movieDetail?.status
            loadImage(Common.POSTER_URL + movie?.imgPoster, imgPosterMovie)
            loadImage(Common.POSTER_URL + movie?.movieDetail?.backdropPath, imgPosterBackdrop)

            if (movie != null) {
                var favoriteState = movie.favorite
                setFavorite(favoriteState)
                fabFavorite.clicks().subscribe {
                    favoriteState = !favoriteState
                    viewModel.setFavorite(movie, favoriteState)
                    setFavorite(favoriteState)
                }

            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            contentDetailMovieBinding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_yellow
                )
            )
        } else {
            contentDetailMovieBinding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_white
                )
            )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movieId"
    }
}