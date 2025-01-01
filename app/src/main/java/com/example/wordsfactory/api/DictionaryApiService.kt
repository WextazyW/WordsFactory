package com.example.wordsfactory.api

import com.example.wordsfactory.model.WordResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("en/{word}")
    fun getWordMeaning(@Path("word") word: String): Call<List<WordResponse>>
}