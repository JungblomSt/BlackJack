package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var dealerCard1: TextView
    lateinit var playerCard1: TextView
    lateinit var dealerSum: TextView
    lateinit var playerSum: TextView

    var playerCardsList = mutableListOf<Int>()
    var dealerCardsList = mutableListOf<Int>()
    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //val statsButton = findViewById<Button>(R.id.btn_stat)
        binding.btnStat.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }
        newGame()

        binding.btnNewGame.setOnClickListener {
            newGame()
        }
        binding.btnDraw.setOnClickListener {
            playerCardsList.add(drawCard())

            showCards()
        }

    }

    private fun drawCard(): Int {
        return listOf(1,2,3,4,5,6,7,8,9,10,10,10,10).random()
    }

    private fun newGame(){
        gameOver = false

        playerCardsList.clear()
        dealerCardsList.clear()

        playerCardsList.add(drawCard())
        playerCardsList.add(drawCard())

        dealerCardsList.add(drawCard())

        showCards()
    }

    private fun showCards() {
        binding.tvPlayerCard1.text = playerCardsList.toString()
        binding.tvPlayerSumNum.text = playerCardsList.sum().toString()

        binding.tvDealerCard1.text = dealerCardsList.toString()
        binding.tvDealerSumNum.text = dealerCardsList.sum().toString()
    }


}