package com.example.myapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context : Context, private val messageList : ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    private val receive = 1;
    private val send = 2;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if(viewType == 1){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive, parent, false)
            ReceiveViewHolder(view)
        }
        else {
            val view : View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            SendViewHolder(view)
        }


    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        // 현재 메세지
        val currentMessage = messageList[position]

        //보내는 데이터
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text = currentMessage.message

        }
        //받는 데이터
        else {
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.sendMessage.text = currentMessage.message
        }


    }

    override fun getItemViewType(position: Int): Int {

        val currentMessage = messageList[position]

        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            send
        }
        else {
            receive
        }
    }

    class SendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val sendMessage : TextView = itemView.findViewById(R.id.send_message_text)

    }

    class ReceiveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val sendMessage : TextView = itemView.findViewById(R.id.receive_message_text)

    }



}