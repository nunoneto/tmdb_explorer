package pt.nunoneto.tmdbexplorer.network.response.details

import android.os.Parcel
import android.os.Parcelable

data class GenresItem(val name: String = "",
                      val id: Int = 0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenresItem> {
        override fun createFromParcel(parcel: Parcel): GenresItem {
            return GenresItem(parcel)
        }

        override fun newArray(size: Int): Array<GenresItem?> {
            return arrayOfNulls(size)
        }
    }
}