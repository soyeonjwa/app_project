package com.example.myapp

import android.graphics.Bitmap
import android.media.Image

data class User(

    var name: String,
    var email: String,
    var uId: String

)
{
    constructor(): this("", "", "")
}

data class Contents(
    var name: String,//id
    var title: String,
    var content: String,
    var location: String,
    var proceeding: Boolean,
    var category: String,
    var image: Bitmap
)