package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.entities.Movie

class MovieDetailsFragment : Fragment(), MovieDetailsPresenter.MovieDetailsView {

    private lateinit var mPresenter: MovieDetailsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUiComponents()
        mPresenter = MovieDetailsPresenter(savedInstanceState, this)
    }

    private fun setUiComponents() {

    }

    /** Methods from {@link MovieDetailsView} **/

    override fun onMovieLoaded(movie: Movie) {

    }
}
