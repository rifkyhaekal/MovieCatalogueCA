package com.albro.alfamoviecatalogue.ui.tvshow

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
import com.albro.alfamoviecatalogue.core.domain.model.TvShow
import com.albro.alfamoviecatalogue.core.ui.TvShowAdapter
import com.albro.alfamoviecatalogue.core.utils.gone
import com.albro.alfamoviecatalogue.core.utils.showToastShort
import com.albro.alfamoviecatalogue.core.utils.startActivity
import com.albro.alfamoviecatalogue.core.utils.visible
import com.albro.alfamoviecatalogue.databinding.FragmentTvshowBinding
import com.albro.alfamoviecatalogue.ui.detail.tvshowdetail.TvShowDetailActivity
import com.example.haekalmoviecatalogue.utils.SortUtils
import com.jakewharton.rxbinding4.view.clicks
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private var _fragmentTvShowBinding: FragmentTvshowBinding? = null
    private val binding get() = _fragmentTvShowBinding

    private val viewModel: TvShowViewModel by viewModel()
    private val tvShowAdapter: TvShowAdapter by lazy { TvShowAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentTvShowBinding = FragmentTvshowBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = binding?.appBar?.toolbar
        toolbar?.title = getString(R.string.app_name)

        binding?.rvTvshow?.gone()
        setTvShows(SortUtils.NEWEST)

        tvShowAdapter.onItemClick = { selectedTvShow ->
            requireActivity().startActivity<TvShowDetailActivity>(
                TvShowDetailActivity.EXTRA_TVSHOW to selectedTvShow.tvShowId
            )
        }

        binding?.svMovie?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding?.progressBar?.gone()
                    binding?.rvTvshow?.visible()
                    setSearchTvShows(query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    binding?.progressBar?.gone()
                    binding?.rvTvshow?.visible()
                    setSearchTvShows(newText.orEmpty())
                    return true
                }

            })
        }

        with(binding?.rvTvshow) {
            this?.setHasFixedSize(true)
            this?.adapter = tvShowAdapter
        }

        onClickFAB()

    }

    @SuppressLint("CheckResult")
    private fun onClickFAB() {
        binding?.apply {
            fabNewest.clicks().subscribe { setTvShows(SortUtils.NEWEST) }
            fabOldest.clicks().subscribe { setTvShows(SortUtils.OLDEST) }
            fabRandom.clicks().subscribe { setTvShows(SortUtils.RANDOM) }
        }
    }

    private fun setTvShows(sort: String) {
        viewModel.getTvShows(sort).observe(viewLifecycleOwner, tvShowObserver)
    }

    private fun setSearchTvShows(title: String) {
        var titleQuery = "%$title%"

        viewModel.searchTvShows(titleQuery).observe(this) {
            tvShowAdapter.setData(it)
            if (it.isNullOrEmpty()) {
                binding?.apply {
                    tvshowNotFound.visible()
                    rvTvshow.gone()
                    progressBar.gone()
                }
            } else {
                binding?.apply {
                    rvTvshow.visible()
                    tvshowNotFound.gone()
                    progressBar.gone()
                }
            }
        }
    }

    private val tvShowObserver = Observer<Resource<List<TvShow>>> { tvShows ->
        if (tvShows !== null) {
            when (tvShows) {
                is Resource.Loading -> binding?.progressBar?.visible()
                is Resource.Success -> {
                    binding?.progressBar?.gone()
                    binding?.rvTvshow?.visible()
                    tvShowAdapter.setData(tvShows.data)
                }
                is Resource.Error -> {
                    binding?.progressBar?.gone()
                    binding?.rvTvshow?.gone()
                    tvShows.message?.let { requireActivity().showToastShort(it) }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentTvShowBinding = null
    }
}