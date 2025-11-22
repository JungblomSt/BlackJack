package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    var playerCardsList = mutableListOf<Int>()
    var dealerCardsList = mutableListOf<Int>()
    var gameOver = false

    var winCount = 0
    var lossesCount = 0
    var tieCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //val statsButton = findViewById<Button>(R.id.btn_stat)
        binding.btnStat.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.btnNewGame.setOnClickListener {
            newGame()
        }
        binding.btnDraw.setOnClickListener {
            draw()
        }
        binding.btnHold.setOnClickListener {
            hold()
        }

        newGame()

    }

    private fun hold() {
        while (dealerCardsList.sum() < 17) {
            dealerCardsList.add(drawCard())
            showCards()
        }
        checkWinner()
    }

    private fun draw() {
        playerCardsList.add(drawCard())

        showCards()
        if (playerCardsList.sum() >= 21) {
            checkWinner()
        }

    }

    private fun drawCard(): Int {
        return listOf(1,2,3,4,5,6,7,8,9,10,10,10,10).random()
    }

    private fun newGame(){
        gameOver = false

        playerCardsList.clear()
        dealerCardsList.clear()
        binding.tvResultText.text = ""

        playerCardsList.add(drawCard())
        playerCardsList.add(drawCard())

        dealerCardsList.add(drawCard())

        showCards()

    }

    private fun checkWinner() {
        var playerSum = playerCardsList.sum()
        var dealerSum = dealerCardsList.sum()

        var resultText = when {
            playerSum > 21 -> {
                lossesCount += 1
                "You busted! Dealer win!"}
            dealerSum > 21 -> {
                winCount += 1
                "Dealer busted! You win!"
            }
            playerSum > dealerSum -> {
                winCount += 1
                "You win!"
            }
            playerSum < dealerSum -> {
                lossesCount += 1
                "Dealer wins!"
            }
            else -> {
                tieCount += 1
                "It´s a tie!"
            }
        }
        binding.tvResultText.text = resultText
        gameOver = true
    }

    private fun showCards() {
        binding.tvPlayerCard1.text = playerCardsList.toString()
        binding.tvPlayerSumNum.text = playerCardsList.sum().toString()

        binding.tvDealerCard1.text = dealerCardsList.toString()
        binding.tvDealerSumNum.text = dealerCardsList.sum().toString()
    }


}