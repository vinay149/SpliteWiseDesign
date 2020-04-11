package com.company.model

import com.company.PercentSplit

class PercentExpenses( amount:Double ,  id:String,  paidBy:RoomMate,  split:List<Split>):Expenses(amount, id,paidBy,split){

    override fun validate(): Boolean {
        for(element in split){
            if(!(element is PercentSplit)){
                return false
            }
        }
        val total = 100;
        var now = 0
        for(element in split){
            now = (now + (element as PercentSplit).percent).toInt()
        }
        if(now!=100){
            return false
        }
        return true
    }
}