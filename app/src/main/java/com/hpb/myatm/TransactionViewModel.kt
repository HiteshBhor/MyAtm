package com.hpb.myatm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository
    val allTransactions: LiveData<List<Transaction>>
    val lastTransaction: LiveData<Transaction>
    val remainingAmount: LiveData<Int>
    val remainingHundreds: LiveData<Int>
    val remainingTwoHundreds: LiveData<Int>
    val remainingFiveHundreds: LiveData<Int>
    val remainingTwoThousands: LiveData<Int>

    init {
        val dao= TransactionDatabase.getDatabase(application).getTransactionDao()
        repository= TransactionRepository(dao)
        allTransactions = repository.allTransactions
        lastTransaction = repository.lastTransaction
        remainingAmount = repository.remainingAmount
        remainingHundreds = repository.remainingHundreds
        remainingTwoHundreds = repository.remainingTwoHundreds
        remainingFiveHundreds = repository.remainingFiveHundreds
        remainingTwoThousands = repository.remainingTwoThousands
    }

    fun insertTransaction(transaction: Transaction) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(
            transaction)
    }
}