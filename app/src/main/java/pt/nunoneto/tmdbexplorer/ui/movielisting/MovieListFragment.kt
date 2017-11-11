package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.app.SearchManager
import android.content.Context
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_movie_list.*
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.ui.EndlessRecyclerViewScrollListener
import pt.nunoneto.tmdbexplorer.utils.Utils


class MovieListFragment : Fragment(), MovieListPresenter.MovieListView,
        MoviesListAdapter.IMovieClickListener,
        SearchView.OnQueryTextListener {

    private lateinit var mPresenter: MovieListPresenter
    private lateinit var mAdapter: MoviesListAdapter
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mScrollListener: EndlessRecyclerViewScrollListener

    // Error Views
    private lateinit var mErrorView:ViewGroup

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUiComponents()
        mPresenter = MovieListPresenter(savedInstanceState,this)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.resume()
    }

    override fun onPause() {
        super.onPause()

        mPresenter.pause()
    }

    private fun setUiComponents() {
        setupToolbar()
        setupRecyclerView()
        setupErrorView()
    }

    private fun setupErrorView() {
        mErrorView = view!!.findViewById<LinearLayout>(R.id.ll_error_view)
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(null)

        var menuItem = activity.toolbar.menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView

        val searchManager = activity.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity.componentName))
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = context.getString(R.string.search_menu_title)
        searchView.maxWidth = Integer.MAX_VALUE
    }

    private fun setupRecyclerView() {
        mRecyclerView = view!!.findViewById(R.id.rv_movies)

        // layout manager
        var layoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = layoutManager

        // adapter
        mAdapter = MoviesListAdapter(this)
        mRecyclerView.adapter = mAdapter

        // scroll listener
        mScrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPresenter.loadNextPage()
            }
        }

        mRecyclerView.addOnScrollListener(mScrollListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mPresenter.saveState(outState!!)
    }

    /** Methods from MovieListPresenter.MovieListView **/

    override fun onPageLoaded(movies: List<Movie>, page:Int) {
        mRecyclerView.visibility = View.VISIBLE
        mErrorView.visibility = View.GONE

        if (page > 1) {
            var curentLength = mAdapter.movieList.size -1
            mAdapter.movieList = mAdapter.movieList.plus(movies)
            mAdapter.notifyItemRangeChanged(curentLength, mAdapter.movieList.size - 1)

        } else {
            mAdapter.movieList = movies
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onConfigurationLoaded(imageBasePath: String) {
        mAdapter.imageBasePath = imageBasePath
    }

    override fun onLoadingError(page: Int) {
        if (page > 1) {
            Snackbar.make(view!!, R.string.movie_list_page_load_error, Snackbar.LENGTH_LONG).show()

        } else {
            mRecyclerView.visibility = View.GONE
            mErrorView.visibility = View.VISIBLE
            Glide.with(context)
                    .load(R.drawable.shrug_emoji)
                    .into(mErrorView.findViewById(R.id.iv_error_image))

            mErrorView.findViewById<TextView>(R.id.tv_error_text).text = getString(R.string.movie_list_load_error)
        }
    }

    /** Methods from {@link SearchView.OnQueryTextListener} **/

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (TextUtils.isEmpty(query)) {
            Snackbar.make(view!!,"", Snackbar.LENGTH_SHORT).show()
            Utils.hideKeyboard(view)
            return false
        }

        mPresenter.searchMovies(query!!)
        Utils.hideKeyboard(view)
        activity.toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_black_36dp)
        activity.toolbar.setNavigationOnClickListener {
            activity.toolbar.navigationIcon = null

            var searchItem = activity.toolbar.menu.findItem(R.id.action_search)
            searchItem.collapseActionView()

            val searchView = searchItem.actionView as SearchView
            searchView.setQuery("", false)
            searchView.clearFocus()

            mPresenter.clearSearch()
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    /** Methods from {@link MoviesListAdapter.IMovieClickListener} **/

    override fun onMovieClicked(movie: Movie) {
        mPresenter.goToMovieDetails(context, movie)
    }
}
