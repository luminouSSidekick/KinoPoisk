package com.example.films.viewModel.films

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.films.data.FilmData
import com.example.films.entities.FilmInfo
import com.example.films.data.NetworkStatus
import com.example.films.data.GenreData
import com.example.films.repository.FilmsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class FilmsViewModel(
    private val filmsRepository: FilmsRepository
) : ViewModel() {

    val stateFlow get() = mutableScreenState.asStateFlow()
    val screenEventFlow get() = screenEventChannel.receiveAsFlow()

    private val screenEventChannel = Channel<FilmStateEvent>(Channel.UNLIMITED)
    private val mutableScreenState = MutableStateFlow<FilmsScreenState>(FilmsScreenState.Loading)

    private var selectedGenreId: String? = null
    private var genreList: MutableList<GenreData> = arrayListOf()
    private var filmList: MutableList<FilmInfo> = arrayListOf()

    init {
        getAllFilmsInfo()
    }

    fun onGenreClicked(id: String) {
        selectedGenreId = if (id == selectedGenreId) null else id

        val filmData = generateFilmData()

        mutableScreenState.value = FilmsScreenState.Success(
            filmData = filmData
        )
    }

    fun onFilmClicked(id: Int) {
        val filmInfo = filmList.first {
            it.id == id
        }

        sentEvent(FilmStateEvent.ShowFilmInfo(filmInfo))
    }

    fun onErrorRepeatButtonClick() {
        getAllFilmsInfo()
    }

    private fun getAllFilmsInfo() {
        mutableScreenState.value = FilmsScreenState.Loading

        CoroutineScope(Dispatchers.IO).launch {
            val resource = filmsRepository.getAllFilms()
            withContext(Dispatchers.Main) {
                when (resource.status) {
                    NetworkStatus.SUCCESS -> {
                        resource.data?.let {
                            setupData(resource.data.films)
                        }
                    }

                    NetworkStatus.ERROR -> mutableScreenState.value = FilmsScreenState.Error(
                        error = "Ошибка загрузки"
                    )
                }
            }
        }
    }

    private fun setupData(listFilmInfo: List<FilmInfo>) {
        generateGenreList(listFilmInfo)
        filmList.addAll(listFilmInfo)
        val filmData = generateFilmData()

        mutableScreenState.value = FilmsScreenState.Success(
            filmData = filmData
        )
    }

    private fun generateFilmData(): FilmData {
        return FilmData(
            selectGenreId = selectedGenreId,
            genreList = genreList,
            filmInfoList = generateFilmList()
        )
    }

    private fun generateFilmList(): List<FilmInfo> {
        if (selectedGenreId == null) return filmList

        val selectGenre = genreList.first { it.id == selectedGenreId }
        return filmList.filter {
            it.genres?.contains(selectGenre.text.replaceFirstChar { char -> char.lowercase() })
                ?: false
        }
    }


    private fun generateGenreList(
        filmsInfo: List<FilmInfo>
    ) {

        genreList.addAll(
            filmsInfo.map { it ->
                it.genres!!.map { item ->
                    GenreData(
                        id = UUID.randomUUID().toString(),
                        text = item.replaceFirstChar { char -> char.uppercaseChar() }
                    )
                }
            }.flatten().distinct().sortedBy { it.text }
        )
    }

    private fun sentEvent(event: FilmStateEvent) {
        viewModelScope.launch { screenEventChannel.send(event) }
    }
}