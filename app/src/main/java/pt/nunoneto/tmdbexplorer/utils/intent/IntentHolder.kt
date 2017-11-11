package pt.nunoneto.tmdbexplorer.utils.intent

import android.content.Context
import android.content.Intent
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import pt.nunoneto.tmdbexplorer.ui.moviedetails.MovieDetailsActivity

class IntentHolder {

    companion object {

        fun openMovieDetailsScreen(context: Context, movie: Movie) : Intent{
            var intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(IntentValues.KEY_MOVIE_ID, movie.id)
            return intent
        }

    }

}