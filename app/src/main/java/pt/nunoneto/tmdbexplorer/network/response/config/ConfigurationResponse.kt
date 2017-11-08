package pt.nunoneto.tmdbexplorer.network.response.config

import com.google.gson.annotations.SerializedName

data class ConfigurationResponse(val images: Images? = null,
                                 @SerializedName("change_keys")
                                 val changeKeys: List<String>? = null)