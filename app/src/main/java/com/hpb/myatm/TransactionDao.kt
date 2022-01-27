package com.hpb.myatm


import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    @Query("SELECT * FROM transactions_table ORDER BY id ASC")
    fun getAllTransactions(): LiveData<List<Transaction>>

    @Query("SELECT * FROM transactions_table ORDER BY id DESC LIMIT 1")
    fun getLastTransaction(): LiveData<Transaction>

    @Query("SELECT SUM(totalWithdrawn) FROM transactions_table")
    fun getRemainingAmount(): LiveData<Int>

    @Query("SELECT SUM(hundred) FROM transactions_table")
    fun getRemainingHundreds(): LiveData<Int>

    @Query("SELECT SUM(twoHundred) FROM transactions_table")
    fun getRemainingTwoHundreds(): LiveData<Int>

    @Query("SELECT SUM(fiveHundred) FROM transactions_table")
    fun getRemainingFiveHundreds(): LiveData<Int>

    @Query("SELECT SUM(twoThousand) FROM transactions_table")
    fun getRemainingTwoThousands(): LiveData<Int>

}