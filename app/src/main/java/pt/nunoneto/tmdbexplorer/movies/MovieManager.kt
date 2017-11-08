package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import pt.nunoneto.tmdbexplorer.config.ConfigCache
import pt.nunoneto.tmdbexplorer.config.entities.ImageConfig
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.network.ServiceHelper
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponse
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem


object MovieManager : IMovieManager  {

    override fun listTopRatedMovies(page: Int) : Observable<MoviesPage> {
        var configObs = ConfigCache.imageConfig
        var topRatedMoviesObs = getTopRatedMovies(page)

        return Observable.zip(
                configObs, topRatedMoviesObs,
                BiFunction<ImageConfig, List<Movie>, MoviesPage> {
                    imageConfig, movies -> MoviesPage(1, movies, imageConfig)
                })
    }

    fun getTopRatedMovies(page: Int): Observable<List<Movie>> {
        return ServiceHelper.apiService
                .getTopRatedMovies(page)
                .subscribeOn(Schedulers.newThread())
                .flatMapIterable { t: MovieListResponse -> t.results }
                .map { t: MovieListResponseItem -> Movie.fromResponse(t) }
                .toList().toObservable()
    }

}
