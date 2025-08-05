package ca.georgian.assignment2.data

data class Movie(
    val id: String = "",
    val title: String = "",
    val studio: String = "",
    val rating: String = "",
    val year: String = "",
    val poster: String = "",
    val description: String = "",
    val userId: String = ""
) {
    // Firebase requires empty constructor
    constructor() : this("", "", "", "", "", "", "", "")
}