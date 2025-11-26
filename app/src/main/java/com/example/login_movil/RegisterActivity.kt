package com.example.login_movil

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.login_movil.data.api.RetrofitClient
import com.example.login_movil.data.models.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegisterSubmit)

        btnRegister.setOnClickListener {
            registerUser()
        }

        findViewById<TextView>(R.id.tvLoginLink).setOnClickListener {
            finish() // Volver al login
        }
    }

    private fun registerUser() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        btnRegister.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.register(
                    RegisterRequest(email, password)
                )

                if (response.isSuccessful && response.body()?.success == true) {
                    val registerResponse = response.body()

                    Toast.makeText(
                        this@RegisterActivity,
                        "¡Cuenta creada! ${registerResponse?.user?.name}",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Ir al login
                    finish()

                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        response.body()?.message ?: "Error al crear cuenta",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            } finally {
                btnRegister.isEnabled = true
            }
        }
    }
}


