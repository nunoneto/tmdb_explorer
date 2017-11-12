package pt.nunoneto.tmdbexplorer.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ServiceHelper {

    var apiService: ITMDBApiServices = Factory.createAPI()

    /**
     * Factory responsible for initializing the api services and respective dependencies
     */
    class Factory {

        companion object {

            private const val BASE_URL: String = "https://api.themoviedb.org/3/"
            private const val API_KEY: String = "83d01f18538cb7a275147492f84c3698"

            fun createAPI() : ITMDBApiServices {
                val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(createHTTPClient())
                        .build()

                return retrofit.create(ITMDBApiServices::class.java)
            }

            private fun createHTTPClient() : OkHttpClient {
                return OkHttpClient.Builder()
                        .addInterceptor(getApiKeyInterceptor())
                        .addNetworkInterceptor(StethoInterceptor())
                        .build()
            }

            private fun getApiKeyInterceptor() : Interceptor {
                return Interceptor { chain ->
                    val original = chain!!.request()
                    val originalHttpUrl = original.url()

                    // Add API key
                    var url = originalHttpUrl.newBuilder()

                            .addQueryParameter("api_key", API_KEY)
                            .build()

                    val requestBuilder = original.newBuilder()
                            .url(url)

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
            }
        }
    }
}