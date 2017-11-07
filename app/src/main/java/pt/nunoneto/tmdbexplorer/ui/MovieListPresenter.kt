package pt.nunoneto.tmdbexplorer.ui

import pt.nunoneto.tmdbexplorer.movies.Movie
import pt.nunoneto.tmdbexplorer.movies.MovieManager
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
        MovieManager.getInstance()
                .listTopRatedMoies()
                .subscribe({ topRatedMovies: List<Movie>? ->

                    if (topRatedMovies == null || topRatedMovies.isEmpty()) {
                        //TODO handle failure

                    } else {
                        mView.onMoviesLoaded(topRatedMovies)
                    }

                })
    }

    /** Methods from {@link IConnectivityEvent} **/

    override fun onConnectivityChanged(connected: Boolean) {

    }

    interface Callback {
        fun onMoviesLoaded(movies: List<Movie>)
    }
}