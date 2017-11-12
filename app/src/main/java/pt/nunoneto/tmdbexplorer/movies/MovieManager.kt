package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import io.reactivex.Observable.zip
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import pt.nunoneto.tmdbexplorer.config.ConfigCache
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MovieDetails
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.network.ServiceHelper
import pt.nunoneto.tmdbexplorer.network.response.details.MovieDetailsResponse
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponse
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem


object MovieManager : IMovieManager  {
    override fun listTopRatedMovies(page: Int) : Observable<MoviesPage> {
        val imagePathObs = ConfigCache.getPosterBasePath()
        val topRatedMoviesObs = getTopRatedMovies(page)

        return zip(
                imagePathObs, topRatedMoviesObs,
                BiFunction<String, MoviesPage, MoviesPage> {
                    imageBasePath, movies -> MoviesPage(movies.page, movies.movies, imageBasePath)
                })
    }

    override fun searchMovies(query: String, page: Int): Observable<MoviesPage> {
        val imagePathObs = ConfigCache.getPosterBasePath()
        val movieSearchObs = getMovieSearch(query, page)

        return zip(
                imagePathObs, movieSearchObs,
                BiFunction<String, MoviesPage, MoviesPage> {
                    imageBasePath, movies -> MoviesPage(movies.page, movies.movies, imageBasePath)
                })
    }

    override fun getMovieDetails(movieId: String): Observable<MovieDetails> {
        val posterPathObs = ConfigCache.getPosterBasePath()
        val backdropPathObs = ConfigCache.getBackDropBasePath()
        val movieDetailsObs = ServiceHelper.apiService.getMovieDetails(movieId)
                .subscribeOn(Schedulers.newThread())
                .map { t: MovieDetailsResponse -> MovieDetails.fromResponse(t) }

        return Observable.zip(
                posterPathObs,
                backdropPathObs,
                movieDetailsObs,
                Function3<String, String, MovieDetails, MovieDetails> { posterPath, backdropPath, movieDetails ->
                    movieDetails.backdropBasePath = backdropPath
                    movieDetails.posterBasePath = posterPath
                    movieDetails })
    }

    /** Private methods **/

    private fun getTopRatedMovies(page: Int): Observable<MoviesPage> {
        val obs = ServiceHelper.apiService
                .getTopRatedMovies(page)
                .subscribeOn(Schedulers.newThread())

        return obs.flatMapIterable { t: MovieListResponse -> t.results }
                .map { t: MovieListResponseItem -> Movie.fromResponse(t) }
                .toList()
                .map { t: MutableList<Movie> ->
                    MoviesPage(obs.map { t -> t.page }.blockingFirst(), t,"") }
                .toObservable()
    }

    private fun getMovieSearch(query: String, page: Int): Observable<MoviesPage> {
        val obs = ServiceHelper.apiService
                .searchMovies(query, page)
                .subscribeOn(Schedulers.newThread())

        return obs.flatMapIterable { t: MovieListResponse -> t.results }
                .map { t: MovieListResponseItem -> Movie.fromResponse(t) }
                .toList()
                .map { t: MutableList<Movie> ->
                    MoviesPage(obs.map { t -> t.page }.blockingFirst(), t,"") }
                .toObservable()
    }

}
