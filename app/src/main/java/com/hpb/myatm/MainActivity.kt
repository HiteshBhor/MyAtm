package com.hpb.myatm

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hpb.myatm.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TransactionViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TransactionsRVAdapter
    var atmAmount = 50000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initObserver()

        binding.btnWithdraw.setOnClickListener {

            atmProcess()

        }

    }

    private fun initView() {

        binding.rvTransactions.layoutManager = LinearLayoutManager(this)
        adapter = TransactionsRVAdapter(this)
        binding.rvTransactions.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(TransactionViewModel::class.java)
    }

    private fun initObserver() {
        viewModel.allTransactions.observe(this, Observer { list ->
            list?.let {
                adapter.updateList(it)
            }
        })

        viewModel.lastTransaction.observe(this, Observer {

            if (it != null) {
                binding.lastTransactionTotalWithdrawn.text = it.totalWithdrawn.toString()
                binding.lastTransactionHundred.text = it.hundred.toString()
                binding.lastTransactionTwoHundred.text = it.twoHundred.toString()
                binding.lastTransactionFiveHundred.text = it.fiveHundred.toString()
                binding.lastTransactionTwoThousand.text = it.twoThousand.toString()
            }
        })

        viewModel.remainingAmount.observe(this, Observer {

            if (it == null) {
                binding.remainingAmount.text = "50000"
            } else {
                binding.remainingAmount.text = (50000 - it).toString()
                atmAmount = 50000 - it

                var moneyValue = atmAmount
                val noteValues = intArrayOf(2000, 500, 200, 100)


                var i = 0
                var hundred = 0
                var twoHundred = 0
                var fiveHundred = 0
                var twoThousand = 0

                while (i < noteValues.size && moneyValue != 0) {

                    if (moneyValue >= noteValues[i]) {

                        when (noteValues[i]) {
                            2000 -> twoThousand = moneyValue / noteValues[i]
                            500 -> fiveHundred = moneyValue / noteValues[i]
                            200 -> twoHundred = moneyValue / noteValues[i]
                            else -> hundred = moneyValue / noteValues[i]
                        }

                    }
                    moneyValue %= noteValues[i]
                    i++
                }

                binding.remaining100s.text = hundred.toString()
                binding.remaining200s.text = twoHundred.toString()
                binding.remaining500s.text = fiveHundred.toString()
                binding.remaining2000s.text = twoThousand.toString()
            }
        })

/*viewModel.remainingHundreds.observe(this, Observer {

    if (it == null) {
        binding.remaining100s.text = "75"
    }else{
        binding.remaining100s.text = (75 - it).toString()
    }
})

viewModel.remainingTwoHundreds.observe(this, Observer {

    if (it == null) {
        binding.remaining200s.text = "50"
    }else{
        binding.remaining200s.text = (50 - it).toString()
    }
})

viewModel.remainingFiveHundreds.observe(this, Observer {

    if (it == null) {
        binding.remaining500s.text = "25"
    }else{
        binding.remaining500s.text = (25 - it).toString()
    }
})

viewModel.remainingTwoThousands.observe(this, Observer {

    if (it == null) {
        binding.remaining2000s.text = "10"
    }else{
        binding.remaining2000s.text = (10 - it).toString()
    }
})*/
    }

    private fun atmProcess() {
        var moneyValue = binding.etWithdrawAmount.text.toString().toInt()
        val noteValues = intArrayOf(2000, 500, 200, 100)

        if (moneyValue % 100 == 0) {
            if (moneyValue > atmAmount) {
                Toast.makeText(this@MainActivity, "ATM Cash Limit exceeds.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var i = 0
                var hundred = 0
                var twoHundred = 0
                var fiveHundred = 0
                var twoThousand = 0

                while (i < noteValues.size && moneyValue != 0) {

                    if (moneyValue >= noteValues[i]) {

                        when (noteValues[i]) {
                            2000 -> twoThousand = moneyValue / noteValues[i]
                            500 -> fiveHundred = moneyValue / noteValues[i]
                            200 -> twoHundred = moneyValue / noteValues[i]
                            else -> hundred = moneyValue / noteValues[i]
                        }

                    }
                    moneyValue %= noteValues[i]
                    i++
                }

                hideKeyboard(this)

                viewModel.insertTransaction(
                    Transaction(
                        binding.etWithdrawAmount.text.toString().toInt(),
                        hundred,
                        twoHundred,
                        fiveHundred,
                        twoThousand
                    )
                )

            }
        } else {
            Toast.makeText(
                this@MainActivity,
                "Please enter amount in multiples of 100",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }
}