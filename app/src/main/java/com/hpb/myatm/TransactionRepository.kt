package com.hpb.myatm

import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()
    val lastTransaction: LiveData<Transaction> = transactionDao.getLastTransaction()
    val remainingAmount: LiveData<Int> = transactionDao.getRemainingAmount()
    val remainingHundreds: LiveData<Int> = transactionDao.getRemainingHundreds()
    val remainingTwoHundreds: LiveData<Int> = transactionDao.getRemainingTwoHundreds()
    val remainingFiveHundreds: LiveData<Int> = transactionDao.getRemainingFiveHundreds()
    val remainingTwoThousands: LiveData<Int> = transactionDao.getRemainingTwoThousands()

    suspend fun insert(transaction: Transaction){
        transactionDao.insert(
            transaction)
    }
}