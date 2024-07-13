package com.example.paymentc.api

import com.example.paymentc.interfaces.PaymentStatusInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PaymentStatusAPI {

    private var retrofit: Retrofit? = null

    fun getPaymentStatus():PaymentStatusInterface?{
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                .baseUrl("https://sandbox.cashfree.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(PaymentStatusInterface::class.java)
    }
}