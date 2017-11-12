package pt.nunoneto.tmdbexplorer.ui.moviedetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(android.R.id.content, MovieDetailsFragment.newInstance())
                    .commitAllowingStateLoss()
        }
    }
}
