package com.example.mycalculator

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("api/latest")
    fun getData(@Query("access_key")AccessKey: String ): Call<CurrencyData>
}