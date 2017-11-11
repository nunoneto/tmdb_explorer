package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.content.Context
import android.os.Bundle
import android.support.annotation.IntDef
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import pt.nunoneto.tmdbexplorer.movies.MovieManager
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.movies.entities.MoviesPage
import pt.nunoneto.tmdbexplorer.utils.ConnectivityUtils
import pt.nunoneto.tmdbexplorer.utils.intent.IntentHolder

class MovieListPresenter (mSavedInstantState: Bundle?, private var mView: MovieListView) : ConnectivityUtils.IConnectivityEvent{

    companion object {
        private const val BUNDLE_KEY_MOVIE_LIST: String = "bundle_key_move_list"
        private const val BUNDLE_KEY_PAGES: String = "bundle_key_pages"
        private const val BUNDLE_KEY_BASE_IMAGE_PATH: String = "bundle_key_base_image_path"
        private const val BUNDLE_KEY_LIST_MODE: String = "bundle_key_list_mode"
        private const val BUNDLE_KEY_PENDING_NEXT_PAGE: String = "bundle_key_pending_next_page"
        private const val BUNDLE_KEY_SEARCH_QUERY: String = "bundle_key_search_query"

        @IntDef(TOP_RATED, SEARCH)
        annotation class ListMode

        const val TOP_RATED: Long = 0
        const val SEARCH: Long = 1
    }

    private var mCurrentPage: Int = -1
    private var mSearchQuery: String = ""
    private var mBaseImagePath:String = ""
    private var mPendingNextPage: Boolean = false
    private var mMovieList: ArrayList<Movie> = ArrayList()
    private var mLastConnectivityState: Boolean = ConnectivityUtils.isConnected()

    @ListMode
    private var mListMode: Long = TOP_RATED

    init {
        restoreState(mSavedInstantState)

        if (mMovieList.size > 0) {
            mView.onConfigurationLoaded(mBaseImagePath)
            mView.onPageLoaded(mMovieList, mCurrentPage)

        } else {
            loadTopRatedMovies()
        }
    }

    fun resume() {
        checkConnectivity()

        ConnectivityUtils.subscribeConnectivityEvents(this)
    }

    fun pause() {
        ConnectivityUtils.unsubscribeConnectivityEvents(this)
    }

    fun loadNextPage() {
        if (!ConnectivityUtils.isConnected()) {
            mView.onLoadingError(mCurrentPage + 1)
            mPendingNextPage = true
            return
        }

        if (mListMode == SEARCH) {
            MovieManager.searchMovies(mSearchQuery, mCurrentPage + 1)
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
            return
        }

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

    fun searchMovies(query: String) {
        mCurrentPage = -1
        mPendingNextPage = false
        mListMode = SEARCH
        mSearchQuery = query

        MovieManager.searchMovies(mSearchQuery, 1)
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

    fun clearSearch() {
        if (mListMode != SEARCH) {
            return
        }

        mListMode = TOP_RATED
        mSearchQuery = ""
        mPendingNextPage = false
        mCurrentPage = -1
        loadTopRatedMovies()
    }

    fun goToMovieDetails(context: Context, movie:Movie) {
        var intent = IntentHolder.openMovieDetailsScreen(context, movie)
        context.startActivity(intent)
    }

    fun saveState(bundle: Bundle?) {
        if (bundle == null) {
            return
        }

        bundle.putParcelableArrayList(BUNDLE_KEY_MOVIE_LIST, mMovieList)
        bundle.putString(BUNDLE_KEY_BASE_IMAGE_PATH, mBaseImagePath)
        bundle.putInt(BUNDLE_KEY_PAGES, mCurrentPage)
        bundle.putLong(BUNDLE_KEY_LIST_MODE, mListMode)
        bundle.putBoolean(BUNDLE_KEY_PENDING_NEXT_PAGE, mPendingNextPage)
        bundle.putString(BUNDLE_KEY_SEARCH_QUERY, mSearchQuery)
    }

    /** Private Methods **/

    private fun loadTopRatedMovies() {
        if (!ConnectivityUtils.isConnected()) {
            mView.onLoadingError(mCurrentPage)
            return
        }

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

    private fun checkConnectivity() {
        var currentConnectivityState = ConnectivityUtils.isConnected()
        if (!mLastConnectivityState && currentConnectivityState) {
            mLastConnectivityState = currentConnectivityState
            loadTopRatedMovies()
            return
        }

        if (mPendingNextPage) {
            loadNextPage()
        }
    }

    private fun restoreState(bundle: Bundle?) {
        if (bundle == null) {
            return
        }

        mMovieList = bundle.getParcelableArrayList(BUNDLE_KEY_MOVIE_LIST)
        mBaseImagePath = bundle.getString(BUNDLE_KEY_BASE_IMAGE_PATH)
        mCurrentPage = bundle.getInt(BUNDLE_KEY_PAGES)
        mListMode = bundle.getLong(BUNDLE_KEY_LIST_MODE)
        mPendingNextPage = bundle.getBoolean(BUNDLE_KEY_PENDING_NEXT_PAGE)
        mSearchQuery = bundle.getString(BUNDLE_KEY_SEARCH_QUERY)
    }

    private fun onMoviePageLoaded(moviesPage: MoviesPage) {
        mMovieList.addAll(moviesPage.movies)
        mCurrentPage = moviesPage.page
        mBaseImagePath = moviesPage.imageBasePath

        mView.onConfigurationLoaded(mBaseImagePath)
        mView.onPageLoaded(moviesPage.movies, mCurrentPage)
    }

    private fun onMoviePageError() {
        mView.onLoadingError(mCurrentPage + 1)
    }

    /** Methods from {@link IConnectivityEvent} **/

    override fun onConnectivityChanged(connected: Boolean) {
        checkConnectivity()
    }

    interface MovieListView {
        fun onPageLoaded(movies: List<Movie>, page:Int)
        fun onConfigurationLoaded(imageBasePath: String)
        fun onLoadingError(page:Int)
    }
}