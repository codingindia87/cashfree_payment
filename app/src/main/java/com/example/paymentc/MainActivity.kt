package com.example.paymentc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cashfree.pg.api.CFPaymentGatewayService
import com.cashfree.pg.base.exception.CFException
import com.cashfree.pg.core.api.CFSession
import com.cashfree.pg.core.api.CFSession.CFSessionBuilder
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback
import com.cashfree.pg.core.api.utils.CFErrorResponse
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutPayment.CFWebCheckoutPaymentBuilder
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutTheme.CFWebCheckoutThemeBuilder
import com.example.paymentc.api.PaymentAPI
import com.example.paymentc.api.PaymentStatusAPI
import com.example.paymentc.databinding.ActivityMainBinding
import com.example.paymentc.models.Data
import com.example.paymentc.models.PaymentModel
import com.example.paymentc.models.PaymentStatusModel
import com.example.paymentc.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class MainActivity : AppCompatActivity(), CFCheckoutResponseCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this)
        } catch (e: CFException) {
            e.printStackTrace()
        }

        binding.btnPay.setOnClickListener {createPayment()}
    }

    private fun createPayment(){
        PaymentAPI.createOrder()?.getOrderID(
            Data(
                order_amount = 20.0,
                order_currency = "INR",
                customer_details = UserData(
                    customer_id = "sac1245",
                    customer_name = "Sachin",
                    customer_email = "sachin@gmail.com",
                    customer_phone = "+917738878697"
                )
            )
        )?.enqueue(object : Callback<PaymentModel>{
            override fun onResponse(p0: Call<PaymentModel>, p1: Response<PaymentModel>) {
                doPayment(p1.body()!!)
            }

            override fun onFailure(p0: Call<PaymentModel>, p1: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "Error: ${p1.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun doPayment(response: PaymentModel){
        try {
            val cfSession = CFSessionBuilder()
                .setEnvironment(CFSession.Environment.SANDBOX)
                .setPaymentSessionID(response.paymentSessionId!!)
                .setOrderId(response.orderId!!)
                .build()
            val cfTheme = CFWebCheckoutThemeBuilder()
                .setNavigationBarBackgroundColor("#fc2678")
                .setNavigationBarTextColor("#ffffff")
                .build()
            val cfWebCheckoutPayment = CFWebCheckoutPaymentBuilder()
                .setSession(cfSession)
                .setCFWebCheckoutUITheme(cfTheme)
                .build()
            CFPaymentGatewayService.getInstance()
                .doPayment(this@MainActivity, cfWebCheckoutPayment)
        } catch (exception: CFException) {
            exception.printStackTrace()
        }
    }

    override fun onPaymentVerify(p0: String?) {
        getPaymentStatus(p0!!)
    }

    override fun onPaymentFailure(p0: CFErrorResponse?, p1: String?) {
        Toast.makeText(
            this@MainActivity,
            "Error: ${p0?.message}",
            Toast.LENGTH_SHORT
        ).show()
    }

    fun getPaymentStatus(orderKey: String){
        PaymentStatusAPI.getPaymentStatus()?.create(orderKey)
            ?.enqueue(object : Callback<PaymentStatusModel>{
                override fun onResponse(
                    p0: Call<PaymentStatusModel>,
                    p1: Response<PaymentStatusModel>
                ) {
                    if (p1.body()?.order_status == "PAID"){
                        Toast.makeText(
                            this@MainActivity,
                            "Payment complete.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            this@MainActivity,
                            "Payment in pending.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(p0: Call<PaymentStatusModel>, p1: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Error: ${p1.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }


}