package com.example.hairdresser.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hairdresser.Object.MyMenu
import com.example.hairdresser.R

class MyMenuAdapter(private val all: ArrayList<MyMenu>, val fragment: Fragment) : RecyclerView.Adapter<MyMenuAdapter.ViewHolder>() {

    private val allMenu: ArrayList<MyMenu> = this.all

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_card, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val temp = allMenu[position]
        holder.pretation.text = temp.prestation
        holder.price.text = temp.price
        holder.time.text = temp.time
        fragment.view?.let { Glide.with(it).load(temp.photo).into(holder.photo) }

        holder.card.setOnClickListener {
            NavHostFragment.findNavController(fragment).navigate(R.id.action_main_fragment_to_schedule_fragment)
            val sp = fragment.context?.getSharedPreferences("MyService", Context.MODE_PRIVATE)
            val editor = sp?.edit()
            editor?.putString("prestation" , temp.prestation)
            editor?.putString("price" , temp.price)
            editor?.putString("time" , temp.time)
            editor?.putInt("photo" , temp.photo)
            editor?.apply()

        }

    }

    override fun getItemCount() = allMenu.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var card : CardView = itemView.findViewById(R.id.card_card)
        var pretation : TextView = itemView.findViewById(R.id.card_pretation)
        var time : TextView = itemView.findViewById(R.id.card_time)
        var price : TextView = itemView.findViewById(R.id.card_price)
        var photo : ImageView = itemView.findViewById(R.id.card_photo)
    }


}
