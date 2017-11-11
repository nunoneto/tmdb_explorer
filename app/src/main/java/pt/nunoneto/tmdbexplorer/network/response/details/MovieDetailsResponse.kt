package pt.nunoneto.tmdbexplorer.network.response.details

import com.google.gson.annotations.SerializedName
import pt.nunoneto.tmdbexplorer.movies.entities.MovieDetails

data class MovieDetailsResponse(@SerializedName("original_language")
                                val originalLanguage: String = "",
                                @SerializedName("imdb_id")
                                val imdbId: String = "",
                                val video: Boolean = false,
                                val title: String = "",
                                @SerializedName("backdrop_path")
                                val backdropPath: String = "",
                                val revenue: Int = 0,
                                val genres: List<GenresItem>? = null,
                                val popularity: Double = 0.0,
                                @SerializedName("production_countries")
                                val productionCountries: List<ProductionCountriesItem>? = null,
                                val id: Int = 0,
                                @SerializedName("vote_count")
                                val voteCount: Int = 0,
                                val budget: Int = 0,
                                val overview: String = "",
                                @SerializedName("original_title")
                                val originalTitle: String = "",
                                val runtime: Int = 0,
                                @SerializedName("poster_path")
                                val posterPath: String? = null,
                                @SerializedName("spoken_languages")
                                val spokenLanguages: List<SpokenLanguagesItem>? = null,
                                @SerializedName("production_companies")
                                val productionCompanies: List<ProductionCompaniesItem>? = null,
                                @SerializedName("release_date")
                                val releaseDate: String = "",
                                @SerializedName("vote_average")
                                val voteAverage: Double = 0.0,
                                val tagline: String = "",
                                val adult: Boolean = false,
                                val homepage: String = "",
                                val status: String = "")