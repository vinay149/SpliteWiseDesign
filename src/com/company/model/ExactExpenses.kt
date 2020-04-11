package com.company.model

class ExactExpenses( amount:Double ,  id:String,  paidBy:RoomMate,  split:List<Split>):Expenses(amount, id,paidBy,split) {

    override fun validate(): Boolean {
        for( element in split){
            if(!(element is ExactSplit)){
                return false
            }
        }
        val value:Double = amount
        var total:Double=0.0
        for(element in split){
            total += element.amount
        }
        if(value!=total){
            return false
        }
        return true
    }
}