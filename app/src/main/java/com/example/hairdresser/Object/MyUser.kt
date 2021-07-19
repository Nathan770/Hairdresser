package com.example.hairdresser.Object

class MyUser(var name : kotlin.String = "",
             var phone : kotlin.String = "",
             var email: kotlin.String = "",
             var password: kotlin.String = "",) {

    override fun toString(): kotlin.String {
        return "User = name : '$name' , phone : '$phone' , email : '$email' , password : '$password'"
    }
}
