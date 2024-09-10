package com.example.appsumativa

import android.content.Context
import android.os.Bundle
import android.util.Log
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

        // Vincular los elementos UI
        etEmailLogin = findViewById(R.id.etEmailLogin)
        etPasswordLogin = findViewById(R.id.etPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        tvForgotPassword = findViewById(R.id.tvForgotPassword)

        // Configurar el listener para el botón de login
        btnLogin.setOnClickListener {
            val emailIngresado = etEmailLogin.text.toString().trim()
            val passwordIngresada = etPasswordLogin.text.toString().trim()

            if (emailIngresado.isEmpty() || passwordIngresada.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese el correo y la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                validarCredenciales(emailIngresado, passwordIngresada)
            }
        }

        // Manejo de "Olvidaste tu contraseña"
        tvForgotPassword.setOnClickListener {
            Toast.makeText(this, "Función de recuperación de contraseña no implementada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarCredenciales(email: String, password: String) {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)

        // Recuperamos los valores almacenados
        val emailGuardado = sharedPreferences.getString("email", null)
        val passwordGuardada = sharedPreferences.getString("password", null)

        // Log para depurar las credenciales almacenadas y las ingresadas
        Log.d("Login", "Intentando iniciar sesión con: Email=$email, Contraseña=$password")
        Log.d("Login", "Credenciales guardadas: Email=$emailGuardado, Contraseña=$passwordGuardada")

        // Validar credenciales
        if (emailGuardado == null || passwordGuardada == null) {
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show()
            return
        }

        if (email == emailGuardado && password == passwordGuardada) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
            // Aquí puedes redirigir al usuario a otra actividad si es necesario
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}
