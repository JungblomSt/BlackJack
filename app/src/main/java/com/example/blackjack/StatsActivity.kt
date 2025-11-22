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

        val backButton = findViewById<Button>(R.id.btn_stat_back)
        backButton.setOnClickListener {
            finish()
        }
    }
}