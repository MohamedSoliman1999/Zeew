package com.example.zeew.vvm.vm

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeew.Network.Client
import com.example.zeew.Repository.AuthRepository
import com.example.zeew.ZeewApp
import com.example.zeew.model.Forms.RegistrationForm
import com.google.gson.JsonObject
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegistrationVM @ViewModelInject constructor(
        var repository: AuthRepository,
        app: Application
) :
        AndroidViewModel(app) {
    var retrievalData: MutableLiveData<JsonObject> = MutableLiveData<JsonObject>()
    var compositeDisposable: CompositeDisposable?=null
    fun userSignUp(signUpForm:RegistrationForm){
        val observable: Observable<JsonObject> = repository.getApiClient().signUp(signUpForm)
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