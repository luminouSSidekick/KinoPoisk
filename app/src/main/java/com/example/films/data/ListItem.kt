package com.example.films.data

data class ListItem(
    var type: ListItemType? = null,
    var id: String? = null,
    var data: Any?,
    var settings: Settings = Settings(),
    var errors: Errors = Errors()
)

class Errors(var message: String?) {
    constructor() : this(null)
}


data class Settings(
    val marginTopInDp: Int? = null,
    val marginBottomInDp: Int? = null,
    val marginStartInDp: Int? = null,
    val marginEndInDp: Int? = null,
    val textStyle: Int? = null,
    val textSize: Int? = null,
)