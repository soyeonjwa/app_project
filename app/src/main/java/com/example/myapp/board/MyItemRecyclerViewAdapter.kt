package com.example.myapp.board

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapp.Contents

import com.example.myapp.placeholder.PlaceholderContent.PlaceholderItem
import com.example.myapp.databinding.FragmentBoardBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
        private val values: ArrayList<Contents>)
    : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    fun removeData(position: Int){
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addData(name: String,
                title: String,
                content: String,
                location: String,
                proceeding:Boolean
    ){
        values.add(Contents(name,title,content,location,proceeding))
        notifyItemInserted(values.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.title
        holder.contentView.text = item.content
        holder.locationView.text = item.location
        if(item.proceeding){
            holder.proceedingView.text="진행중"
        }
        else{
            holder.proceedingView.text="완료됨"
        }


        //이미지 뷰는 어케 하는 거임?
    }

    override fun getItemCount(): Int{
        return values.size
    }

    inner class ViewHolder(binding: FragmentBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.title
        val contentView: TextView = binding.content
        val locationView: TextView = binding.location
        val proceedingView: TextView = binding.proceeding
      //  val imageView: ImageView = binding.imageIcon
        /*
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }*/
    }

}