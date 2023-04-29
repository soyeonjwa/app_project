package com.example.myapp

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.*
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

        var chattingMem = ArrayList<String>()

        val databaseRef = FirebaseDatabase.getInstance().reference.child("user").child(mAuth.currentUser?.uid.toString()).child("chattingUser")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {
                    chattingMem.add(childSnapshot.key.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("aaa", "loadPost:onCancelled", databaseError.toException())

            }
        })

        mDbRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {

                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(currentUser?.uId in chattingMem){
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