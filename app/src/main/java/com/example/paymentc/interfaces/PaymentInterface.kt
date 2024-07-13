package com.example.paymentc.interfaces

import com.example.paymentc.models.Data
import com.example.paymentc.models.PaymentModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface PaymentInterface {
    @POST("/pg/orders")
    @Headers(
        "Content-Type: application/json",
        "x-client-id: *******YOUR_APP_ID(Client-id)********",
        "x-client-secret: ******YOUR_SECRET_KEY*******",
        "x-api-version: 2022-09-01",
        "Accept: application/json"
    )
    fun getOrderID(
        @Body data: Data
    ):Call<PaymentModel>
}