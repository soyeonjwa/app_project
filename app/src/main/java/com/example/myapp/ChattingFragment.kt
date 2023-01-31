package com.example.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapp.databinding.FragmentMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChattingFragment : Fragment() {



    lateinit var binding : FragmentMapBinding
    lateinit var adapter : userAdapter

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var userList: ArrayList<User>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chatting, container, false)

        binding = FragmentMapBinding.inflate(inflater, container, false)

        //인증 초기화
        mAuth = Firebase.auth

        //리스트 초기화
        mDbRef = Firebase.database.reference


        adapter = userAdapter(this, userList)








        return binding.root
    }

}