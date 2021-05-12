package com.example.zeew.model.Forms

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RegistrationForm: Serializable{
         @SerializedName("action")
         var action:String="CustomerSignUp"
         @SerializedName("first_name")
         var first_name:String
         @SerializedName("last_name")
         var last_name:String
         @SerializedName("username")
         var  username:String
         @SerializedName("phone_number")
         var phone_number:String
         @SerializedName("password")
         var password:String
         @SerializedName("referral_code")
         var referral_code:String
         var confirmPassword:String

    constructor(action: String, first_name: String, last_name: String, username: String, phone_number: String, password: String, referral_code: String, confirmPassword: String) {
        this.action = action
        this.first_name = first_name
        this.last_name = last_name
        this.username = username
        this.phone_number = phone_number
        this.password = password
        this.referral_code = referral_code
        this.confirmPassword = confirmPassword
    }
}