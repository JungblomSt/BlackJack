package com.example.blackjack

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val deck = Deck()
    var playerCardsList = mutableListOf<Card>()
    var dealerCardsList = mutableListOf<Card>()
    var gameOver = false

    var winCount = 0
    var lossesCount = 0
    var tieCount = 0

    //TODO: Kommentering av koden

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

        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)
        loadStats(prefs)

        newGame()

    }

    private fun hold() {
        while (handValue(dealerCardsList) < 17) {
            dealerCardsList.add(deck.drawCard())
            showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerCardsList, dealerCardsList)
        }
        checkWinner()
    }

    private fun draw() {
        playerCardsList.add(deck.drawCard())


        showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerCardsList, dealerCardsList)
        if (handValue(playerCardsList) >= 21) {
            checkWinner()
        }

    }

    private fun newGame(){
        gameOver = false

        deck.createDeck()
        playerCardsList.clear()
        dealerCardsList.clear()
        binding.tvResultText.text = ""


        playerCardsList.add(deck.drawCard())
        playerCardsList.add(deck.drawCard())

        dealerCardsList.add(deck.drawCard())

        showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerCardsList, dealerCardsList)


    }

    private fun handValue(hand: List<Card>): Int {
        var value = hand.sumOf { it.value }
        var aces = hand.count {it.number == "A"}

        while (value > 21 && aces > 0) {
            value -= 10
            aces--
        }
        return value
    }

    private fun checkWinner() {
        val playerSum = handValue(playerCardsList)
        val dealerSum = handValue(dealerCardsList)


        val resultText = when {
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
        saveStats()
        gameOver = true
    }

    private fun saveStats() {
        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)
        prefs.edit()
            .putInt("wins", winCount)
            .putInt("losses", lossesCount)
            .putInt("ties", tieCount)
            .apply()
    }

    private fun loadStats(prefs: SharedPreferences){
        winCount = prefs.getInt("wins", 0)
        lossesCount = prefs.getInt("losses", 0)
        tieCount = prefs.getInt("ties", 0)

    }

    private fun showCards(
        playerContainer: LinearLayout,
        dealerContainer: LinearLayout,
        playerCardList: List<Card>,
        dealerCardList: List<Card>,
    ) {
        playerContainer.removeAllViews()
        dealerContainer.removeAllViews()

        binding.tvPlayerSumNum.text = handValue(playerCardList).toString()
        binding.tvDealerSumNum.text = handValue(dealerCardList).toString()

        fun createCardLayout(container: LinearLayout, card: Card) {
            val cardView = layoutInflater.inflate(R.layout.cards_card, container, false)
            val tvCard = cardView.findViewById<TextView>(R.id.tv_card)

            tvCard.text = "${card.number}${card.suit}"

            container.addView(cardView)
        }

        playerCardList.forEach { createCardLayout(playerContainer, it) }

        dealerCardList.forEach { createCardLayout(dealerContainer, it) }

    //TODO: Dealer visar baksidan av ett kort innan player är klar
    // TODO: snyggare kort

    }


}