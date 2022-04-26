package com.albro.alfamoviecatalogue.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.albro.alfamoviecatalogue.core.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.albro.alfamoviecatalogue.core.data.source.remote.response.MovieGenresItem
import com.albro.alfamoviecatalogue.core.data.source.remote.response.NetworksItem
import com.albro.alfamoviecatalogue.core.data.source.remote.response.TvGenresItem
import java.io.Serializable
import kotlin.math.floor

/**
 * visible
 *
 * Function to set view visibility to visible
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * visible
 *
 * Function to set view visibility to gone
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * showToastShort
 *
 * Function to make Toast
 *
 * @param message the message that want to display
 */
fun Context.showToastShort(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

/**
 * startActivity
 *
 * Function for move to an activity
 *
 * @param params key and value of the data that want to send to the activity
 */
inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    if (params.isNotEmpty()) fillIntentArguments(intent, params)
    this.startActivity(intent)
}

/**
 * fillIntentArguments
 *
 * Function to fill some arguments in intent
 *
 * @param intent
 * @param params
 */
fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
    params.forEach {
        when (val value = it.second) {
            null -> intent.putExtra(it.first, null as Serializable?)
            is Int -> intent.putExtra(it.first, value)
            is String -> intent.putExtra(it.first, value)
            is Double -> intent.putExtra(it.first, value)
            is Boolean -> intent.putExtra(it.first, value)
            is Serializable -> intent.putExtra(it.first, value)
            is Bundle -> intent.putExtra(it.first, value)
            is Parcelable -> intent.putExtra(it.first, value)
            else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
        }
        return@forEach
    }
}


/**
 * loadImage
 *
 * Function for load an image with Glide
 *
 * @param url image url
 * @param imageView image view
 */
@SuppressLint("CheckResult")
fun Context.loadImage(url: String, imageView: ImageView) {
    Glide.with(this)
        .load(url)
        .transform(CenterCrop())
        .apply {
            RequestOptions()
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
        }
        .into(imageView)
}

/**
 * generateMovieGenres
 *
 * Function to generate movie genre from List to String
 *
 * @param genresItem list of MovieGenresItem
 */
fun generateMovieGenres(genresItem: List<MovieGenresItem>): String {
    val builder = StringBuilder()

    if (genresItem.isEmpty()) {
        builder.append("-")
    } else {
        genresItem.forEach { genre ->
            builder.append(genre.name)
            if (genre.name == genresItem.lastOrNull()?.name) {
                builder.append(".")
            } else {
                builder.append(", ")
            }
        }
    }

    return builder.toString()
}

/**
 * generateMovieDuration
 *
 * Function to generate duration of the movie from Int? to String
 *
 * @param duration duration of the movie in Int?
 */
fun generateMovieDuration(duration: Int?): String {
    lateinit var durationParse: String
    if (duration != null) {
        val hour: Double = floor(duration.toDouble() / 60)
        val minute: Double = duration.toDouble() % 60

        durationParse = "${hour.toInt()}h ${minute.toInt()}m"

    }
    return durationParse
}

/**
 * generateNetworks
 *
 * Function to generate network of the tv show from list to String
 *
 * @param networksItem List<NetworkItem>
 */
fun generateNetworks(networksItem: List<NetworksItem>): String {
    val builder = StringBuilder()

    networksItem.forEach { network ->
        builder.append(network.name)
        if (network.name == networksItem.lastOrNull()?.name) {
            builder.append(".")
        } else {
            builder.append(", ")
        }
    }

    return builder.toString()
}

/**
 * generateTvShowGenres
 *
 * Function to generate tv show genre from List to String
 *
 * @param genresItem list of TvGenresItem
 */
fun generateTvShowGenres(genresItem: List<TvGenresItem>): String {
    val builder = StringBuilder()

    if (genresItem.isEmpty()) {
        builder.append("-")
    } else {
        genresItem.forEach { genre ->
            builder.append(genre.name)
            if (genre.name == genresItem.lastOrNull()?.name) {
                builder.append(".")
            } else {
                builder.append(", ")
            }
        }
    }

    return builder.toString()
}

/**
 * setRating
 *
 * Function to set rating from Double? to Float
 *
 * @param rating Double?
 */
fun setRating(rating: Double?): Float {
    val newRating: Float = if (rating != null) {
        rating.toFloat() / 2
    } else {
        0F
    }
    return newRating
}

/**
 * setOverview
 *
 * Function to set overview from String? to String
 *
 * @param overview String?
 */
fun setOverview(overview: String?): String {
    val newOverview: String = if (overview?.length != 0 && overview != null) {
        overview
    } else {
        "-"
    }
    return newOverview
}