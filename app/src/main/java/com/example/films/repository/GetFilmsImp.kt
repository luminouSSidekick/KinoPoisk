package com.example.films.repository

import com.example.films.api.GetFilmsInterface
import com.example.films.data.Resource
import com.example.films.entities.Films

class GetFilmsImp(private val api: GetFilmsInterface) : FilmsRepository {

    override suspend fun getAllFilms(): Resource<Films> {
        return try {
            val response = api.getAllFilms()
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Неизвестная ошибка", null)
            } else {
                Resource.error("Ошибка загрузки", null)
            }
        } catch (e: Exception) {
            Resource.error("Ошибка загрузки", null)
        }
    }
}