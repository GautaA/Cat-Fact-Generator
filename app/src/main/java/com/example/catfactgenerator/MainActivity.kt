package com.example.catfactgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.catfactgenerator.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnGenerate.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val response = try {
                    RetrofitInstance.api.getFact()
                } catch (e: IOException) {
                    Log.e(TAG, "No internet connection: ${e.localizedMessage}")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(TAG, "HttpException, ${e.code()}")
                    return@launchWhenCreated
                }

                if (response.isSuccessful && response.body() != null) {
                    val Fact = response.body()
                    if (Fact != null) {
                        binding.tvFact.text = Fact.text
                    }
                }
            }
        }
    }
}