package com.example.hairdresser.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hairdresser.Object.MyMenu
import com.example.hairdresser.R
import com.google.android.material.button.MaterialButton

class HourAdapter(private val all: ArrayList<String>, val fragment: Fragment): RecyclerView.Adapter<HourAdapter.ViewHolder>() {

    private val allMenu: ArrayList<String> = this.all

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.hour_card, parent, false)
        return HourAdapter.ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HourAdapter.ViewHolder, position: Int) {
        val temp = allMenu[position]
        holder.hour.text = temp
        holder.hour.setOnClickListener {
            holder.hour.setBackgroundColor(Color.GRAY)
            val sp = fragment.context?.getSharedPreferences("MyService", Context.MODE_PRIVATE)
            val editor = sp?.edit()
            editor?.putString("hour" , temp)
            editor?.apply()

        }
    }

    override fun getItemCount(): Int = all.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var hour : MaterialButton = itemView.findViewById(R.id.hour_BTN_adp)
    }


}