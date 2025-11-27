package com.example.login_movil

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Obtener datos del intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Usuario"
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        val token = intent.getStringExtra("TOKEN") ?: ""

        // Configurar vistas
        findViewById<TextView>(R.id.tvUserName).text = userName
        findViewById<TextView>(R.id.tvUserEmail).text = userEmail

        // Mostrar fecha y hora actual
        val currentDateTime = SimpleDateFormat("dd 'de' MMMM, yyyy - hh:mm a", Locale("es", "ES"))
            .format(Date())
        findViewById<TextView>(R.id.tvLoginTime).text = currentDateTime

        // Botón de cerrar sesión
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Limpiar datos de sesión si los guardaste en SharedPreferences
        // val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        // sharedPref.edit().clear().apply()

        // Volver a la pantalla de bienvenida
        val intent = Intent(this, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // Prevenir que el usuario vuelva atrás con el botón back
    override fun onBackPressed() {
        // No hacer nada o mostrar un diálogo de confirmación
    }
}
