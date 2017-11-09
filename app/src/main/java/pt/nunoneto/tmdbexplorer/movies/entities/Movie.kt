package pt.nunoneto.tmdbexplorer.movies.entities

import android.os.Parcel
import android.os.Parcelable
import pt.nunoneto.tmdbexplorer.network.response.movielist.MovieListResponseItem

class Movie (
        val id: Int,
        val title: String,
        val poster_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String,
        val popularity: Double) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(poster_path)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeString(overview)
        parcel.writeString(release_date)
        parcel.writeDouble(popularity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }

        fun fromResponse(response: MovieListResponseItem) : Movie {
            return Movie(response.id, response.title, response.posterPath, response.adult,
                    response.overview, response.releaseDate, response.popularity)
        }
    }
}