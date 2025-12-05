package com.example.blackjack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.blackjack.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("blackjack_stats", MODE_PRIVATE)

        val wins = prefs.getInt("wins", 0)
        binding.tvVictoriesCount.text = "$wins"

        val losses = prefs.getInt("losses", 0)
        binding.tvLosesCount.text = "$losses"

        val ties = prefs.getInt("ties", 0)
        binding.tvTieCount.text = "$ties"

        binding.btnStatBack.setOnClickListener {
            finish()
        }
        binding.btnStatReset.setOnClickListener {
            prefs.edit()
                .putInt("wins", 0)
                .putInt("losses", 0)
                .putInt("ties", 0)
                .apply()

            binding.tvVictoriesCount.text = "0"
            binding.tvLosesCount.text = "0"
            binding.tvTieCount.text = "0"
        }
    }
}