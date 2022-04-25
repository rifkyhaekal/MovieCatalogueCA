package com.albro.alfamoviecatalogue.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.albro.alfamoviecatalogue.favorite.R
import com.albro.alfamoviecatalogue.favorite.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator

class FavoriteFragment : Fragment() {

    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val fragmentFavoriteBinding get() = _fragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

        return fragmentFavoriteBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        fragmentFavoriteBinding?.viewPager?.adapter = sectionsPagerAdapter
        fragmentFavoriteBinding?.let {
            TabLayoutMediator(it.tabs, it.viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.title_movie, R.string.title_tv_show)
    }
}