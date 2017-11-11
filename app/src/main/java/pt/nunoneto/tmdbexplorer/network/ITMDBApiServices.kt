package pt.nunoneto.tmdbexplorer.network

import io.reactivex.Observable
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponse
import pt.nunoneto.tmdbexplorer.network.response.config.ConfigurationResponse
import pt.nunoneto.tmdbexplorer.network.response.details.MovieDetailsResponse
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ITMDBApiServices {

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Observable<MovieListResponse>

    @GET("configuration")
    fun getConfigurations(): Observable<ConfigurationResponse>

    @GET("search/movie")
    fun searchMovies(@Query("query") query:String, @Query("page") page:Int): Observable<MovieListResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(@Path("movieId") movieId: String) : Observable<MovieDetailsResponse>
}