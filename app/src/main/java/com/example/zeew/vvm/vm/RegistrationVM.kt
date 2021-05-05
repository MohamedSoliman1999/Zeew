package com.example.zeew.vvm.vm

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeew.Network.Client
import com.example.zeew.ZeewApp
import com.example.zeew.model.Forms.RegistrationForm
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegistrationVM: ViewModel() {
    var retrievalData: MutableLiveData<JsonObject> = MutableLiveData<JsonObject>()
    var compositeDisposable: CompositeDisposable?=null
    fun userSignUp(firstName: String, lastName: String, email: String, phoneNumber: String, password: String, referralCode: String){
        val signUpForm = RegistrationForm(action = "CustomerSignUp",first_name = firstName,last_name = lastName, username = email,phone_number = "+2$phoneNumber",password =  password,referral_code = referralCode)
        val observable: Observable<JsonObject> = Client.getINSTANCE().signUp(signUpForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(observable.subscribe({
            Log.e("RegistrationVM", "Done")
            retrievalData.setValue(it)
        }, {
            Log.e("RegistrationVM", it.message.toString())
            Toast.makeText(ZeewApp.getINSTANCE().applicationContext, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
        }))

    }

    override fun onCleared() {
        super.onCleared()
        if (compositeDisposable != null) {
            compositeDisposable!!.clear()
        }
    }
}