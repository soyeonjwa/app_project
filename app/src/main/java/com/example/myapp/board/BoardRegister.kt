package com.example.myapp.board

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R
import com.example.myapp.databinding.BoardRegisterBinding
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class BoardRegister : AppCompatActivity() {
    lateinit var imageIv:ImageView
    lateinit var title:String
    lateinit var content:String
    private var location: LatLng ?=null
    lateinit var location_text: TextView

    lateinit var uploadBtn:Button
    lateinit var locationBtn: Button
    //위치는 지도 검색 기능 추가한 다음에 추가
    //사진은 사진 업로드 기능 추가한 다음에 추가

    //  lateinit var storage: FirebaseStorage
    // lateinit var firestore: FirebaseFirestore

    val requestLauncher: ActivityResultLauncher<Intent> =registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val lat = it.data?.getDoubleExtra("Lat", 1.0)
        val lng = it.data?.getDoubleExtra("Lng", 1.0)

        location = LatLng(lat!!.toDouble(), lng!!.toDouble())

        val str:String = it.data?.getStringExtra("address").toString()
        location_text.text = str
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapp.R.layout.board_register)

        var binding = BoardRegisterBinding.inflate(layoutInflater) as BoardRegisterBinding

        title = binding.titleEt.toString()
        content = binding.contentEt.toString()
        var date = LocalDateTime.now()
        location_text= findViewById(R.id.location)

        locationBtn = findViewById(R.id.findLocationBtn)
        uploadBtn = binding.regButton

        //위치 검색 관련
        locationBtn.setOnClickListener{
            Log.d("엥","왜 안돼?")
            val intent: Intent = Intent(this@BoardRegister,FindLocationInBoard::class.java)
            requestLauncher.launch(intent)
        }

        // 체크박스 하나만 선택+정보 저장(정보 저장은 나중에)
        val checkBoxFind = findViewById<CheckBox>(R.id.find)
        val checkBoxLost = findViewById<CheckBox>(R.id.lost)

        checkBoxFind.setOnClickListener{
            checkBoxFind.isChecked = true
            checkBoxLost.isChecked = false
        }

        checkBoxLost.setOnClickListener{
            checkBoxFind.isChecked = false
            checkBoxLost.isChecked = true
        }




        /* 이때 파이어베이스에 저장하면 됨
        uploadBtn.setOnClickListener{
            val data = mapOf(
                "title" to title,
                "content" to content,
                "date" to date
            )

            firestore.collection("board").add(data)
                .addOnSuccessListener {

                }
                .addOnFailureListener {
                    Log.w("storage","data save error", it)
                }
        }*/
    }
}