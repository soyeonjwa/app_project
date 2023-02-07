package com.example.myapp

data class Message(
    val message: String?,
    val sendId: String?
){
    constructor(): this("","")
}
