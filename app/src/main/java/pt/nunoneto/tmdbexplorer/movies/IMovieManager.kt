package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MovieDetails
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage


interface IMovieManager {

    fun listTopRatedMovies(page: Int) : Observable<MoviesPage>

    fun searchMovies(query:String, page: Int) : Observable<MoviesPage>

    fun getMovieDetails(movieId: String) : Observable<MovieDetails>

}