package pt.nunoneto.tmdbexplorer.ui

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.Movie

class MovieListFragment : Fragment(), MovieListPresenter.Callback {

    private lateinit var mPresenter: MovieListPresenter
    private lateinit var mAdapter: MoviesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mPresenter = MovieListPresenter(this)
        setUiComponents()
    }

    private fun setUiComponents() {
        var recyclerView = view!!.findViewById<RecyclerView>(R.id.rv_movies)
        recyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter = MoviesListAdapter()
        recyclerView.adapter = mAdapter
    }

    /** Methods from MovieListPresenter.Callback **/

    override fun onMoviesLoaded(movies: List<Movie>) {
        mAdapter.movieList = movies
        mAdapter.notifyDataSetChanged()
    }

    companion object {

        fun newInstance() : MovieListFragment {
            return MovieListFragment()
        }
    }
}
