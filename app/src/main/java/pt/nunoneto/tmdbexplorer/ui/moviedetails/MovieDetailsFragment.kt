package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_movie_details.*
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.entities.MovieDetails

class MovieDetailsFragment : Fragment(), MovieDetailsPresenter.MovieDetailsView {

    private lateinit var mPresenter: MovieDetailsPresenter
    private var mAdapter: MovieDetailsAdapter = MovieDetailsAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUiComponents()
        mPresenter = MovieDetailsPresenter(activity.intent, savedInstanceState, this)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mPresenter.saveState(outState)
    }

    private fun setUiComponents() {
        setupToolbar()
        setupRecycler()
    }

    private fun setupRecycler() {
        rv_movie_details.layoutManager = LinearLayoutManager(context)
        rv_movie_details.adapter = mAdapter
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(null)
        toolbar.navigationIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { activity.finish() }
    }

    /** Methods from {@link MovieDetailsView} **/

    override fun onMovieLoaded(movie: MovieDetails) {
        toolbar.title = movie.title
        toolbar.subtitle = movie.tagline

        var imagePath = movie.backdropBasePath + movie.backdropPath
        if (TextUtils.isEmpty(movie.backdropPath)) {
            imagePath = movie.posterBasePath + movie.posterPath
        }

        Glide.with(context)
                .load(imagePath)
                .apply(RequestOptions().dontTransform())
                .apply(RequestOptions().downsample(DownsampleStrategy.NONE))
                .into(iv_movie_poster)

        var details = ArrayList<Pair<String, String>>()

        details.add(Pair(getString(R.string.details_overview),movie.overview))

        val popularity = Math.round(movie.popularity)
        details.add(Pair(getString(R.string.details_popularity),
                getString(R.string.details_popularity_value, popularity.toString())))

        if (movie.voteAverage > 0 && movie.voteCount > 0) {
            details.add(Pair(getString(R.string.details_voting),
                    getString(R.string.details_voting_value, movie.voteAverage.toString(), movie.voteCount.toString())))
        }

        details.add(Pair(getString(R.string.details_runtime),
                getString(R.string.details_runtime_value, movie.runtime.toString())))

        if (movie.budget > 0 && movie.revenue > 0) {
            details.add(Pair(getString(R.string.details_budget),
                    getString(R.string.details_budget_details, movie.budget.toString(), movie.revenue.toString())))
        }

        details.add(Pair(getString(R.string.details_date), movie.releaseDate))

        mAdapter.mDetails = details
        mAdapter.notifyDataSetChanged()
    }

    override fun onError() {
        Toast.makeText(context, R.string.movie_details_load_error, Toast.LENGTH_SHORT).show()
        activity.finish()
    }

    companion object {

        fun newInstance() : MovieDetailsFragment {
            var fragment = MovieDetailsFragment()
            return fragment
        }
    }
}
