package com.example.wordsfactory.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wordsfactory.api.DictionaryApiService
import com.example.wordsfactory.model.WordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryViewModel : ViewModel() {
    private val _wordData = MutableLiveData<WordResponse>()
    val wordData: LiveData<WordResponse> get() = _wordData

    fun fetchWordMeaning(api: DictionaryApiService, word: String) {
        api.getWordMeaning(word).enqueue(object : Callback<List<WordResponse>> {
            override fun onResponse(call: Call<List<WordResponse>>, response: Response<List<WordResponse>>) {
                if (response.isSuccessful && response.body() != null) {
                    _wordData.postValue(response.body()!![0])
                }
            }

            override fun onFailure(call: Call<List<WordResponse>>, t: Throwable) {
                // Handle error
            }
        })
    }
}