package com.example.zeew.Repository

import com.example.zeew.Network.ZeewFireBaseAuth
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AuthRepository @Inject constructor(private var zeewFirease:ZeewFireBaseAuth)
{
    fun getFireBaseInstance(): ZeewFireBaseAuth {
        return zeewFirease
    }
}