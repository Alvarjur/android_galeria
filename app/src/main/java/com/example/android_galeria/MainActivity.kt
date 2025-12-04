package com.example.android_galeria

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    // Registrar el ActivityResult una sola vez
    private val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        imageView = findViewById(R.id.imageView)
        // Aquí manejas la imagen seleccionada
        if (uri != null) {
            // Ejemplo: imprimir la URI
            println("Imagen seleccionada: $uri")
            imageView.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Ajuste de insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencia al botón
        val selectButton = findViewById<Button>(R.id.button)

        // Listener para abrir el selector de imágenes
        selectButton.setOnClickListener {
            getContent.launch("image/*")
        }
    }
}
