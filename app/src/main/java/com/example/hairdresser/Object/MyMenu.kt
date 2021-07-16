package com.example.hairdresser.Object

class MyMenu(prestation : String , price : String , time : String , photo : Int) {

    var prestation : String = ""
        get() = field
        set(value) {
            field = value
        }

    var price : String = ""
        get() = field
        set(value) {
            field = value
        }

    var time : String = ""
        get() = field
        set(value) {
            field = value
        }

    var photo : Int = 0
        get() = field
        set(value) {
            field = value
        }

    init {
        this.prestation = prestation
        this.price = price
        this.time = time
        this.photo = photo
    }
}
