package com.example.tourio

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class HomePageActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val userInputLink = "https://matthews.sites.wfu.edu/misc/graphics/ResVsComp/50percent_444x2.png" // This would come from EditText or another source
        val imageView: ImageView = findViewById(R.id.tour_image2)


        //fun extractFileId(driveLink: String): String? {
           // val regex = """(?:/d/|file/d/)([^/]+)""".toRegex()
            //val matchResult = regex.find(driveLink)
            //return matchResult?.groupValues?.get(1)
        //}

        fun loadImageFromDrive(driveLink: String, imageView: ImageView) {
            //val fileId = extractFileId(driveLink)

            if (true) {
                val imageUrl = userInputLink

                // Use Glide to load the image
                Glide.with(imageView.context)
                    .load(imageUrl)
                    .into(imageView)
            } else {
                // Handle invalid URL
                Toast.makeText(imageView.context, "Invalid Google Drive link", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        loadImageFromDrive(userInputLink, imageView)
    }
}