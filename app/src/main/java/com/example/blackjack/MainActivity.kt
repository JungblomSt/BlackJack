package com.example.blackjack

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.blackjack.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    val deck = Deck()
    var playerHand = mutableListOf<Card>()
    var dealerHand = mutableListOf<Card>()
    var gameOver = false

    var winCount = 0
    var lossesCount = 0
    var tieCount = 0

    //TODO: Kommentering av koden
    //TODO: switch sv - eng och ev något mer
    //TODO: Dealer visar baksidan av ett kort innan player är klar
    //TODO: Lägg till ett fragment

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
        while (handValue(dealerHand) < 17) {
            dealerHand.add(deck.drawCard())
            showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerHand, dealerHand)
        }
        checkWinner()
    }

    private fun draw() {
        playerHand.add(deck.drawCard())


        showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerHand, dealerHand)
        if (handValue(playerHand) >= 21) {
            checkWinner()
        }

    }

    private fun newGame(){
        gameOver = false

        deck.createDeck()
        playerHand.clear()
        dealerHand.clear()
        binding.tvResultText.text = ""


        playerHand.add(deck.drawCard())
        playerHand.add(deck.drawCard())

        dealerHand.add(deck.drawCard())

        showCards(binding.cardLayoutPlayer, binding.cardLayoutDealer,playerHand, dealerHand)


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
        val playerSum = handValue(playerHand)
        val dealerSum = handValue(dealerHand)


        val resultText = when {
            playerSum > 21 -> {
                lossesCount += 1
                getString(R.string.you_busted)}
            dealerSum > 21 -> {
                winCount += 1
                getString(R.string.dealer_busted)
            }
            playerSum > dealerSum -> {
                winCount += 1
                getString(R.string.you_win)
            }
            playerSum < dealerSum -> {
                lossesCount += 1
                getString(R.string.dealer_wins)
            }
            else -> {
                tieCount += 1
                getString(R.string.it_s_a_tie)
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

    // Save the Stats when you come back from StatsActivity (if reset)
    override fun onResume() {
        super.onResume()

        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)
        loadStats(prefs)
    }

    //---------------- UI -------------------------//

    private fun showCards(
        playerContainer: LinearLayout,
        dealerContainer: LinearLayout,
        playerHand: List<Card>,
        dealerHand: List<Card>,
    ) {
        playerContainer.removeAllViews()
        dealerContainer.removeAllViews()

        binding.tvPlayerSumNum.text = handValue(playerHand).toString()
        binding.tvDealerSumNum.text = handValue(dealerHand).toString()

        fun createCardLayout(container: LinearLayout, card: Card) {
            val cardView = layoutInflater.inflate(R.layout.cards_card, container, false)
            val tvCard = cardView.findViewById<TextView>(R.id.tv_card)

            tvCard.text = "${card.number}${card.suit}"

            val cvCard = cardView.findViewById<MaterialCardView>(R.id.cv_card)
            val isRedSuit = card.suit == "♥" || card.suit == "♦"

            cvCard.strokeColor = if (isRedSuit)
                ContextCompat.getColor(this,R.color.red)
            else
                ContextCompat.getColor(this, R.color.black)

            container.addView(cardView)
        }

        playerHand.forEach { createCardLayout(playerContainer, it) }

        dealerHand.forEach { createCardLayout(dealerContainer, it) }


    }

}