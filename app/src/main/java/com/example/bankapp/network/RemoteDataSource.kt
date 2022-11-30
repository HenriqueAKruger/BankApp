package com.example.bankapp.network

import com.example.bankapp.model.LoginRequest

class RemoteDataSource {

    fun login(loginRequest: LoginRequest, onUserLoggedIn: (String?, Throwable?) -> Unit){
        Thread{
            Thread.sleep(1000)
            onUserLoggedIn("a", null)
        }.start()
    }
}