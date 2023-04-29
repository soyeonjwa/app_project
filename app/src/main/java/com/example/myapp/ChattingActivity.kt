package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.databinding.ActivityChattingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChattingActivity : AppCompatActivity() {

    private lateinit var received_name: String
    private lateinit var received_uId: String

    //binding instance
    private lateinit var binding: ActivityChattingBinding

    lateinit var mAuth: FirebaseAuth // 인증 객체
    lateinit var mDbRef: DatabaseReference // Db 객체
    private lateinit var receiverRoom: String
    private lateinit var senderRoom: String

    private lateinit var messageList : ArrayList<com.example.myapp.Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageList = ArrayList()
        val messageAdapter : MessageAdapter = MessageAdapter(this, messageList)

        //RecyclerView
        binding.chatRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerview.adapter = messageAdapter


        //넘겨온 데이터 변수에 담기
        received_name = intent.getStringExtra("name").toString()
        received_uId = intent.getStringExtra("uId").toString()

        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().reference

        //접속자 Uid
        val senderUid = mAuth.currentUser?.uid

        //sender room
        senderRoom = received_uId + senderUid

        //recevierRoom
        receiverRoom = senderUid + received_uId

        supportActionBar?.title = received_name

        binding.sendBtn.setOnClickListener {

            val message = binding.messageEdit.text.toString()
            val messageobject = Message(message, senderUid)

            //restore data
            mDbRef.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {
                    // success restoring
                    mDbRef.child("chat").child(receiverRoom).child("messages").push()
                        .setValue(messageobject)
                }

            binding.messageEdit.setText("")

        }

        mDbRef.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {

                    messageList.clear()

                    for (postSnapshat in snapshot.children){

                        val message = postSnapshat.getValue(com.example.myapp.Message::class.java)
                        messageList.add(message!!)

                        //Toast.makeText(this@ChattingActivity,message.toString(), Toast.LENGTH_SHORT).show()

                    }

                    messageAdapter.notifyDataSetChanged()
                    //Toast.makeText(this@ChattingActivity,"send", Toast.LENGTH_SHORT).show()

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ChattingActivity,"fail", Toast.LENGTH_SHORT).show()
                }


            })


    }
}