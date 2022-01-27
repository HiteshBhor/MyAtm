package com.hpb.myatm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionsRVAdapter(private val context: Context): RecyclerView.Adapter<TransactionsRVAdapter.TransactionViewHolder>() {

    private val allTransactions = ArrayList<Transaction>()

    inner class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvHundred: TextView = itemView.findViewById<TextView>(R.id.tv_hundred)
        val tvTwoHundred: TextView = itemView.findViewById<TextView>(R.id.tv_twoHundred)
        val tvFiveHundred: TextView = itemView.findViewById<TextView>(R.id.tv_fiveHundred)
        val tvTwoThousand: TextView = itemView.findViewById<TextView>(R.id.tv_twoThousand)
        val tvTotalWithdrawn: TextView = itemView.findViewById<TextView>(R.id.tv_totalWithdrawn)
        //val deleteButton: ImageView = itemView.findViewById<ImageView>(R.id.iv_deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val viewHolder = TransactionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_transactions, parent, false))
        /*viewHolder.deleteButton.setOnClickListener{
            listener.onItemClicked(allTransactions[viewHolder.adapterPosition])
        }*/
        return viewHolder
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentTransaction = allTransactions[position]
        holder.tvHundred.text = currentTransaction.hundred.toString()
        holder.tvTwoHundred.text = currentTransaction.twoHundred.toString()
        holder.tvFiveHundred.text = currentTransaction.fiveHundred.toString()
        holder.tvTwoThousand.text = currentTransaction.twoThousand.toString()
        holder.tvTotalWithdrawn.text = currentTransaction.totalWithdrawn.toString()
    }

    override fun getItemCount(): Int {
        return allTransactions.size
    }

    fun updateList(newList: List<Transaction>){
        allTransactions.clear()
        allTransactions.addAll(newList)

        notifyDataSetChanged()
    }

}