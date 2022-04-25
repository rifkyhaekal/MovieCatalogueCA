package com.albro.alfamoviecatalogue.favorite.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.albro.alfamoviecatalogue.favorite.ui.favoritemovie.FavoriteMovieFragment
import com.albro.alfamoviecatalogue.favorite.ui.favoritetvshow.FavoriteTvShowFragment

class SectionsPagerAdapter(fragment: FavoriteFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FavoriteMovieFragment()
            1 -> FavoriteTvShowFragment()
            else -> FavoriteMovieFragment()
        }
    }


}