package com.example.mft12.pocket

class Member {
    var ID:Int?=null
    var Name:String?=null
    var Money:Double = 0.0

    constructor(ID:Int,Name:String, Money:Double) {
        this.ID = ID
        this.Name = Name
        this.Money = Money
    }
}