package com.example.hairdresser.Fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hairdresser.Adapter.HourAdapter
import com.example.hairdresser.MyApp
import com.example.hairdresser.Object.MyUser
import com.example.hairdresser.R
import com.google.android.material.button.MaterialButton
import io.realm.mongodb.App
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Schedule_fragment : Fragment() {
    private lateinit var scheduel_photo: ImageView
    private lateinit var schedule_pretation: TextView
    private lateinit var schedule_time: TextView
    private lateinit var schedule_price: TextView
    private lateinit var schedule_date: DatePicker
    private lateinit var scheduel_SPN_hairdresser: Spinner
    private lateinit var schedule_BTN_ok: MaterialButton
    private lateinit var scheduel_RCV_time: RecyclerView
    private lateinit var HourAdapter: HourAdapter

    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongoCollection: MongoCollection<Document>

    private var prestation = ""

    var sp = context?.getSharedPreferences("MyService", Context.MODE_PRIVATE)
    val app : App = MyApp.Constants.app

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_schedule, container, false)

        findViews(view)
        getDataFromPresdent()
        var hourList : ArrayList<String> = getHourData()
        addAdapeter(hourList)


        scheduel_SPN_hairdresser.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { getHourData() }
        }

        schedule_BTN_ok.setOnClickListener {
            scheduleValidation()
        }

        return view
    }


    private fun scheduleValidation(){
        val hour = sp?.getString("hour" , "no")
        if (hour.equals("no")){
            Toast.makeText(this.requireContext(), "Please Select hour", Toast.LENGTH_SHORT).show()
        }else{
            postToDb(hour)
        }
    }

    private fun postToDb(hour: String?) {

        mongoCollection.insertOne(
            Document("post_id", "1" )
                .append("Name", MyApp.Variable.mUser.name)
                .append("Phone", MyApp.Variable.mUser.phone)
                .append("Date", schedule_date.dayOfMonth.toString() + "/"+schedule_date.month +"/"+schedule_date.year)
                .append("Hour", hour)
                .append("Prestation" , prestation)
                .append("hairdresssername",scheduel_SPN_hairdresser.selectedItem.toString() )
        )
            .getAsync({ r ->
                if (r.isSuccess){
                    requireActivity().runOnUiThread {
                        Toast.makeText(this.requireContext(), "Succefully", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    requireActivity().runOnUiThread {
                        Toast.makeText(this.requireContext(),"Data base:" + r.error, Toast.LENGTH_SHORT).show()
                    }
                    Log.d("nathan", "onCreate Schedule to data base:" + r.error)

                }
                Log.d("nathan", "onCreate Schedule to data base:" + r.isSuccess)
            }
            )
    }

    private fun addAdapeter(hourList: ArrayList<String>) {
        HourAdapter = HourAdapter(hourList ,this)
        val layoutManager = LinearLayoutManager(this.context)
        scheduel_RCV_time.layoutManager = layoutManager
        scheduel_RCV_time.itemAnimator = DefaultItemAnimator()
        scheduel_RCV_time.adapter = HourAdapter
    }

    private fun getHourData(): ArrayList<String> {
        var hourList  = arrayListOf<String>()

        for (i in 10..18){
            hourList.add(""+i+":00")
            hourList.add(""+i+":30")
        }

        Thread{
            checkAvaliable(hourList)
            requireActivity().runOnUiThread {
                addAdapeter(hourList)
            }
        }.start()
        return hourList
    }

    private fun checkAvaliable(hourList: ArrayList<String>) {
        val itemsIterator = mongoCollection.find().iterator().get()
        while (itemsIterator.hasNext()) {
            val temp = itemsIterator.next().toJson()
            val plainJson = JSONObject(temp)

            val name =  plainJson.get("hairdresssername") as String
            val coiffeurName = scheduel_SPN_hairdresser.selectedItem.toString()

            val date =  plainJson.get("Date") as String
            val scheduleDate : String = schedule_date.dayOfMonth.toString() + "/"+schedule_date.month +"/"+schedule_date.year

            if (date == scheduleDate) {
                if (coiffeurName == name) {
                    val hour = plainJson.get("Hour") as String
                    hourList.remove(hour)
                }
            }
        }
    }


    private fun getDataFromPresdent() {
        prestation = sp?.getString("prestation", "no data").toString()
        val time = sp?.getString("time", "no data")
        val price = sp?.getString("price", "no data")
        val photo = sp?.getInt("photo", 0)
        Log.d("Nathan", "onCreateView: " + prestation + " , " + time + " , " + price)
        Glide.with(this).load(photo).into(scheduel_photo)
        schedule_pretation.text = prestation
        schedule_time.text = time
        schedule_price.text = price
    }

    private fun findViews(view: View) {
        sp = context?.getSharedPreferences("MyService", Context.MODE_PRIVATE)

        mongoClient = app.currentUser()?.getMongoClient("mongodb-atlas")!!
        mongoDatabase = mongoClient.getDatabase("HairDresser")!!
        mongoCollection = mongoDatabase.getCollection("schedule")!!

        scheduel_photo = view.findViewById(R.id.scheduel_photo)
        schedule_pretation = view.findViewById(R.id.schedule_pretation)
        schedule_time = view.findViewById(R.id.schedule_time)
        schedule_price = view.findViewById(R.id.schedule_price)
        schedule_date = view.findViewById(R.id.schedule_date)
        scheduel_SPN_hairdresser = view.findViewById(R.id.scheduel_SPN_hairdresser)
        schedule_BTN_ok = view.findViewById(R.id.schedule_BTN_ok)
        scheduel_RCV_time = view.findViewById(R.id.scheduel_RCV_time)

        schedule_date.minDate = Calendar.getInstance().time.time
    }

}

