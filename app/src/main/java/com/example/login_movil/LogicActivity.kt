package com.example.login_movil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.login_movil.data.api.RetrofitClient
import com.example.login_movil.data.models.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLoginSubmit)

        btnLogin.setOnClickListener {
            loginUser()
        }

        findViewById<TextView>(R.id.tvRegisterLink).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        btnLogin.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.login(
                    LoginRequest(email, password)
                )

                if (response.isSuccessful && response.body()?.success == true) {
                    val loginResponse = response.body()

                    Toast.makeText(
                        this@LoginActivity,
                        "Bienvenido ${loginResponse?.user?.name ?: ""}",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        response.body()?.message ?: "Error al iniciar sesión",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@LoginActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnLogin.isEnabled = true
            }
        }
    }
}
