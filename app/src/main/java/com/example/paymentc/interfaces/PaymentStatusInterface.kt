package com.example.paymentc.interfaces

import com.example.paymentc.models.PaymentStatusModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PaymentStatusInterface {

    @GET("/pg/orders/{order_id}")
    @Headers(
        "accept: application/json",
        "x-client-id: *******YOUR_APP_ID(Client-id)********",
        "x-client-secret: ******YOUR_SECRET_KEY*******",
        "x-api-version: 2022-01-01"
    )
    fun create(
        @Path("order_id") orderId: String
    ):Call<PaymentStatusModel>
}