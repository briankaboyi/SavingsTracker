package com.example.savingstrackerapp

data class Transaction(
    val type: TransactionType,
    val method: String,         // e.g. "MPESA 0712345678"
    val amount: Double,
    val date: String            // e.g. "24 Sep 2025"
)
