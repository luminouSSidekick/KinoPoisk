package com.example.films.activitys

import com.example.films.entities.FilmInfo

sealed interface NavigationAction {

    class NavigationToFilmInfo(val filmInfo: FilmInfo) : NavigationAction
}