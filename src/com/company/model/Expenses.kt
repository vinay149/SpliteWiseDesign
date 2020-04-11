package com.company.model

abstract class Expenses(val amount:Double , val id:String, val paidBy:RoomMate, val split:List<Split>){
    abstract fun validate():Boolean
}