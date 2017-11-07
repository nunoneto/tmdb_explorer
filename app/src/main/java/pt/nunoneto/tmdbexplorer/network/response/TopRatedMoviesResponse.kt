package pt.nunoneto.tmdbexplorer.network.response


class TopRatedMoviesResponse (val data: TopRatedMoviesResponseData) {

    class TopRatedMoviesResponseData (
            val page:Int,
            val results: List<TopRatedMovie>
    )

    class TopRatedMovie (
            val id: Long,
            val title: String,
            val poster_path: String,
            val adult: Boolean,
            val overview: String,
            val release_date: String,
            val original_title: String,
            val original_language: String,
            val popularity: Double,
            val vote_count: Int,
            val vote_average: Double
    )
}

