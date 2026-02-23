package com.example.savingstrackerapp.data.transactions

data class Transaction(
    val type: TransactionType,
    val method: String,
    val amount: Double,
    val date: String
)
