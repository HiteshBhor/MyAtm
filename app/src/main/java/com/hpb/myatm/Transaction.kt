package com.hpb.myatm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions_table")
 class Transaction(

    @ColumnInfo(name = "totalWithdrawn")
    val totalWithdrawn: Int,

    @ColumnInfo(name = "hundred")
    val hundred: Int,

    @ColumnInfo(name = "twoHundred")
    val twoHundred: Int,

    @ColumnInfo(name = "fiveHundred")
    val fiveHundred: Int,

    @ColumnInfo(name = "twoThousand")
    val twoThousand: Int

) {

    @PrimaryKey(autoGenerate = true)
    var id = 0

}