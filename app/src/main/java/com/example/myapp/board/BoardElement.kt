package com.example.myapp.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapp.R
import org.w3c.dom.Text

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

        idView.text = intent.getStringExtra("id")
        titleView.text = intent.getStringExtra("title")
        //imageView.setImageBitmap(intent.getParcelableExtra("image"))
        contentView.text = intent.getStringExtra("content")
        categoryView.text = intent.getStringExtra("category")
        if(intent.getBooleanExtra("proceeding",false)){
            proceedingView.text = "진행중"
        }
        else{
            proceedingView.text = "완료됨"
        }

    }
}