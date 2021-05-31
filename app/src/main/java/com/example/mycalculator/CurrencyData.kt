package com.example.mycalculator

data class CurrencyData(
    val base: String,
    val date: String,
    var rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Int
)