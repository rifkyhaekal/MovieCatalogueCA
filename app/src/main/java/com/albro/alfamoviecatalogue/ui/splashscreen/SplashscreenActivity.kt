package com.albro.alfamoviecatalogue.ui.splashscreen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.albro.alfamoviecatalogue.core.utils.startActivity
import com.albro.alfamoviecatalogue.databinding.ActivitySplashscreenBinding
import com.albro.alfamoviecatalogue.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CustomSplashScreen")
class SplashscreenActivity : AppCompatActivity() {

    private lateinit var activitySplashscreenBinding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activitySplashscreenBinding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(activitySplashscreenBinding.root)

        lifecycleScope.launch {
            moveToHome()
        }
    }

    /**
     * moveToHome
     *
     * A function for move to HomeActivity
     */
    private suspend fun moveToHome() {
        lifecycleScope.launch {
            delay(SPLASHSCREEN_DURATION)
            withContext(Dispatchers.Main) {
                startActivity<HomeActivity>()
            }
        }
    }

    /**
     * @property SPLASHSCREEN_DURATION The duration of splashscreen
     */
    companion object {
        private const val SPLASHSCREEN_DURATION: Long = 3000
    }
}