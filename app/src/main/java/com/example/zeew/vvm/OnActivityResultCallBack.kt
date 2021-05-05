package com.example.zeew.vvm

import android.content.Intent

interface OnActivityResultCallBack {

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}