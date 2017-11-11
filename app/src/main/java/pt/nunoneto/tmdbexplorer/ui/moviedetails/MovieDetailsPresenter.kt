package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.os.Bundle
import pt.nunoneto.tmdbexplorer.movies.entities.Movie

class MovieDetailsPresenter(savedInstanceFragment: Bundle?, private var mView: MovieDetailsView) {

    private lateinit var mMovie: Movie

    companion object {
        const val BUNDLE_KEY_MOVIE: String = "bundle_key_movie"
    }

    init {

    }


    // Private Methods

    fun restoreState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            return
        }

        mMovie = savedInstanceState.getParcelable(BUNDLE_KEY_MOVIE)
    }


    interface MovieDetailsView {
        fun onMovieLoaded(movie: Movie)
    }
}