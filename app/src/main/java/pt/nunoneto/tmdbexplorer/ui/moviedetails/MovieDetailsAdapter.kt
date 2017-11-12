package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.movie_details_list_item.view.*
import pt.nunoneto.tmdb_explorer.R

class MovieDetailsAdapter : RecyclerView.Adapter<MovieDetailsAdapter.ViewHolder>() {

    var mDetails: List<Pair<String, String>> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        var pair = mDetails[position]

        holder!!.title.text = pair.first
        holder.value.text = pair.second
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.movie_details_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mDetails.size
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val context = itemView!!.context!!

        val title = itemView!!.tv_details_title!!
        val value = itemView!!.tv_details_value!!
    }
}