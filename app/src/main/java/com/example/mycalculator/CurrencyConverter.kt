package com.example.mycalculator

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mycalculator.databinding.ActivityCurrencyConverterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://data.fixer.io/"

class CurrencyConverter : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyConverterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCurrencyConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchConversions()
    }

    var conversions: Map<String, Double> = hashMapOf()

    private fun fetchConversions() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData("e7951f40dc007e273cd188a9da22674c")

        retrofitData.enqueue(object : Callback<CurrencyData> {
            override fun onResponse(call: Call<CurrencyData>, response: Response<CurrencyData>) {
                conversions = response.body()!!.rates
            }

            override fun onFailure(call: Call<CurrencyData>, t: Throwable) {
                Log.e("MainActivity", "onFailure: " + t.message)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun convertHandler(view: View) {
        val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            if (binding.editTextNumber.text.isNotEmpty()) {
                val equivalentFrom =
                    conversions.getValue(binding.FromCurrency.selectedItem.toString())
                val equivalentTo = conversions.getValue(binding.ToCurrency.selectedItem.toString())
                val toEuro = (binding.editTextNumber.text.toString().toDouble()).div(equivalentFrom)
                var finalResult = toEuro.times(equivalentTo.toString().toDouble())
                finalResult = String.format("%.2f", finalResult).toDouble()

                binding.Result.text =
                    binding.editTextNumber.text.toString() + " " + binding.FromCurrency.selectedItem.toString() + "\n=\n" + finalResult.toString() + " " + binding.ToCurrency.selectedItem.toString()
            }
        } else {
            binding.Result.text = "Check your\ninternet connection"
        }
    }
}