package pt.nunoneto.tmdbexplorer.movies.entities

import android.os.Parcel
import android.os.Parcelable
import pt.nunoneto.tmdbexplorer.network.response.details.MovieDetailsResponse

data class MovieDetails (val imdbId: String = "",
                         val video: Boolean = false,
                         val title: String = "",
                         val backdropPath: String = "",
                         val revenue: Int = 0,
                         val popularity: Double = 0.0,
                         val id: Int = 0,
                         val voteCount: Int = 0,
                         val budget: Int = 0,
                         val overview: String = "",
                         val originalTitle: String = "",
                         val runtime: Int = 0,
                         val posterPath: String? = null,
                         val releaseDate: String = "",
                         val voteAverage: Double = 0.0,
                         var imageBasePath: String = "") : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readDouble(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imdbId)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(backdropPath)
        parcel.writeInt(revenue)
        parcel.writeDouble(popularity)
        parcel.writeInt(id)
        parcel.writeInt(voteCount)
        parcel.writeInt(budget)
        parcel.writeString(overview)
        parcel.writeString(originalTitle)
        parcel.writeInt(runtime)
        parcel.writeString(posterPath)
        parcel.writeString(releaseDate)
        parcel.writeDouble(voteAverage)
        parcel.writeString(imageBasePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDetails> {
        override fun createFromParcel(parcel: Parcel): MovieDetails {
            return MovieDetails(parcel)
        }

        override fun newArray(size: Int): Array<MovieDetails?> {
            return arrayOfNulls(size)
        }

        fun fromResponse(response: MovieDetailsResponse) : MovieDetails{
            return MovieDetails(response.imdbId, response.video, response.title, response.backdropPath,
                    response.revenue, response.popularity, response.id, response.voteCount,
                    response.budget, response.overview, response.originalTitle, response.runtime,
                    response.posterPath, response.releaseDate, response.voteAverage, "")
        }
    }

}