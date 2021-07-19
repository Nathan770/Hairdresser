package com.example.hairdresser

import android.app.Application
import android.util.Log
import com.example.hairdresser.Object.MyUser
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials

class MyApp : Application() {

    object Constants {
        const val TAG = "MyApp"
        val app = App(
            AppConfiguration.Builder(BuildConfig.MONGODB_REALM_APP_ID)
                .defaultSyncErrorHandler { _, error ->
                    Log.e(Constants.TAG, "Sync error: ${error.errorMessage}")
                }
                .build())


    }

    object Variable{
        var mUser : MyUser = MyUser("" , "" ,"" ,"")
    }
    override fun onCreate() {
        super.onCreate()
        Realm.init(this) // context, usually an Activity or Application
        val appID: String =
            "hairdresser-app-nlxzy" // replace this with your App ID "\"${gamenet-xopej}\""
        val app: App = App(AppConfiguration.Builder(appID).build())
    }
}