package com.example.myapp.board

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Contents
import com.example.myapp.R
import com.example.myapp.databinding.FragmentBoardBinding
import com.example.myapp.placeholder.PlaceholderContent.PlaceholderItem
import java.time.format.DateTimeFormatter

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
        private val context:Context)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {
    private var values = mutableListOf<Contents>()

    fun setListData(data:MutableList<Contents>){
        values = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(FragmentBoardBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.titleView.text = item.title
        holder.locationView.text = item.location
        //holder.imageView.setImageBitmap(item.image)
        if(item.proceeding){
            holder.proceedingView.text="진행중"
        }
        else {
            holder.proceedingView.text = "완료됨"
        }


        holder.itemView.setOnClickListener {
            if(RecyclerView.NO_POSITION!=position){
                val intent = Intent(holder.itemView.context,BoardElement::class.java)
                intent.putExtra("title",item.title)
                intent.putExtra("content",item.content)
                intent.putExtra("id",item.name)
              //  intent.putExtra("image",item.image)
                intent.putExtra("location",item.location)
                intent.putExtra("proceeding",item.proceeding)
                intent.putExtra("category",item.category)
                intent.putExtra("dateTime",item.dateTime)
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            }
        }

        //이미지 뷰는 어케 하는 거임?
    }

    override fun getItemCount(): Int{
        return values.size
    }

    inner class ViewHolder(binding: FragmentBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        val titleView: TextView = binding.title
        val locationView: TextView = binding.location
        val proceedingView: TextView = binding.proceeding
        val imageView: ImageView = binding.imageIcon

    }

}