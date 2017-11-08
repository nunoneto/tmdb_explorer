package pt.nunoneto.tmdbexplorer.ui.movielisting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MovieListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, MovieListFragment.newInstance())
                    .commitAllowingStateLoss()
        }
    }
}
