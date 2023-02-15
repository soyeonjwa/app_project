package com.example.myapp.board

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.Contents

class ListViewModel : ViewModel() {
    private val repo = Repo()
    fun fetchData(): LiveData<MutableList<Contents>> {
        val mutableData = MutableLiveData<MutableList<Contents>>()
        repo.getData().observeForever{
            mutableData.value = it
        }
        return mutableData
    }
}