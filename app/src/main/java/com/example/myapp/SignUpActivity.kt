package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.myapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증 초기화
        mAuth = Firebase.auth

        //db 초기화
        dbRef = Firebase.database.reference

        binding.signUp.setOnClickListener {

            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val password = binding.name.text.toString().trim()

            signup(name, email, password)
        }




    }

    private fun signup(name:String, email:String, password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val intent: Intent = Intent(this@SignUpActivity, MainActivity::class.java)
                    startActivity(intent)
                    adduserToDb(name, email, mAuth.currentUser?.uid!!)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "회원가입 실패",Toast.LENGTH_LONG).show()
                }
            }

    }

    private fun adduserToDb(name:String, email:String, uId:String){

        dbRef.child("user").child(uId).setValue(User(name, email, uId))
    }
}