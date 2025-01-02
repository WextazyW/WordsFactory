package com.example.wordsfactory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsfactory.Adapter.MeaningAdapter
import com.example.wordsfactory.model.Definition
import com.example.wordsfactory.api.DictionaryApiService
import com.example.wordsfactory.R
import com.example.wordsfactory.ViewModel.DictionaryViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryFragment : Fragment() {

    private lateinit var apiService: DictionaryApiService
    private lateinit var viewModel: DictionaryViewModel
    private lateinit var meaningsRecyclerView: RecyclerView
    private lateinit var meaningAdapter: MeaningAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary, container, false)

        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)
        val wordTextView = view.findViewById<TextView>(R.id.wordTextView)
        val phoneticTextView = view.findViewById<TextView>(R.id.phoneticTextView)
        val partOfSpeechTextView = view.findViewById<TextView>(R.id.partOfSpeechTextView)
        val addToDictionaryButton = view.findViewById<Button>(R.id.addToDictionaryButton)
        val searchButton = view.findViewById<Button>(R.id.searchButton)
        meaningsRecyclerView = view.findViewById(R.id.meaningsRecyclerView)

        meaningsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(DictionaryApiService::class.java)
        viewModel = ViewModelProvider(this)[DictionaryViewModel::class.java]

        searchButton.setOnClickListener {
            val word = searchEditText.text.toString()
            if (word.isNotEmpty()) {
                viewModel.fetchWordMeaning(apiService, word)
            } else {
                Toast.makeText(requireContext(), "Please enter a word", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.wordData.observe(viewLifecycleOwner) { wordResponse ->
            wordTextView.text = wordResponse.word
            phoneticTextView.text = wordResponse.phonetic ?: ""
            partOfSpeechTextView.text = "Part of Speech: ${wordResponse.meanings[0].partOfSpeech}"

            val definitions : List<Definition> = wordResponse.meanings.flatMap { it.definitions }
            meaningAdapter = MeaningAdapter(definitions)
            meaningsRecyclerView.adapter = meaningAdapter
        }


        addToDictionaryButton.setOnClickListener {
            Toast.makeText(requireContext(), "Word added to dictionary", Toast.LENGTH_SHORT).show()
        }

        return view
    }

}