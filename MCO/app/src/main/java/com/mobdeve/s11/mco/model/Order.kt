package com.mobdeve.s11.mco.model

class Order(
    val orderId: Int,
    val userEmail: String,
    val orderItems: String,
    val orderAddress: String,
    val orderTotal: Double,
    val orderDate: String
) {
}