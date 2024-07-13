package com.example.paymentc.models

import com.google.gson.annotations.SerializedName

data class PaymentModel(
    @SerializedName("cf_order_id"        ) var cfOrderId        : String?          = null,
    @SerializedName("created_at"         ) var createdAt        : String?          = null,
    @SerializedName("customer_details"   ) var customerDetails  : UserData? =       null,
    @SerializedName("entity"             ) var entity           : String?          = null,
    @SerializedName("order_amount"       ) var orderAmount      : Double?          = null,
    @SerializedName("order_currency"     ) var orderCurrency    : String?          = null,
    @SerializedName("order_expiry_time"  ) var orderExpiryTime  : String?          = null,
    @SerializedName("order_id"           ) var orderId          : String?          = null,
    @SerializedName("order_note"         ) var orderNote        : String?          = null,
    @SerializedName("order_status"       ) var orderStatus      : String?          = null,
    @SerializedName("payment_session_id" ) var paymentSessionId : String?          = null

)