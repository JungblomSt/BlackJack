package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blackjack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var dealerCard1: TextView
    lateinit var playerCard1: TextView
    lateinit var dealerSum: TextView
    lateinit var playerSum: TextView

    var playerCards = mutableListOf<Int>()
    var dealerCards = mutableListOf<Int>()
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
            playerCards.add(drawCard())

            showCards()
        }

    }

    private fun drawCard(): Int {
        return listOf(1,2,3,4,5,6,7,8,9,10,10,10,10).random()
    }

    private fun newGame(){
        gameOver = false
        playerCards.add(drawCard())
        playerCards.add(drawCard())

        dealerCards.add(drawCard())

        showCards()
    }

    private fun showCards() {
        binding.tvPlayerCard1.text = playerCards.toString()
        binding.tvPlayerSumNum.text = playerCards.sum().toString()

        binding.tvDealerCard1.text = dealerCards.toString()
        binding.tvDealerSumNum.text = dealerCards.sum().toString()
    }


}