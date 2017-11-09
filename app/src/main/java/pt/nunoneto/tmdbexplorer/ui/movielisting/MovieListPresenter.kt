package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.os.Bundle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import pt.nunoneto.tmdbexplorer.movies.MovieManager
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.utils.ConnectivityUtils

class MovieListPresenter (mSavedInstantState: Bundle?, var mView: Callback) : ConnectivityUtils.IConnectivityEvent {

    companion object {
        private const val BUNDLE_KEY_MOVIE_LIST: String = "bundle_key_move_list"
        private const val BUNDLE_KEY_PAGES: String = "bundle_key_pages"
        private const val BUNDLE_KEY_BAS_IMAGE_PATH: String = "bundle_key_base_image_path"
    }

    private var mMovieList: ArrayList<Movie> = ArrayList()
    private var mCurrentPage: Int = -1
    private lateinit var mBaseImagePath:String

    init {
        restoreState(mSavedInstantState)

        if (mMovieList.size > 0) {
            mView.onConfigurationLoaded(mBaseImagePath)
            mView.onPageLoaded(mMovieList, mCurrentPage)
            
        } else {
            loadMovieList()
        }
    }

    fun resume() {
        ConnectivityUtils.subscribeConnectivityEvents(this)
    }

    fun pause() {
        ConnectivityUtils.unsubscribeConnectivityEvents(this)
    }

    fun loadNextPage() {
        MovieManager.listTopRatedMovies(mCurrentPage+1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Observer<MoviesPage> {
                    override fun onError(e: Throwable) {
                        onMoviePageError()
                    }

                    override fun onComplete() {
                        // do nothing
                    }

                    override fun onNext(t: MoviesPage) {
                        onMoviePageLoaded(t)
                    }

                    override fun onSubscribe(d: Disposable) {
                        // do nothing
                    }
                })
    }

    fun saveState(bundle: Bundle?) {
        if (bundle == null) {
            return
        }

        bundle.putParcelableArrayList(BUNDLE_KEY_MOVIE_LIST, mMovieList)
        bundle.putString(BUNDLE_KEY_BAS_IMAGE_PATH, mBaseImagePath)
        bundle.putInt(BUNDLE_KEY_PAGES, mCurrentPage)
    }

    /** Private Methods **/

    private fun restoreState(bundle: Bundle?) {
        if (bundle == null) {
            return
        }

        mMovieList = bundle.getParcelableArrayList(BUNDLE_KEY_MOVIE_LIST)
        mBaseImagePath = bundle.getString(BUNDLE_KEY_BAS_IMAGE_PATH)
        mCurrentPage = bundle.getInt(BUNDLE_KEY_PAGES)
    }

    private fun loadMovieList() {
        MovieManager.listTopRatedMovies(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<MoviesPage> {
                    override fun onSubscribe(d: Disposable) {
                        // do nothing
                    }

                    override fun onNext(moviesPage: MoviesPage) {
                        onMoviePageLoaded(moviesPage)
                    }

                    override fun onError(e: Throwable) {
                        onMoviePageError()
                    }

                    override fun onComplete() {
                        // do nothing
                    }
                })
    }

    private fun onMoviePageLoaded(moviesPage: MoviesPage) {
        mMovieList.addAll(moviesPage.movies)
        mCurrentPage = moviesPage.page
        mBaseImagePath = moviesPage.imageBasePath

        mView.onConfigurationLoaded(mBaseImagePath)
        mView.onPageLoaded(moviesPage.movies, mCurrentPage)
    }

    private fun onMoviePageError() {
        //TODO
    }

    /** Methods from {@link IConnectivityEvent} **/

    override fun onConnectivityChanged(connected: Boolean) {

    }

    interface Callback {
        fun onPageLoaded(movies: List<Movie>, page:Int)
        fun onConfigurationLoaded(imageBasePath: String)
    }
}