package com.example.films.di

import com.example.films.api.GetFilmsInterface
import com.example.films.repository.FilmsRepository
import com.example.films.repository.GetFilmsImp
import com.example.films.viewModel.filmInfo.FilmDetailViewModel
import com.example.films.viewModel.films.FilmsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://s3-eu-west-1.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetFilmsInterface::class.java)
    }

    single<FilmsRepository> {
        GetFilmsImp(get())
    }

    viewModel {
        FilmsViewModel(get())
    }

    viewModel { params ->
        FilmDetailViewModel(
            film = params.get()
        )
    }
}