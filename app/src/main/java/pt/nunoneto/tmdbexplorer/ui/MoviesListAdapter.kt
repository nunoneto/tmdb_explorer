package pt.nunoneto.tmdbexplorer.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.movies.Movie

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    var movieList: List<Movie> = ArrayList()

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }
}