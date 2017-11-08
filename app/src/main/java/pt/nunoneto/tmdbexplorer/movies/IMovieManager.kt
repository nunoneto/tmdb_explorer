package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage


interface IMovieManager {

    fun listTopRatedMovies(page: Int) : Observable<MoviesPage>

    interface IMovieListCallback {
        fun onMoviesLoaded(movies: List<Movie>, page: Int)
        fun onError()
    }
}