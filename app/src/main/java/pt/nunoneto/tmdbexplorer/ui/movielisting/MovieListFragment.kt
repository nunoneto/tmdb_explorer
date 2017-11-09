package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import android.view.MenuInflater
import pt.nunoneto.tmdbexplorer.ui.EndlessRecyclerViewScrollListener


class MovieListFragment : Fragment(), MovieListPresenter.Callback {

    private lateinit var mPresenter: MovieListPresenter
    private lateinit var mAdapter: MoviesListAdapter
    private lateinit var mScrollListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUiComponents()
        mPresenter = MovieListPresenter(savedInstanceState,this)
    }

    private fun setUiComponents() {
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        var toolbar = view!!.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setupRecyclerView() {
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.rv_movies)

        // layout manager
        var layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        // adapter
        mAdapter = MoviesListAdapter()
        recyclerView.adapter = mAdapter

        // scroll listener
        mScrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                mPresenter.loadNextPage()
            }
        }

        recyclerView.addOnScrollListener(mScrollListener)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mPresenter.saveState(outState!!)
    }

    /** Methods from MovieListPresenter.Callback **/

    override fun onPageLoaded(movies: List<Movie>, page:Int) {
        if (page > 1) {
            var curentLength = mAdapter.movieList.size -1
            mAdapter.movieList = mAdapter.movieList.plus(movies)
            mAdapter.notifyItemRangeChanged(curentLength, mAdapter.movieList.size-1)

        } else {
            mAdapter.movieList = movies
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onConfigurationLoaded(imageBasePath: String) {
        mAdapter.imageBasePath = imageBasePath
    }

    companion object {

        fun newInstance() : MovieListFragment {
            return MovieListFragment()
        }
    }
}
