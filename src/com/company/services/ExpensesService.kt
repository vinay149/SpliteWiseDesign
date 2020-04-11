package com.company.services

import com.company.PercentSplit
import com.company.model.*

object ExpensesService {
    fun createExpense(expenseType: ExpensesType?, amount: Double, paidBy: RoomMate?, splits: List<Split>): Expenses? {
        return when (expenseType) {
            ExpensesType.EXACT -> ExactExpenses(amount, "", paidBy!!, splits)
            ExpensesType.PERCENT -> {
                for (split in splits) {
                    val percentSplit = split as PercentSplit
                    split.amount = amount * percentSplit.percent / 100.0
                }
                PercentExpenses(amount, "", paidBy!!, splits)
            }
            ExpensesType.EQUAL -> {
                val totalSplits = splits.size
                val splitAmount = Math.round(amount * 100 / totalSplits).toDouble() / 100.0
                for (split in splits) {
                    split.amount = splitAmount
                }
                splits[0].amount = splitAmount + (amount - splitAmount * totalSplits)
                EqualExpenses(amount, "", paidBy!!, splits)
            }
            else -> null
        }
    }
}