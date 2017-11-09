package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu

import kotlinx.android.synthetic.main.activity_main.*
import pt.nunoneto.tmdb_explorer.R

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.movie_list_options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
