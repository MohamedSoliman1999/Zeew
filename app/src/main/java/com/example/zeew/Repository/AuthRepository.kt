package com.example.zeew.Repository

import com.example.zeew.Network.Client
import com.example.zeew.Network.ZeewFireBaseAuth
import com.example.zeew.Utils.DataStorePreferenceManager
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class AuthRepository @Inject constructor(private var zeewFirease:ZeewFireBaseAuth,
                                         private var client: Client,
                                         private var dataStore: DataStorePreferenceManager)
{
    fun getFireBaseInstance(): ZeewFireBaseAuth {
        return zeewFirease
    }
    fun getApiClient():Client{
        return client
    }
    fun getDataStore():DataStorePreferenceManager{
        return dataStore
    }
}