package pt.nunoneto.tmdbexplorer.config

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import pt.nunoneto.tmdbexplorer.config.entities.ImageConfig
import pt.nunoneto.tmdbexplorer.network.ServiceHelper

object ConfigCache {

    private var mImageConfig: ImageConfig? = null

    val imageConfig: Observable<ImageConfig>
        get() {
            if (mImageConfig == null) {
                val obs = ServiceHelper.apiService
                        .getConfigurations()
                        .subscribeOn(Schedulers.newThread())

                mImageConfig = obs.map { (images) ->
                    ImageConfig(
                            images?.posterSizes,
                            images?.backdropSizes,
                            images?.baseUrl)
                }.blockingSingle()
            }

            return Observable.just(mImageConfig)
        }

    fun getPosterBasePath() : Observable<String> {
        return imageConfig.map { t: ImageConfig ->
            if (t.baseUrl != null && t.posterSizes != null && t.posterSizes.size >= 3) {
                t.baseUrl.plus(t.posterSizes[2])
            } else {
                ""
            }
        }
    }

    fun getBackDropBasePath() : Observable<String> {
        return imageConfig.map { t: ImageConfig ->
            if (t.baseUrl != null && t.backdropSizes != null) {
                t.baseUrl + t.backdropSizes.last()
            } else {
                ""
            }
        }
    }
}
