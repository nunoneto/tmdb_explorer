package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_list_item.view.*
import pt.nunoneto.tmdb_explorer.R
import pt.nunoneto.tmdbexplorer.config.entities.ImageConfig
import pt.nunoneto.tmdbexplorer.movies.entities.Movie
import java.text.SimpleDateFormat
import java.util.*

class MoviesListAdapter : RecyclerView.Adapter<MoviesListAdapter.ViewHolder>() {

    var movieList: List<Movie> = ArrayList()
    lateinit var config: ImageConfig

    // Private
    private var sdf: SimpleDateFormat
    private var mCurrentYear: Int = -1

    init {
        sdf = SimpleDateFormat("yyyy-MM-dd")
        mCurrentYear = Calendar.getInstance().get(Calendar.YEAR)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.movie_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val movie = movieList[position]

        var posterUrl = config.baseUrl + config.posterSizes!![0] + movie.poster_path
        Glide.with(holder!!.context)
                .load(posterUrl)
                .into(holder.poster)

        holder.title.text = movie.title

        var releaseYear: Int = getYearFromDate(movie.release_date)
        holder.releaseYear.text = releaseYear.toString()

        if (mCurrentYear == releaseYear) {
            holder.releaseYear.setTextColor(Color.RED)

        } else {
            holder.releaseYear.setTextColor(Color.BLACK)
        }
    }

    private fun getYearFromDate(timestamp:String) : Int {
        var date: Date = sdf.parse(timestamp)

        var calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        return calendar.get(Calendar.YEAR);
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var context: Context = itemView.context

        val poster: ImageView = itemView.iv_movie_poster
        val title: TextView= itemView.tv_movie_title
        val releaseYear: TextView = itemView.tv_release_year
    }
}