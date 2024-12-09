package com.example.films.viewModel.films

import com.example.films.data.FilmData

sealed interface FilmsScreenState {
    data object Loading : FilmsScreenState
    data class Success(val filmData: FilmData) : FilmsScreenState
    data class Error(val error: String) : FilmsScreenState
}

