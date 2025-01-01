package com.example.wordsfactory.model

data class WordResponse(
    val word: String,
    val phonetic: String?,
    val meanings: List<Meaning>
)
