package com.example.myapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.databinding.FragmentChattingBinding
import com.example.myapp.databinding.FragmentMapBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ChattingFragment : Fragment() {




    private var _binding: FragmentChattingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var adapter : userAdapter

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    private lateinit var userList: ArrayList<User>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentChattingBinding.inflate(inflater, container, false)
        val view = binding.root

        //인증 초기화
        mAuth = Firebase.auth

        //리스트 초기화
        mDbRef = Firebase.database.reference

        userList = ArrayList()

        adapter = userAdapter(userList, this@ChattingFragment.requireContext())

        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.userRecyclerView.adapter = adapter


        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(mAuth.currentUser?.uid != currentUser?.uId){
                        userList.add(currentUser!!)
                    }

                    //Toast.makeText(this@ChattingFragment.requireActivity(), currentUser.toString(), Toast.LENGTH_SHORT).show()


                }
                adapter.notifyDataSetChanged()
                //Toast.makeText(this@ChattingFragment.requireActivity(), "success", Toast.LENGTH_SHORT).show()
                //Toast.makeText(this@ChattingFragment.requireActivity(), userList.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                //실패시 실행
                //Toast.makeText(this@ChattingFragment.requireActivity(), "fail", Toast.LENGTH_SHORT).show()
            }
        })



        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}