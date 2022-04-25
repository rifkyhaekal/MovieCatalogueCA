package com.albro.alfamoviecatalogue.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.ui.NavigationUI
import com.albro.alfamoviecatalogue.R
import com.albro.alfamoviecatalogue.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NavigationUI.setupWithNavController(binding.navView, navController)
    }

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment_activity_home
        ) as DynamicNavHostFragment).navController
    }
}