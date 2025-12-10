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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private val getContent = registerForActivityResult(GetContent()) { uri: Uri? ->
        imageView = findViewById(R.id.imageView)
        if (uri != null) {
            imageView.setImageURI(uri)
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
    }
}
