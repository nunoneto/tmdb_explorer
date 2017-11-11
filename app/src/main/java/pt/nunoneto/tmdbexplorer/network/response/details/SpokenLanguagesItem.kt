package pt.nunoneto.tmdbexplorer.network.response.details

import com.google.gson.annotations.SerializedName

data class SpokenLanguagesItem(val name: String = "",
                               @SerializedName("iso_639_1")
                               val iso: String = "")