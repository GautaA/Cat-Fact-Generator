package com.example.catfactgenerator.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fact_table")
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val text: String
)