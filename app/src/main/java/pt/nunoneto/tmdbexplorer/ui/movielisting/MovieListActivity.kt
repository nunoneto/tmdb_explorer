package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_movie_list.*
import pt.nunoneto.tmdb_explorer.R

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)
        setSupportActionBar(toolbar)
        toolbar.inflateMenu(R.menu.movie_list_options_menu)
    }
}
