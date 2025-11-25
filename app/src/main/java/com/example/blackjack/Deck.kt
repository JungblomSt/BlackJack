package com.example.blackjack

class Deck {

    val numbers = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    val suits = listOf("♥","♦","♣","♠",)

    val cards = mutableListOf<Card>()

    init {
        for (suit in suits) {
            for (number in numbers){
                val value = when (number){
                    "A" -> 11
                    "J", "Q", "K" -> 10
                    else -> number.withIndex() + 1
                }
                cards.add(Card(number, suit, value as Int))
            }

        }
        cards.shuffle()
    }
}