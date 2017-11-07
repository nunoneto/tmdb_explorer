package pt.nunoneto.tmdbexplorer.movies

import io.reactivex.Observable

interface IMovieManager {

    fun listTopRatedMoies() : <List<Movie>

}