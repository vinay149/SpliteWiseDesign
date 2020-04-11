package com.company

import com.company.model.*
import com.company.services.ExpenseManager
import java.util.*
import kotlin.collections.ArrayList


object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val reader = Scanner(System.`in`)


        val listOfAllUser: MutableList<RoomMate> = ArrayList()
        val user1 = RoomMate("Vinay", "v1", "vinay@gmail.com", "9044804751")
        val user2 = RoomMate("Ankit", "a1", "ankit@gmail.com", "9044804751")
        val user3 = RoomMate("Apurva", "ap", "apurva@gmail.com", "9044804751")
        listOfAllUser.add(user1)
        listOfAllUser.add(user2)
        listOfAllUser.add(user3)
        val expenseManager = ExpenseManager()
        expenseManager.addAllUser(listOfAllUser)
        while (true) {
            val input = reader.nextLine()
            val command = input.split(" ")
            val type = command[0]
            when (type) {
                "SHOW" -> {
                    if (command.size == 1) {
                        expenseManager.showAllBalances()
                    } else {
                        expenseManager.userList[command[1]]?.let {
                            expenseManager.getBalanceOfParticularUser(it)
                        }
                    }
                }
                "EXPENSE" -> {
                    val paidBy = command[1]
                    val amount = command[2].toDouble()
                    val numberOfUser = command[3].toInt()
                    val type1 = command[4 + numberOfUser]
                    val splits: MutableList<Split> = ArrayList()
                    when (type1) {
                        ExpensesType.EQUAL.name -> {
                            for (i in 0 until numberOfUser) {
                                expenseManager.userList[(command.get(4 + i))]?.let {
                                    splits.add(EqualSplit(it))
                                }
                            }
                            println("Adding Amount Of Equal split${amount}")
                            expenseManager.addExpense(ExpensesType.EQUAL, amount, paidBy, splits)
                        }
                        ExpensesType.EXACT.name -> {
                            for (i in 0 until numberOfUser) {
                                expenseManager.userList[(command[4 + i])]?.let {
                                    splits.add(ExactSplit(it, command[5 + numberOfUser + i].toDouble()))
                                }
                            }
                            expenseManager.addExpense(ExpensesType.EXACT, amount, paidBy, splits)
                        }
                        ExpensesType.PERCENT.name -> {
                            for (i in 0 until numberOfUser) {
                                expenseManager.userList[(command[4 + i])]?.let {
                                    splits.add(PercentSplit(it, command[5 + numberOfUser + i].toDouble()))
                                }
                            }
                            expenseManager.addExpense(ExpensesType.PERCENT, amount, paidBy, splits)
                        }
                    }
                }

            }
        }

    }
}