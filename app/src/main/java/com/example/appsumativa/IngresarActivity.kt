package com.example.appsumativa

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class IngresarActivity : AppCompatActivity() {

    private lateinit var etEmailLogin: EditText
    private lateinit var etPasswordLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvForgotPassword: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingresar)

        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        btnLogin.setOnClickListener {
            val email = etEmailLogin.text.toString().trim()
            val password = etPasswordLogin.text.toString().trim()

            val storedPassword = loadUser(email)

            if (storedPassword != null && storedPassword == password) {
                Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        }

        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Se ha enviado un correo de verificación para restablecer tu contraseña.", Toast.LENGTH_LONG).show()

        }
    }

    private fun loadUser(email: String): String? {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
        return sharedPreferences.getString(email, null)
    }
}
