package pt.nunoneto.tmdbexplorer.ui.movielisting

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import pt.nunoneto.tmdbexplorer.config.entities.ImageConfig
import pt.nunoneto.tmdbexplorer.movies.MovieManager
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.utils.ConnectivityUtils

class MovieListPresenter (callback: Callback) : ConnectivityUtils.IConnectivityEvent {

    private var mView: Callback = callback

    init {
        loadMovieList()
    }

    fun resume() {
        ConnectivityUtils.subscribeConnectivityEvents(this)
    }

    fun pause() {
        ConnectivityUtils.unsubscribeConnectivityEvents(this)
    }


    /** Private Methods **/

    private fun loadMovieList() {
        MovieManager.listTopRatedMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MoviesPage> {
                    override fun onSubscribe(d: Disposable) {
                        // do nothing
                    }

                    override fun onNext(moviesPage: MoviesPage) {
                        mView.onConfigurationLoaded(moviesPage.config)
                        mView.onMoviesLoaded(moviesPage.movies)
                    }

                    override fun onError(e: Throwable) {
                        mView.onMoviesLoaded(ArrayList())
                    }

                    override fun onComplete() {
                        // do nothing
                    }
                })
    }

    /** Methods from {@link IConnectivityEvent} **/

    override fun onConnectivityChanged(connected: Boolean) {

    }

    interface Callback {
        fun onMoviesLoaded(movies: List<Movie>)

        fun onConfigurationLoaded(config: ImageConfig);
    }
}