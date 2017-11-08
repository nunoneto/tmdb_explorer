package pt.nunoneto.tmdbexplorer.movies.entities

import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem

class Movie (
        val id: Int,
        val title: String,
        val poster_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String,
        val popularity: Double) {

    companion object {

        fun fromResponse(response: MovieListResponseItem) : Movie {
            return Movie(response.id, response.title, response.posterPath, response.adult,
                    response.overview, response.releaseDate, response.popularity)
        }
    }
}