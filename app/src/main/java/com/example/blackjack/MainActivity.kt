package com.example.blackjack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var dealerCard1: TextView
    lateinit var playerCard1: TextView
    lateinit var dealerSum: TextView
    lateinit var playerSum: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dealerCard1 = findViewById(R.id.tv_player_card_1)
        playerCard1 = findViewById(R.id.tv_player_card_1)
        dealerSum = findViewById(R.id.tv_dealer_sum_num)
        playerSum = findViewById(R.id.tv_player_sum_num)



        fun drawCard(): Int {
            return listOf(1,2,3,4,5,6,7,8,9,10,10,10,10).random()
        }

        val statsButton = findViewById<Button>(R.id.btn_stat)
        statsButton.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

    }
}