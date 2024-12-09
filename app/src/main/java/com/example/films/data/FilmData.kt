package com.example.films.data

import com.example.films.entities.FilmInfo

data class FilmData(
    val selectGenreId: String?,
    val genreList: List<GenreData>,
    val filmInfoList: List<FilmInfo>
)

