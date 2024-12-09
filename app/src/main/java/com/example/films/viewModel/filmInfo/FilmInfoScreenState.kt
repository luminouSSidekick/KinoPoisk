package com.example.films.viewModel.filmInfo

import com.example.films.entities.FilmInfo

interface FilmInfoScreenState {
    data object Initial : FilmInfoScreenState
    data class Success(val query: String, val filmInfo: FilmInfo) : FilmInfoScreenState

}