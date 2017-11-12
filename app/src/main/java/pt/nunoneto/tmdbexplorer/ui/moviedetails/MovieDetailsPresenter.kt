package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.content.Intent
import android.os.Bundle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import pt.nunoneto.tmdbexplorer.movies.MovieManager
import pt.nunoneto.tmdbexplorer.movies.entities.MovieDetails
import pt.nunoneto.tmdbexplorer.utils.intent.IntentValues

class MovieDetailsPresenter(intent: Intent?, savedInstanceFragment: Bundle?, private var mView: MovieDetailsView) {

    private var mMovie: MovieDetails? = null

    companion object {
        const val BUNDLE_KEY_MOVIE: String = "bundle_key_movie"
    }

    init {
        if (savedInstanceFragment != null) {
            mMovie = savedInstanceFragment.getParcelable(BUNDLE_KEY_MOVIE)
        }

        if (mMovie != null) {
            mView.onMovieLoaded(mMovie!!)

        } else if (intent != null) {
            var movieId = intent.getIntExtra(IntentValues.KEY_MOVIE_ID, -1)
            loadMovieDetails(movieId.toString())
        }
    }

    fun saveState(savedInstance: Bundle?) {
        if (savedInstance == null) {
            return
        }

        savedInstance.putParcelable(BUNDLE_KEY_MOVIE, mMovie)
    }

    // Private Methods

    private fun loadMovieDetails(movieId: String) {
        MovieManager.getMovieDetails(movieId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<MovieDetails> {
                    override fun onComplete() {
                        // do nothing
                    }

                    override fun onSubscribe(d: Disposable) {
                        // do nothing
                    }

                    override fun onError(e: Throwable) {
                        mView.onError()
                    }

                    override fun onNext(t: MovieDetails) {
                        mView.onMovieLoaded(t)
                    }

                })
    }

    interface MovieDetailsView {
        fun onMovieLoaded(movie: MovieDetails)
        fun onError()
    }
}