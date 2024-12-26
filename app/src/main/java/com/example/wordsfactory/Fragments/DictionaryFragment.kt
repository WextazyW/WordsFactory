package com.example.wordsfactory.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.wordsfactory.R
import com.example.wordsfactory.databinding.FragmentDictionaryBinding
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException


class DictionaryFragment : Fragment() {

    private lateinit var editTextWord: EditText
    private lateinit var textViewMeaning: TextView
    private lateinit var buttonSearch: Button
    private lateinit var buttonAddToDictionary: Button

    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dictionary, container, false)

        editTextWord = view.findViewById(R.id.editTextWord)
        textViewMeaning = view.findViewById(R.id.textViewMeaning)
        buttonSearch = view.findViewById(R.id.buttonSearch)
        buttonAddToDictionary = view.findViewById(R.id.buttonAddToDictionary)

        buttonSearch.setOnClickListener {
            val word = editTextWord.text.toString()
            if (word.isNotEmpty()) {
                fetchMeaning(word)
            } else {
                Toast.makeText(requireContext(), "Введите слово", Toast.LENGTH_SHORT).show()
            }
        }

        buttonAddToDictionary.setOnClickListener {
            saveWord(editTextWord.text.toString(), textViewMeaning.text.toString())
        }

        return view
    }

    private fun fetchMeaning(word: String) {
        val request = Request.Builder()
            .url("https://api.dictionaryapi.dev/api/v2/entries/en/$word")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: okhttp3.Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    responseData?.let {
                        parseJson(it)
                    }
                }
            }
        })
    }

    private fun parseJson(responseData: String) {
        try {
            val jsonArray = JSONArray(responseData)
            val meanings = StringBuilder()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val meaningsArray = jsonObject.getJSONArray("meanings")
                for (j in 0 until meaningsArray.length()) {
                    val meaningObject = meaningsArray.getJSONObject(j)
                    val definitionsArray = meaningObject.getJSONArray("definitions")
                    for (k in 0 until definitionsArray.length()) {
                        val definition = definitionsArray.getJSONObject(k)
                        meanings.append(definition.getString("definition")).append("\n")
                    }
                }
            }

            requireActivity().runOnUiThread {
                textViewMeaning.text = meanings.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveWord(word: String, meaning: String) {
        val sharedPreferences = requireActivity().getSharedPreferences("dictionary", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(word, meaning)
        editor.apply()
        Toast.makeText(requireContext(), "Слово добавлено в словарь", Toast.LENGTH_SHORT).show()
    }

}