package pt.nunoneto.tmdbexplorer.movies

import pt.nunoneto.tmdbexplorer.network.response.TopRatedMoviesResponse

class Movie (
        val id: Long,
        val title: String,
        val poster_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String,
        val popularity: Double) {

    companion object {

        fun fromResponse(response: TopRatedMoviesResponse.TopRatedMovie) : Movie {
            return Movie(response.id, response.title, response.poster_path, response.adult,
                    response.overview, response.release_date, response.popularity)
        }
    }
}