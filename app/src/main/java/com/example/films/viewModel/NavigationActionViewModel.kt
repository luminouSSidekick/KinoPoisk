package com.example.films.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.activitys.NavigationAction
import com.example.films.entities.FilmInfo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationActionViewModel : ViewModel() {

    private val screenEventChanel = Channel<NavigationAction>(Channel.UNLIMITED)
    val screenEventFlow get() = screenEventChanel.receiveAsFlow()

    fun navigationToFilmInfoCreation(
        filmInfo: FilmInfo
    ) {
        val action = NavigationAction.NavigationToFilmInfo(filmInfo)
        viewModelScope.launch { screenEventChanel.send(action) }
    }
}