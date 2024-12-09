package com.example.films.repository

import com.example.films.data.Resource
import com.example.films.entities.Films

interface FilmsRepository {
    suspend fun getAllFilms(): Resource<Films>
}