package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class userAdapter(private val context: ChattingFragment, private val userList: ArrayList<User>):
RecyclerView.Adapter<userAdapter.userViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)

        return userViewHolder(view as ActionMenuItemView)

    }

    override fun getItemCount(): Int {

        return userList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {

        val currentUser = userList[position]
        holder.nameText.text = currentUser.name
    }

    class userViewHolder(itemView: ActionMenuItemView): RecyclerView.ViewHolder(itemView){

        val nameText : TextView = itemView.findViewById((R.id.name_text))
    }
}