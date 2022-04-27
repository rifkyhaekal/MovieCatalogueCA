package com.albro.alfamoviecatalogue.ui.detail.tvshowdetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.albro.alfamoviecatalogue.R
import com.albro.alfamoviecatalogue.core.data.source.Resource
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.utils.*
import com.albro.alfamoviecatalogue.databinding.ActivityTvShowDetailBinding
import com.albro.alfamoviecatalogue.databinding.ContentDetailTvshowBinding
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowDetailActivity : AppCompatActivity() {

    private lateinit var activityTvShowDetailBinding: ActivityTvShowDetailBinding
    private lateinit var contentDetailTvshowBinding: ContentDetailTvshowBinding

    private val viewModel: TvShowDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityTvShowDetailBinding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        contentDetailTvshowBinding = activityTvShowDetailBinding.detailContent

        activityTvShowDetailBinding.appBar.toolbar.title =
            getString(R.string.title_activity_detail_tv_show)

        setSupportActionBar(activityTvShowDetailBinding.appBar.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContentView(activityTvShowDetailBinding.root)

        val extras = intent.extras
        if (extras !== null) {
            val tvShowId = extras.getInt(EXTRA_TVSHOW)
            viewModel.getTvShowDetail(tvShowId).observe(this, tvShowDetail)
        }
    }

    private val tvShowDetail = Observer<Resource<TvShow>> { tvShowDetail ->
        if (tvShowDetail != null) {
            when (tvShowDetail) {
                is Resource.Loading -> activityTvShowDetailBinding.progressBar.visible()
                is Resource.Success -> if (tvShowDetail.data != null) {
                    activityTvShowDetailBinding.progressBar.gone()
                    setMovieDetail(tvShowDetail.data)
                }
                is Resource.Error -> {
                    activityTvShowDetailBinding.progressBar.gone()
                    this.showToastShort(getString(R.string.error))
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun setMovieDetail(tvShow: TvShow?) {
        contentDetailTvshowBinding.apply {
            tvTitleTvshow.text = tvShow?.title
            tvNetwork.text = tvShow?.tvShowDetail?.network
            tvGenreTvshow.text = tvShow?.tvShowDetail?.genre
            tvOverviewTvshow.text = tvShow?.tvShowDetail?.overview
            tvType.text = tvShow?.tvShowDetail?.type
            ratingTvshow.rating = tvShow?.tvShowDetail?.userScore ?: 0F
            ratingValueMovie.text = tvShow?.tvShowDetail?.userScore.toString()
            tvStatusTvshow.text = tvShow?.tvShowDetail?.status
            loadImage(Common.POSTER_URL + tvShow?.imgPoster, imgPosterTvshow)
            loadImage(Common.POSTER_URL + tvShow?.tvShowDetail?.backdropPath, imgPosterBackdrop)

            if (tvShow != null) {
                var favoriteState = tvShow.favorite
                setFavorite(favoriteState)
                fabFavorite.clicks().subscribe {
                    favoriteState = !favoriteState
                    viewModel.setFavorite(tvShow, favoriteState)
                    setFavorite(favoriteState)
                }

            }
        }
    }

    private fun setFavorite(state: Boolean) {
        if (state) {
            contentDetailTvshowBinding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this, R.drawable.ic_favorite_yellow
                )
            )
        } else {
            contentDetailTvshowBinding.fabFavorite.setImageDrawable(
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
        const val EXTRA_TVSHOW = "extra_tvShowId"
    }
}