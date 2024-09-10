package com.example.appsumativa

import RegistroViewModel
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class RegistroActivity : AppCompatActivity() {

    private val registroViewModel: RegistroViewModel by viewModels()

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etNombre: EditText
    private lateinit var etApellidoPaterno: EditText
    private lateinit var etApellidoMaterno: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etDireccion: EditText
    private lateinit var btnRegistrarse: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Vincular elementos de la interfaz
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etNombre = findViewById(R.id.etNombre)
        etApellidoPaterno = findViewById(R.id.etApellidoPaterno)
        etApellidoMaterno = findViewById(R.id.etApellidoMaterno)
        etTelefono = findViewById(R.id.etTelefono)
        etDireccion = findViewById(R.id.etDireccion)
        btnRegistrarse = findViewById(R.id.btnRegistrarse)
        progressBar = findViewById(R.id.progressBar)

        // Observar el estado del registro desde el ViewModel
        registroViewModel.registroExitoso.observe(this, Observer { exito ->
            if (exito) {
                saveUser(
                    etNombre.text.toString().trim(),
                    etApellidoPaterno.text.toString().trim(),
                    etApellidoMaterno.text.toString().trim(),
                    etTelefono.text.toString().trim(),
                    etDireccion.text.toString().trim(),
                    etEmail.text.toString().trim(),
                    etPassword.text.toString().trim()
                )
                Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                // Ocultar el ProgressBar y habilitar el botón después del registro
                progressBar.visibility = View.GONE
                btnRegistrarse.isEnabled = true
                finish()
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los campos correctamente", Toast.LENGTH_SHORT).show()
                // Ocultar el ProgressBar y habilitar el botón si hay un error
                progressBar.visibility = View.GONE
                btnRegistrarse.isEnabled = true
            }
        })

        // Acción al presionar el botón de registrarse
        btnRegistrarse.setOnClickListener {
            if (validarCampos()) {
                // Mostrar el ProgressBar y deshabilitar el botón mientras se procesa el registro
                progressBar.visibility = View.VISIBLE
                btnRegistrarse.isEnabled = false

                // Simulación de un proceso de registro con un retraso de 2 segundos
                Handler(Looper.getMainLooper()).postDelayed({
                    registroViewModel.registrarUsuario(
                        etNombre.text.toString().trim(),
                        etApellidoPaterno.text.toString().trim(),
                        etApellidoMaterno.text.toString().trim(),
                        etTelefono.text.toString().trim(),
                        etDireccion.text.toString().trim(),
                        etEmail.text.toString().trim(),
                        etPassword.text.toString().trim()
                    )
                }, 2000)
            }
        }
    }

    // Validación de los campos
    private fun validarCampos(): Boolean {
        var valido = true

        // Validar email
        val email = etEmail.text.toString().trim()
        if (email.isEmpty()) {
            etEmail.error = "El email es obligatorio"
            valido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.error = "Formato de email inválido"
            valido = false
        }

        // Validar contraseña
        val password = etPassword.text.toString().trim()
        if (!validarPassword(password)) {
            etPassword.error = "La contraseña debe tener al menos 6 caracteres, una mayúscula, un número y un carácter especial"
            valido = false
        }

        // Validar otros campos (nombre, apellido, teléfono, etc.)...
        val nombre = etNombre.text.toString().trim()
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre es obligatorio"
            valido = false
        }

        // Valida los demás campos (omitidos por brevedad)...

        return valido
    }

    // Validar fortaleza de la contraseña
    private fun validarPassword(password: String): Boolean {
        // Expresión regular ajustada para aceptar más caracteres especiales
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\$%^&*()_+\\-\\[\\]{};':\"\\\\|,.<>\\/?]).{6,}$")
        return passwordRegex.matches(password)
    }

    // Guardar usuario en SharedPreferences
    private fun saveUser(
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        telefono: String,
        direccion: String,
        email: String,
        password: String
    ) {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("nombre", nombre)
        editor.putString("apellidoPaterno", apellidoPaterno)
        editor.putString("apellidoMaterno", apellidoMaterno)
        editor.putString("telefono", telefono)
        editor.putString("direccion", direccion)
        editor.putString("email", email)
        editor.putString("password", password)

        editor.apply()

        // Log para verificar los valores guardados
        Log.d("Registro", "Usuario guardado: Email=$email, Contraseña=$password")
    }
}
