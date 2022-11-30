package com.example.bankapp.view

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.bankapp.R
import com.example.bankapp.model.LoginRequest
import com.example.bankapp.network.RemoteDataSource
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress

class LoginActivity : AppCompatActivity() {

    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSource()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnSend : Button = findViewById(R.id.login_btn_send)

        btnSend.setOnClickListener {
            val email : EditText = findViewById(R.id.login_edit_email)
            val password : EditText = findViewById(R.id.login_edit_password)

            if (!validate(email, password)) {
                Toast.makeText(this, R.string.field_null_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSend.showProgress{
                progressColor = Color.WHITE
            }

            remoteDataSource.login(LoginRequest(email.toString(), password.toString())) { token, throwable ->
                runOnUiThread {
                    if (!token.isNullOrBlank()) {
                        MainActivity.launch(this)
                    } else {
                        btnSend.hideProgress(R.string.login)  
                        Toast.makeText(this, R.string.invalid_login, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validate(email: EditText, password: EditText): Boolean {
        return (email.text.toString().isNotEmpty()) && (password.text.toString().isNotEmpty())
    }
}