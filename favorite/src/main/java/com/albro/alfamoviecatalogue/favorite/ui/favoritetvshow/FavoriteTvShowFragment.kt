package com.albro.alfamoviecatalogue.favorite.ui.favoritetvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.ui.TvShowAdapter
import com.albro.alfamoviecatalogue.core.utils.gone
import com.albro.alfamoviecatalogue.core.utils.startActivity
import com.albro.alfamoviecatalogue.core.utils.visible
import com.albro.alfamoviecatalogue.favorite.databinding.FragmentFavoriteTvShowBinding
import com.albro.alfamoviecatalogue.favorite.di.favoriteModule
import com.albro.alfamoviecatalogue.ui.detail.tvshowdetail.TvShowDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteTvShowFragment : Fragment() {

    private var _favoriteBinding: FragmentFavoriteTvShowBinding? = null
    private val binding get() = _favoriteBinding

    private val tvShowAdapter: TvShowAdapter by lazy { TvShowAdapter() }
    private val viewModel: FavoriteTvShowViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _favoriteBinding = FragmentFavoriteTvShowBinding.inflate(
            layoutInflater,
            container, false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        binding?.progressBar?.visible()
        viewModel.getFavorieTvShows().observe(viewLifecycleOwner) { tvShows ->
            showDataFavorite(tvShows)
        }

        tvShowAdapter.onItemClick = { selectedTvShow ->
            requireActivity().startActivity<TvShowDetailActivity>(
                TvShowDetailActivity.EXTRA_TVSHOW to selectedTvShow.tvShowId
            )
        }
    }

    private fun showDataFavorite(tvShows: List<TvShow>) {
        tvShowAdapter.setData(tvShows)

        if (tvShowAdapter.itemCount == 0 || tvShows.isEmpty()) {
            binding?.apply {
                progressBar.gone()
                rvTvshowFavorite.gone()
                emptyInfo.visible()
            }
        } else {
            binding?.apply {
                progressBar.gone()
                rvTvshowFavorite.visible()
                emptyInfo.gone()
            }
        }

        with(binding?.rvTvshowFavorite) {
            this?.layoutManager = GridLayoutManager(view?.context, 3)
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }
    }

    override fun onDestroyView() {
        binding?.rvTvshowFavorite?.adapter = null
        _favoriteBinding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteBinding = null
    }
}