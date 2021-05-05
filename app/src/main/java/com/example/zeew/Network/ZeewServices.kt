package com.example.zeew.Network

import android.service.autofill.UserData
import com.example.zeew.model.Forms.LoginForm
import com.example.zeew.model.Forms.RegistrationForm
import com.example.zeew.model.UserData2
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ZeewServices {
    //Authentication & Registration
    @POST("CustomerSignUp")
    fun signUp(@Body userData: RegistrationForm): Observable<JsonObject>

    @POST("CustomerLogin")
    fun signIn(@Body userData: LoginForm?): Observable<JsonObject>
}