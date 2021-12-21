package com.example.catfactgenerator

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.catfactgenerator.api.RetrofitInstance
import com.example.catfactgenerator.data.FactEntity
import com.example.catfactgenerator.data.FactViewModel
import com.example.catfactgenerator.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException

const val TAG = "MainActivity"

private lateinit var binding: ActivityMainBinding
private lateinit var factViewModel: FactViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        factViewModel = ViewModelProvider(this).get(FactViewModel::class.java)

        binding.btnGenerate.setOnClickListener {
            val online = isNetworkAvaliable(this)
            if (online) {
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
                        val fact = response.body()
                        if (fact != null) {
                            insertDataToDatabase(binding.tvFact.text.toString())
                            binding.tvFact.text = fact.text
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Offline mode", Toast.LENGTH_LONG ).show()
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun isNetworkAvaliable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
            return false
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

    }

    private fun insertDataToDatabase(text: String) {
        val fact = FactEntity(0, text)
        factViewModel.addFact(fact)
    }
}