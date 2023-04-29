package com.example.myapp.board

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.Contents
import com.example.myapp.R
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID


class BoardRegister : AppCompatActivity() {
    private var image: Bitmap? = null
    lateinit var title: EditText
    var finding: Boolean?= true
    lateinit var content: EditText
    lateinit var category: String
    private var location: LatLng? = null
    lateinit var location_text: TextView
    lateinit var spinner: Spinner
    private lateinit var dbRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var radioGroup: RadioGroup


    lateinit var uploadBtn: Button
    lateinit var locationBtn: Button
    lateinit var imageButton: Button

    private var imageUri: Uri? = null


    val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val lat = it.data?.getDoubleExtra("Lat", 1.0)
        val lng = it.data?.getDoubleExtra("Lng", 1.0)

        location = LatLng(lat!!.toDouble(), lng!!.toDouble())

        val str: String = it.data?.getStringExtra("address").toString()
        location_text.text = str
    }

    val requestLauncherCamera: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        result->
        if(result.resultCode== RESULT_OK){
            val extras = result.data?.extras

            // Bitmap으로 컨버전
            val imageBitmap = extras?.getParcelable("data") as Bitmap?
            image = imageBitmap

            // 이미지뷰에 Bitmap으로 이미지를 입력
            val imageview = findViewById<ImageView>(R.id.board_image)
            imageview.setImageBitmap(imageBitmap)

            imageUri = getImageUriFromBitmap(imageBitmap!!)
        }
    }

    val requestLauncherGallery: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imUri = result.data?.data
            val imageBitmap = MediaStore.Images.Media.getBitmap(contentResolver,imUri)
            image = imageBitmap

            val imageview = findViewById<ImageView>(R.id.board_image)
            imageview.setImageBitmap(imageBitmap)

            imageUri = imUri
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapp.R.layout.board_register)

        title = findViewById<EditText>(R.id.title_et)
        content = findViewById<EditText>(R.id.content_et)
        location_text = findViewById<EditText>(R.id.location)
        spinner = findViewById(R.id.spinner)
        radioGroup = findViewById(R.id.radioGroup)


        locationBtn = findViewById(R.id.findLocationBtn)
        uploadBtn = findViewById(R.id.reg_button)

        //위치 검색 관련
        locationBtn.setOnClickListener {
            Log.d("엥", "왜 안돼?")
            val intent: Intent = Intent(this@BoardRegister, FindLocationInBoard::class.java)
            requestLauncher.launch(intent)
        }

        //이미지 가져오기
        imageButton = findViewById(R.id.upload_image)
        val imageBar = findViewById<View>(R.id.imageBar)
        imageButton.setOnClickListener {
            imageButton.visibility = View.GONE
            imageBar.visibility = View.VISIBLE
        }

        //이미지 가져오기-카메라로 가져오기
        val cameraBtn = findViewById<Button>(R.id.camera)
        val galleryBtn = findViewById<Button>(R.id.gallery)
        cameraBtn.setOnClickListener {
            /*   val intent = Intent(this,Camera::class.java)
            startActivity(intent)*/
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            requestLauncherCamera.launch(intent)
        }

        //이미지 가져오기-갤러리로 가져오기
        galleryBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
            requestLauncherGallery.launch(intent)
        }

        //카테고리 스피너
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.category_array,
            android.R.layout.simple_spinner_dropdown_item
        )
        spinner.adapter = adapter
        spinner.setSelection(0)

        //라디오 그룹에서 하나 선택
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.find2 -> finding = true
                R.id.lost2 -> finding = false
            }
        }

        var dateTime:LocalDateTime


        // 이때 파이어베이스에 저장하면 됨
        dbRef = Firebase.database.reference
        mAuth = Firebase.auth

        uploadBtn.setOnClickListener{
            dateTime = LocalDateTime.now()
            category = spinner.selectedItem.toString()

            val database = FirebaseDatabase.getInstance()
            val key = database.getReference("board").push().key
            dbRef.child("board").child(key.toString()).setValue(Contents(
                key.toString(),
                mAuth.currentUser?.uid.toString(),
                title.text.toString(),
                content.text.toString(),
                location_text.text.toString(),
                true,
                category,
                finding!!,
                dateTime.format(DateTimeFormatter.ISO_DATE),
                location!!.latitude,
                location!!.longitude
            ))

            if(imageUri!=null){
                uploadImage(imageUri,key.toString())
            }

            finish()
        }


    }

    private fun uploadImage(filePath: Uri?, fileName:String){
        if(filePath != null){
            val storageReference = FirebaseStorage.getInstance().reference


            val imageRef = storageReference.child("images/$fileName")

            imageRef.putFile(filePath)
                .addOnSuccessListener {
                    Log.d("이미지 업로드 성공","aa")
                }
                .addOnFailureListener{
                    Log.d("이미지 업로드 실패","aa")
                }

        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(this.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

}

