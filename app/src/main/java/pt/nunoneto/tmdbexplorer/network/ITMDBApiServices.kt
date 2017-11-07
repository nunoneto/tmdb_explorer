package pt.nunoneto.tmdbexplorer.network

import io.reactivex.Observable
import pt.nunoneto.tmdbexplorer.network.response.TopRatedMoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ITMDBApiServices {

    @GET("/movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int) : Observable<TopRatedMoviesResponse>

}