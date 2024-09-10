package com.example.appsumativa

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BienvenidoActivity : AppCompatActivity() {

    private lateinit var tvSaludo: TextView
    private lateinit var btnMostrarUsuarios: Button
    private lateinit var llUsuarios: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenido)

        // Vincular los elementos de la UI
        tvSaludo = findViewById(R.id.tvSaludo)
        btnMostrarUsuarios = findViewById(R.id.btnMostrarUsuarios)
        llUsuarios = findViewById(R.id.llUsuarios)

        // Obtener el nombre del usuario que se pasó desde IngresarActivity
        val nombreUsuario = intent.getStringExtra("nombreUsuario")

        // Mostrar saludo personalizado
        tvSaludo.text = "¡Hola!, Bienvenido $nombreUsuario"

        // Configurar el botón para mostrar los usuarios registrados
        btnMostrarUsuarios.setOnClickListener {
            mostrarUsuariosRegistrados()
        }
    }

    // Función para listar todos los usuarios registrados
    private fun mostrarUsuariosRegistrados() {
        val sharedPreferences = getSharedPreferences("users", Context.MODE_PRIVATE)

        // Recuperar los valores guardados en SharedPreferences
        val nombre = sharedPreferences.getString("nombre", "N/A")
        val email = sharedPreferences.getString("email", "N/A")

        // Limpiar el LinearLayout antes de agregar los usuarios
        llUsuarios.removeAllViews()

        // Crear un TextView para cada usuario y añadirlo al LinearLayout
        val tvUsuario = TextView(this)
        tvUsuario.text = "Nombre: $nombre\nEmail: $email"
        tvUsuario.textSize = 16f
        llUsuarios.addView(tvUsuario)

        // Hacer visible el LinearLayout si estaba oculto
        llUsuarios.visibility = View.VISIBLE
    }
}
