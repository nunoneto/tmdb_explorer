package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pt.nunoneto.tmdbexplorer.network.ServiceHelper

class MovieManager : IMovieManager {

    companion object {

        lateinit var managerInstance: MovieManager

        fun getInstance() : MovieManager {
            if (managerInstance == null) {
                managerInstance = MovieManager()
            }

            return managerInstance
        }
    }

    override fun listTopRatedMoies(): Observable<List<Movie>> {
        return ServiceHelper.getApi().getTopRatedMovies(0)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map({
                    t -> Observable.fromArray(t.data.results)
                        .flatMapIterable { t -> t }
                        .map { t -> Movie.fromResponse(t) }
                        .toList().blockingGet()
                })
    }
}
