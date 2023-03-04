package com.example.mycostsapp

data class MonthlyExpenses(
    val monthName: String,
    val groceries: Double,
    val car: Double,
    val other: Double,
    val bills: Double,
    val totalExpenses: Double,
    val totalSavings: Double
)
