package com.example.catfactgenerator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFact(fact: FactEntity)

    @Query("SELECT * FROM fact_table ORDER BY RANDOM() LIMIT 1")
    fun readFact(): LiveData<List<FactEntity>>
}