package com.example.zeew.Network

import android.service.autofill.UserData
import com.example.zeew.Network.Constants.Companion.BASE_URL
import com.example.zeew.Network.Constants.Companion.CONNECT_TIMEOUT
import com.example.zeew.Network.Constants.Companion.REQUEST_TIMEOUT
import com.example.zeew.Network.Constants.Companion.WRITE_TIMEOUT
import com.example.zeew.model.Forms.LoginForm
import com.example.zeew.model.Forms.RegistrationForm
import com.example.zeew.model.UserData2
import com.google.gson.JsonObject
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import io.reactivex.Observable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
@Module
@InstallIn(ApplicationComponent::class)
class Client() {
    private lateinit var SERVICES: ZeewServices
    init {
        if (okHttpClient == null) {
            reinstantiateClient(null)
        }
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        SERVICES = retrofit.create(ZeewServices::class.java)
    }
    companion object{
        private var okHttpClient: OkHttpClient?=null
        private var INSTANCE:Client?=null
        private fun initHttpClient(token: String?):OkHttpClient{
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
           var httpClient:OkHttpClient.Builder = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            httpClient.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val requestBuilder: Request.Builder = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")

                    //                        .addHeader("Accept", "application/json")
                    if (token != null) {
                        requestBuilder.addHeader("Authorization", "JWT $token")
                    }

                    val request: Request = requestBuilder.build()
                    return chain.proceed(request)
                }
            })
           return httpClient.build()
        }
        @Singleton
        @Provides
        fun getINSTANCE(): Client {
            if (INSTANCE == null) {
                INSTANCE = Client()
            }
            return INSTANCE!!
        }

        fun reinstantiateClient(token: String?) {
            okHttpClient =initHttpClient(token)
            INSTANCE = Client()
        }

}
    fun signUp(form: RegistrationForm): Observable<JsonObject> {
        return SERVICES.signUp(form)
    }
    fun signIn(form: LoginForm?): Observable<JsonObject> {
        return SERVICES.signIn(form)
    }
}