package com.example.hairdresser.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.hairdresser.MyApp
import com.example.hairdresser.MyApp.Constants.app
import com.example.hairdresser.Object.MyUser
import com.example.hairdresser.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document

class Register_fragment : Fragment() {

    private val TAG = "nathan"
    private lateinit var register_EDT_name : TextInputEditText
    private lateinit var register_EDT_password : TextInputEditText
    private lateinit var register_EDT_email : TextInputEditText
    private lateinit var register_EDT_phone : TextInputEditText
    private lateinit var register_BTN_register : MaterialButton

    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongoCollection: MongoCollection<Document>
    val app = MyApp.Constants.app

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        findViews(view)


        register_BTN_register.setOnClickListener {
            createAccount()
        }

        return view
    }

    private fun createAccount() {
        app.emailPassword.registerUserAsync(register_EDT_email.text.toString(), register_EDT_password.text.toString()) {
            if (!it.isSuccess) {
                Log.e(TAG, "Error: ${it.error}")
                //TODO: Handle user not saved
            } else {
                Log.i(TAG, "Successfully registered user.")
                Toast.makeText(this.requireContext(), "User created successfully!", Toast.LENGTH_SHORT).show()
                addToDB()
                moveToPage()
            }

        }
    }

    private fun moveToPage() {
        NavHostFragment.findNavController(this).navigate(R.id.action_register_fragment_to_blankFragment2)
    }

    private fun addToDB() {
        mongoCollection.insertOne(
            Document("post_id", "1" )
                .append("Name", register_EDT_name.text.toString())
                .append("Phone", register_EDT_phone.text.toString())
                .append("Email" , register_EDT_email.text.toString())
                .append("Password" , register_EDT_password.text.toString())
        )
            .getAsync({ r ->
                Log.d("nathan", "onCreate register: " + r.error)
                if (r.isSuccess){
                    Toast.makeText(this.requireContext(), "Succefully", Toast.LENGTH_SHORT).show()
                }
                Log.d("nathan", "onCreate register: " + r.isSuccess)
            }
            )

    }

    private fun findViews(view: View) {

        mongoClient = app.currentUser()?.getMongoClient("mongodb-atlas")!!
        mongoDatabase = mongoClient.getDatabase("HairDresser")!!
        mongoCollection = mongoDatabase.getCollection("client")!!

        register_EDT_name  = view.findViewById(R.id.register_EDT_name)
        register_EDT_password = view.findViewById(R.id.register_EDT_password)
        register_EDT_email  = view.findViewById(R.id.register_EDT_email)
        register_EDT_phone  = view.findViewById(R.id.register_EDT_phone)
        register_BTN_register = view.findViewById(R.id.register_BTN_register)
    }

}