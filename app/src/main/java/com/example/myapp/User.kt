package com.example.myapp

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
    var name: String,
    var title: String,
    var content: String,
    var location: String,
    var proceeding: Boolean
   /// var image: Image
)