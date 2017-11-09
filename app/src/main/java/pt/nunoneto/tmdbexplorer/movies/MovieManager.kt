package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import pt.nunoneto.tmdbexplorer.config.ConfigCache
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.network.ServiceHelper
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponse
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem


object MovieManager : IMovieManager  {

    override fun listTopRatedMovies(page: Int) : Observable<MoviesPage> {
        var imagePathObs = ConfigCache.getPosterBasePath()
        var topRatedMoviesObs = getTopRatedMovies(page)

        return Observable.zip(
                imagePathObs, topRatedMoviesObs,
                BiFunction<String, MoviesPage, MoviesPage> {
                    imageBasePath, movies -> MoviesPage(movies.page, movies.movies, imageBasePath)
                })
    }

    /** Private methods **/

    private fun getTopRatedMovies(page: Int): Observable<MoviesPage> {
        var obs = ServiceHelper.apiService
                .getTopRatedMovies(page)
                .subscribeOn(Schedulers.newThread())

        return obs.flatMapIterable { t: MovieListResponse -> t.results }
                .map { t: MovieListResponseItem -> Movie.fromResponse(t) }
                .toList()
                .map { t: MutableList<Movie> ->
                    MoviesPage(obs.map { t -> t.page }.blockingFirst(), t,"") }
                .toObservable()
    }

}
