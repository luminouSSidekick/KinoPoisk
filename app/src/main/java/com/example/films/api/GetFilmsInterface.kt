package com.example.films.api

import com.example.films.entities.Films
import retrofit2.Response
import retrofit2.http.GET

interface GetFilmsInterface {

    @GET("sequeniatesttask/films.json")
    suspend fun getAllFilms(): Response<Films>

}