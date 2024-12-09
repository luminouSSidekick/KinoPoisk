package com.example.films.data

data class GenreData(
    val isSelected: Boolean = false ,
    val id: String,
    val text: String
) {
    override fun equals(
        other: Any?
    ): Boolean {
        if (other !is GenreData) return false

        return text == other.text && isSelected == other.isSelected
    }

    override fun hashCode(): Int {
        return text.hashCode()
    }
}