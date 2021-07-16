package com.example.hairdresser.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hairdresser.Object.MyMenu
import com.example.hairdresser.Adapter.MyMenuAdapter
import com.example.hairdresser.R
import java.util.ArrayList

class Main_fragment : Fragment(){

    private lateinit var menuAdapter: MyMenuAdapter
    private var menuList = ArrayList<MyMenu>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_main, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.main_RCV_menu)

        menuList = getData();

        menuAdapter = MyMenuAdapter(menuList ,this)
        val layoutManager = LinearLayoutManager(this.context)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = menuAdapter

        return view
    }

    private fun getData(): ArrayList<MyMenu> {
        if(menuList.size == 0){
            menuList.add(MyMenu("Women Cut " , "100" , "30 min" , R.drawable.cut))
            menuList.add(MyMenu("Women Color" , "250" , "30 min" , R.drawable.color))
            menuList.add(MyMenu("Women Brushing" , "50" , "30 min" , R.drawable.brushing))
            menuList.add(MyMenu("Women Cut, Color, Brushing" , "300" , "90 min" , R.drawable.cut_color))
            menuList.add(MyMenu("Men Cut" , "50" , "30 min" , R.drawable.men))
            menuList.add(MyMenu("Children Cut" , "30" , "30 min" , R.drawable.children))
        }
        return menuList
    }
}


