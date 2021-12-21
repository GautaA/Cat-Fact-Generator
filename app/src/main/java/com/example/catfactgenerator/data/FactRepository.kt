package com.example.catfactgenerator.data

import androidx.lifecycle.LiveData

class FactRepository(private val factDao: FactDao) {
    val readFact: LiveData<List<FactEntity>> = factDao.readFact()

    suspend fun addFact(fact: FactEntity) {
        factDao.addFact(fact)
    }
}