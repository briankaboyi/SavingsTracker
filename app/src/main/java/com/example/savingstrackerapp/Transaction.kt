package com.example.savingstrackerapp

data class Transaction(
    val type: TransactionType,
    val method: String,
    val amount: Double,
    val date: String
)
