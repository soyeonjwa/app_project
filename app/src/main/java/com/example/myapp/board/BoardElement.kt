package com.example.myapp.board

import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Text
import java.time.LocalDateTime

class BoardElement : AppCompatActivity() {
    //날짜 추가해야돼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_element)

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

        val imageName = intent.getStringExtra("boardid")
        var bitmap: Bitmap? = null

        //Firebase Storage의 참조 객체를 가져옵니다.
        val storageReference = FirebaseStorage.getInstance().reference

        //Firebase Storage의 이미지 경로를 지정합니다.
        val imageRef = storageReference.child("images/$imageName")

        //이미지를 다운로드합니다.
        imageRef.getBytes(Long.MAX_VALUE)
            .addOnSuccessListener { bytes ->
                //이미지 다운로드 성공 시 처리할 코드를 작성합니다.
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                imageView.setImageBitmap(bitmap)
            }
            .addOnFailureListener {
                //이미지 다운로
            }


        titleView.text = intent.getStringExtra("title")
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