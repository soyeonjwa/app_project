package com.example.myapp.board

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Contents
import com.example.myapp.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database

/**
 * A fragment representing a list of Items.
 */
class BoardFragment : Fragment() {
    lateinit var spinner: Spinner
    private var columnCount = 1

    private lateinit var myadapter:MyItemRecyclerViewAdapter
    private val viewModel by lazy{ViewModelProvider(this).get(ListViewModel::class.java)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_board_list, container, false)
        // Set the adapter
        val dd = view.findViewById<RecyclerView>(R.id.list)
        if (dd is RecyclerView) {
            with(dd) {
                layoutManager = LinearLayoutManager(context)

                myadapter = MyItemRecyclerViewAdapter(context)
                adapter = myadapter
                observeData()
            }
        }


        val fab: View = view.findViewById(R.id.FAB)
        fab.setOnClickListener {
            val intent: Intent = Intent(this.context, BoardRegister::class.java)
            startActivity(intent)
        }

        spinner = view.findViewById(R.id.spinner2)
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this.context!!,R.array.category_array,android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)

        val fabSearch:View = view.findViewById(R.id.FAB_search)
        val searchBar:View = view.findViewById(R.id.search_bar)
        fabSearch.setOnClickListener{
            if(searchBar.isGone){
                searchBar.visibility=View.VISIBLE
            }
            else{
                searchBar.visibility = View.GONE
            }
        }

        val radioGroup = view.findViewById<RadioGroup>(R.id.radioGroup2)
        var finding =true
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.find2 -> finding = true
                R.id.lost2 -> finding = false
            }
        }

        val searchBtn: Button = view.findViewById(R.id.search_button)
        searchBtn.setOnClickListener{
            val text = view.findViewById<EditText>(R.id.search_et).text.toString()
            searchData(text,spinner.selectedItem.toString(),finding)
        }

        return view


    }

    fun observeData(){
        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            myadapter.setListData(it)
            myadapter.notifyDataSetChanged()
        })
    }


    fun searchData(text:String,category:String,finding:Boolean){
        viewModel.fetchData().observe(viewLifecycleOwner, Observer {
            var dataset = mutableListOf<Contents>()
            for(data in it){
                if(data.finding==finding && data.category==category){
                    if(text in data.location|| text in data.title){
                        dataset.add(data)
                    }

                }
            }
            myadapter.setListData(dataset)
            myadapter.notifyDataSetChanged()
        })
    }


    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                BoardFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}