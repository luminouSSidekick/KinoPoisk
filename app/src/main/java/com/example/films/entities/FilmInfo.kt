package com.example.films.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmInfo(
    val id: Int,
    val localized_name: String?,
    val name: String?,
    val year: Int?,
    val rating: Float?,
    val image_url: String?,
    val description: String?,
    val genres: List<String>?
): Parcelable