package com.example.myapp.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.Contents
import com.example.myapp.R
import com.example.myapp.User
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import org.w3c.dom.Text
import java.time.LocalDateTime

class BoardElement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_element)

        //날짜 추가해야돼
        var idView = findViewById<TextView>(R.id.boardID)
        var titleView = findViewById<TextView>(R.id.boardTitle)
        var imageView = findViewById<ImageView>(R.id.boardImage)
        var contentView = findViewById<TextView>(R.id.boardContent)
        var categoryView = findViewById<TextView>(R.id.boardCategory)
        var proceedingView = findViewById<TextView>(R.id.boardPreceeding)
        var dateView = findViewById<TextView>(R.id.boardDate)
        var locationView = findViewById<TextView>(R.id.boardLocation)

        val id = intent.getStringExtra("id")

        val mDbRef = FirebaseDatabase.getInstance().reference

        mDbRef.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(user in snapshot.children){
                    val tmp =user.key.toString()
                    if(tmp==id.toString()){
                        var nowUser = user.getValue(User::class.java)
                        idView.text = nowUser?.name
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })



        titleView.text = intent.getStringExtra("title")
        //imageView.setImageBitmap(intent.getParcelableExtra("image"))
        dateView.text = intent.getStringExtra("dateTime")
        contentView.text = intent.getStringExtra("content")
        categoryView.text = intent.getStringExtra("category")
        locationView.text = intent.getStringExtra("location")
        if(intent.getBooleanExtra("proceeding",false)){
            proceedingView.text = "진행중"
        }
        else{
            proceedingView.text = "완료됨"
        }

    }
}