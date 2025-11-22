package com.example.blackjack

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blackjack.databinding.ActivityMainBinding
import com.example.blackjack.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)

        val wins = prefs.getInt("wins", 0)
        binding.tvVictorysCount.text = "$wins"

        val losses = prefs.getInt("losses", 0)
        binding.tvLosesCount.text = "$losses"

        val ties = prefs.getInt("ties", 0)
        binding.tvTieCount.text = "$ties"




        val backButton = findViewById<Button>(R.id.btn_stat_back)
        backButton.setOnClickListener {
            finish()
        }
    }
}