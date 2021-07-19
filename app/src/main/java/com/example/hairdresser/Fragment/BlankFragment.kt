package com.example.hairdresser.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.example.hairdresser.Activity.MainActivity
import com.example.hairdresser.MyApp
import com.example.hairdresser.Object.MyUser
import com.example.hairdresser.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import io.realm.mongodb.Credentials
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document

class BlankFragment : Fragment() {
private val TAG = "BlankFragment"
    private lateinit var start_EDT_email : TextInputEditText
    private lateinit var start_EDT_password : TextInputEditText
    private lateinit var start_BTN_login : MaterialButton
    private lateinit var start_LBL_pass : TextView
    private lateinit var start_LBL_register : TextView

    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongoCollection: MongoCollection<Document>

    val app = MyApp.Constants.app

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_blank, container , false)
        findViews(view)

        start_BTN_login.setOnClickListener {
            loginFunc()
            NavHostFragment.findNavController(this).navigate(R.id.action_blankFragment_to_main_fragment)
        }
        start_LBL_pass.setOnClickListener{
            loadClient()
            Toast.makeText(this.requireContext(), "Please create Account it's take one minute", Toast.LENGTH_SHORT).show()
        }
        start_LBL_register.setOnClickListener{
            NavHostFragment.findNavController(this).navigate(R.id.action_blankFragment_to_register_fragment2)
        }

        return view
    }



    private fun loginFunc() {
        val cred: Credentials = Credentials.emailPassword(start_EDT_email.text.toString(), start_EDT_password.text.toString())
        app.loginAsync(cred) { result ->
            Log.d(TAG, "onCreate loginAsync: " + result.isSuccess)
            if (result.isSuccess){
                loadClient()
            }else{
                Log.d(TAG, "onCreate loginAsync: " + result.error)
                requireActivity().runOnUiThread {
                    Toast.makeText(this.requireContext(), ""+ result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadClient() {
        mongoCollection.findOne(Document("Email" , start_EDT_email.text.toString())).getAsync { user ->
            if(user != null && user.isSuccess){
                val u = MyUser(user.get()["Name"].toString() , user.get()["Phone"].toString() ,user.get()["Email"].toString() , user.get()["Password"].toString())
                MyApp.Variable.mUser = u
                Log.d(TAG, "loadClient: " + MyApp.Variable.mUser.toString())
            }
        }
    }

    private fun findViews(view: View) {

        mongoClient = app.currentUser()?.getMongoClient("mongodb-atlas")!!
        mongoDatabase = mongoClient.getDatabase("HairDresser")!!
        mongoCollection = mongoDatabase.getCollection("client")!!

        start_EDT_email  = view.findViewById(R.id.start_EDT_email)
        start_EDT_password = view.findViewById(R.id.start_EDT_password)
        start_BTN_login  = view.findViewById(R.id.start_BTN_login)
        start_LBL_pass  = view.findViewById(R.id.start_LBL_pass)
        start_LBL_register = view.findViewById(R.id.start_LBL_register)
    }


}