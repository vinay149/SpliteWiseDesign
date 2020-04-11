package com.company.model

class EqualExpenses( amount:Double ,  id:String,  paidBy:RoomMate,  split:List<Split>):Expenses(amount, id,paidBy,split){

    override fun validate():Boolean {
        for(element in split){
            if(element is EqualSplit){
                return true
            }
        }
        return false
    }
}