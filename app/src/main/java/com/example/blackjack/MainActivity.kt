package com.example.blackjack

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.blackjack.databinding.ActivityMainBinding
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity(){
    lateinit var binding : ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var settingsOverlay: View

    val deck = Deck()
    var playerHand = mutableListOf<Card>()
    var dealerHand = mutableListOf<Card>()
    var gameOver = false

    var winCount = 0
    var lossesCount = 0
    var tieCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsOverlay = findViewById(R.id.settingsOverlay)

        activateButtons()
        observeShowCountSetting()

        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)
        loadStats(prefs)

        newGame()

    }

    // Save the Stats when you come back from StatsActivity (if reset)
    override fun onResume() {
        super.onResume()
        loadStats(getSharedPreferences("blackjack_stats", MODE_PRIVATE))
    }

    //---------------- Game functions -------------------------//
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

    //---------------- Math -------------------------//
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
            playerSum > 21 -> {lossesCount++; getString(R.string.you_busted)}
            dealerSum > 21 -> {winCount++; getString(R.string.dealer_busted)}
            playerSum > dealerSum -> {winCount++; getString(R.string.you_win)}
            playerSum < dealerSum -> {lossesCount++; getString(R.string.dealer_wins)}
            else -> {tieCount++; getString(R.string.it_s_a_tie)
            }
        }
        binding.tvResultText.text = resultText
        saveStats()
        gameOver = true
    }
    private fun startSettingsFragment() {
        val settingsFragment = SettingsFragment()

        settingsOverlay.visibility = View.VISIBLE

        supportFragmentManager.beginTransaction()
            .add(R.id.settingsContainer, settingsFragment, "settingsFragment")
            .commit()
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.settingsContainer, settingsFragment, "settingsFragment")
//        transaction.commit()
    }
    private fun closeSettingsFragment() {
        settingsOverlay.visibility = View.GONE

        val settingsFragment = supportFragmentManager.findFragmentByTag("settingsFragment")
        if (settingsFragment != null) {
            supportFragmentManager.beginTransaction()
                .remove(settingsFragment)
                .commit()
        }
    }

    //---------------- Stats -------------------------//
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
    private fun activateButtons() {
        binding.btnStat.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
        }

        binding.btnNewGame.setOnClickListener {
            newGame()
        }
        binding.btnDraw.setOnClickListener {
            if (!gameOver)draw()
        }
        binding.btnHold.setOnClickListener {
            if (!gameOver)hold()
        }
        binding.ibSettings.setOnClickListener {
            startSettingsFragment()
        }
        settingsOverlay.setOnClickListener {
            closeSettingsFragment()
        }
    }
    private fun observeShowCountSetting() {
        sharedViewModel.textVisible.observe(this) { visible ->

            val visibility = if (visible) View.VISIBLE else View.INVISIBLE

            binding.tvPlayerSumNum.visibility = visibility
            binding.tvDealerSumNum.visibility = visibility
            binding.tvPlayerSumText?.visibility = visibility
            binding.tvDealerSumText?.visibility = visibility

        }
    }

}

// I have used AI for:
// bugg fixes
// SharedViewModel (I knew what I wanted but not how/where to write it)