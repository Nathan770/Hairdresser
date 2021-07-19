package com.example.hairdresser.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.hairdresser.MyApp
import com.example.hairdresser.R
import io.realm.Realm;
import io.realm.internal.objectstore.OsAppCredentials.anonymous
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.AuthenticationListener
import io.realm.mongodb.Credentials.anonymous
import io.realm.mongodb.Credentials.emailPassword
import io.realm.mongodb.User;
import io.realm.mongodb.Credentials
import io.realm.mongodb.mongo.MongoClient
import io.realm.mongodb.mongo.MongoCollection
import io.realm.mongodb.mongo.MongoDatabase
import org.bson.Document


class MainActivity : AppCompatActivity() {

    lateinit var uiThreadRealm: Realm

    private lateinit var mongoClient: MongoClient
    private lateinit var mongoDatabase: MongoDatabase
    private lateinit var mongoCollection: MongoCollection<Document>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

/*

        create account
       app.emailPassword.registerUserAsync("nanathanamiel5@gmail.com", "Nathan770",App.Callback {
            result ->
            Log.d("nathan", "onCreate: " + result.error)
            Log.d("nathan", "onCreate: " + result.isSuccess)

        })



        //login
        val cred: Credentials = Credentials.emailPassword("nanathanamiel5@gmail.com", "Nathan770")

        app.loginAsync(cred) { result ->
            Log.d("nathan", "onCreate fir: " + result.error)
            Log.d("nathan", "onCreate fir: " + result.isSuccess)



            mongoClient = app.currentUser()?.getMongoClient("mongodb-atlas")!!
            mongoDatabase = mongoClient.getDatabase("HairDresser")!!
            mongoCollection = mongoDatabase.getCollection("schedule")!!



            //putData
            mongoCollection.insertOne(
                Document("post_id", 1)
                    .append("Name", "nathan")
                    .append("Phone", "0543957047")
                    .append("Date", "13.10.2010")
                    .append("Hour", "10:30")
                    .append("hairdresssername", "shlomi")
            )
                .getAsync({ r ->
                    Log.d("nathan", "onCreate sec: " + r.error)
                    Log.d("nathan", "onCreate sec: " + r.isSuccess)
                }
                )


          //getData
            Thread{
                val itemsIterator = mongoCollection.find().iterator().get()
                while (itemsIterator.hasNext()) {
                    val temp = itemsIterator.next().toJson().toString()
                    Log.d("nathan", "onCreate: thr" +temp)

                }
            }.start()

        }


 */

    }
}
