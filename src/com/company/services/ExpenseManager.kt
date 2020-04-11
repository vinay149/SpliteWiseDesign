package com.company.services

import com.company.model.Expenses
import com.company.model.ExpensesType
import com.company.model.RoomMate
import com.company.model.Split

class ExpenseManager{
    var listOfExpenses:List<Expenses> = ArrayList()
    var userList:MutableMap<String,RoomMate> = HashMap()
    var balanceToAllOthers:MutableMap<String,MutableMap<String,Double>> = HashMap()


    fun getBalanceOfParticularUser(user: RoomMate){
        val iterator = balanceToAllOthers[user.id]?.iterator()
        iterator?.let {
            while (iterator.hasNext()) {
                 val entry = iterator.next()
                 print("${user.name} has owe to ${entry.key} of amount ${entry.value}")
            }
        }
    }


    fun init(userList:MutableMap<String,RoomMate>){
        val iterator  = userList.iterator()
        while (iterator.hasNext()){
            val itr = userList.iterator()
            val balance:HashMap<String,Double> = HashMap()
            val usr = iterator.next().key
            while (itr.hasNext()){
                val addUser = itr.next().key
                if(addUser != usr){
                    balance[addUser] = 0.0
                }
            }
            balanceToAllOthers[usr] = balance
        }
    }

    fun addAllUser(listOfAllUser: MutableList<RoomMate>) {
        for(element in listOfAllUser) {
            userList.put(element.id,element)
        }
        init(userList)
    }

    fun addExpense(expensesType: ExpensesType, amount:Double, paidBy:String, splits:List<Split>){
        val expenseService = ExpensesService.createExpense(expensesType,amount,userList[paidBy],splits)
        if(expenseService?.validate()==true){
            val balance:MutableMap<String,Double> = HashMap()
            for( split in expenseService.split){
                val user = split.user
                if(user.id != paidBy){
                    val debtOnThat:Double? = balanceToAllOthers[paidBy]?.getOrDefault(user.id,0.0)
                    var totalAmount:Double? = balanceToAllOthers[user.id]?.getOrDefault(paidBy,0.0)
                    if (totalAmount != null) {
                        totalAmount += split.amount
                        if (debtOnThat != null) {
                            if(debtOnThat<totalAmount){
                                totalAmount -= debtOnThat
                            }
                        }
                    }
                    println("${user.id}totalAmount${totalAmount}")
                    if (totalAmount != null) {
                        balanceToAllOthers[user.id]?.put(paidBy,totalAmount)
                    }
                }
            }
        }
    }

    fun showAllBalances() {

    }
}