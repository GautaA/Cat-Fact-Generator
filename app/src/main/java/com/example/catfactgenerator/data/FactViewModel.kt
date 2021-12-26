package com.example.catfactgenerator.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FactViewModel(application: Application): AndroidViewModel(application) {
    val readFact: LiveData<List<FactEntity>>
    private val repository: FactRepository

    init {
        val factDao = FactDatabase.getDatabase(application).factDao()
        repository = FactRepository(factDao)
        readFact = repository.readFact
    }

    fun addFact(fact: FactEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFact(fact)
        }
    }
}