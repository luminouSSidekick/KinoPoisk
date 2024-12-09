package com.example.films.viewModel.filmInfo

import androidx.lifecycle.ViewModel
import com.example.films.data.NetworkStatus
import com.example.films.entities.FilmInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FilmDetailViewModel(
    val film: FilmInfo
) : ViewModel() {

    private val mutableScreenState = MutableStateFlow<FilmInfoScreenState>(FilmInfoScreenState.Initial)
    val stateFlow get() = mutableScreenState.asStateFlow()

    init {
        setupData()
    }

    private fun setupData() {

        mutableScreenState.value = FilmInfoScreenState.Success(
            query = NetworkStatus.SUCCESS.toString(),
            filmInfo = film
        )
    }
}