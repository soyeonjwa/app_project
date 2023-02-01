package com.example.myapp

import android.app.Activity
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.FragmentChattingBinding
import com.example.myapp.databinding.UserLayoutBinding


class userAdapter(val userList: ArrayList<User>, val context: Context):
RecyclerView.Adapter<userAdapter.userViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {

        return userViewHolder(UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {

        val binding = holder.binding

        val currentUser = userList[position]

        binding.nameText.text = currentUser.name

        binding.itemRoot.setOnClickListener {


            val intent = Intent(context, ChattingActivity::class.java)
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uId)
            context.startActivity(intent)


        }

    }

    inner class userViewHolder(val binding:UserLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val nameText : TextView = binding.nameText


    }
}
