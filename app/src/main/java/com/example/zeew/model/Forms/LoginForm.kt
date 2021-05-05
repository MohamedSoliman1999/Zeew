package com.example.zeew.model.Forms

import java.io.Serializable

class LoginForm:Serializable {
    lateinit var username:String
    lateinit var password:String
    var device_id:String="token"
    var device_type:String="ANDROID"

    constructor(username: String, password: String, device_id: String) {
        this.username = username
        this.password = password
        this.device_id = device_id
    }
}