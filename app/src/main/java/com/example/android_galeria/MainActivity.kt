package com.example.android_galeria

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var fotoUri: Uri
    private val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        imageView = findViewById(R.id.imageView)
        if (uri != null) {
            imageView.setImageURI(uri)
        }
    }

    val takeFullPic = registerForActivityResult(
        ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageView = findViewById(R.id.imageView)
            // netejar imatge i pintar nova
            imageView.setImageDrawable(null)
            imageView.setImageURI(fotoUri)
        } else {
            // notifiquem error
            Toast.makeText(this, "Error al guardar la foto", Toast.LENGTH_SHORT).show()
        }
    }
    private val takeThumb = registerForActivityResult(
        ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        imageView = findViewById(R.id.imageView)
        imageView.setImageBitmap(bitmap)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val selectButton = findViewById<Button>(R.id.button)
        val thumbButton = findViewById<Button>(R.id.thumbButton)

        selectButton.setOnClickListener {
            getContent.launch("image/*")
        }
        thumbButton.setOnClickListener {
            takeThumb.launch(null)
        }

        val photoButton = findViewById<Button>(R.id.photoButton)
        photoButton.setOnClickListener {
            // Assegurem que existeix la carpeta de fotos (igual que al FileProvider)
            val carpeta = File(filesDir.toString(), "fotos")
            carpeta.mkdirs()

            // Preparem l'arxiu pq hi pugui escriure l'App Camera
            val file = File(filesDir.toString(),"fotos/tmpimage.jpg")
            fotoUri = FileProvider.getUriForFile(this,
                "com.example.android_galeria.fileprovider",file)

            // engeguem l'App Camera
            takeFullPic.launch(fotoUri)
        }
    }
}
