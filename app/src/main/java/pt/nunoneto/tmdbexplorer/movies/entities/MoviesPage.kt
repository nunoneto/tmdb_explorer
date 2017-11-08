package pt.nunoneto.tmdbexplorer.movies.entities

import pt.nunoneto.tmdbexplorer.config.entities.ImageConfig

class MoviesPage (val page:Int, val movies: List<Movie>, val config: ImageConfig)