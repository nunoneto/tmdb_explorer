package pt.nunoneto.tmdbexplorer.network.response.movielist

import com.google.gson.annotations.SerializedName

data class MovieListResponse(val page: Int = 0,
                             @SerializedName("total_pages")
                             val totalPages: Int = 0,
                             val results: List<MovieListResponseItem>? = null,
                             @SerializedName("total_results")
                             val totalResults: Int = 0)