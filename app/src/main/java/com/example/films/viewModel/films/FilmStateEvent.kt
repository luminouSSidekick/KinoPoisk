package com.example.films.viewModel.films

import com.example.films.entities.FilmInfo

sealed interface FilmStateEvent {

    data class ShowFilmInfo(val filmInfo: FilmInfo) : FilmStateEvent

}