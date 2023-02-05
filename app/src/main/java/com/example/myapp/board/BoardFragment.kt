package com.example.myapp.board

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.Contents
import com.example.myapp.R

/**
 * A fragment representing a list of Items.
 */
class BoardFragment : Fragment() {
    private val dataSet: ArrayList<Contents> = arrayListOf()
    private val rvAdapter= MyItemRecyclerViewAdapter(dataSet)
    lateinit var spinner: Spinner


    private var columnCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        rvAdapter.addData("좌소연","마르지엘라 지갑 잃어버렸어요.","어제 연남동에서 술마시다가 잃어버림ㅠㅠ","연남동",proceeding = true)
        rvAdapter.notifyDataSetChanged()
        rvAdapter.addData("좌소연","마르지엘라 지갑 잃어버렸어요.","어제 연남동에서 술마시다가 잃어버림ㅠㅠ","연남동",proceeding = false)
        rvAdapter.notifyDataSetChanged()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_board_list, container, false)
        // Set the adapter
        val dd = view.findViewById<RecyclerView>(R.id.list)
        if (dd is RecyclerView) {
            with(dd) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyItemRecyclerViewAdapter(dataSet)

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


        return view


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