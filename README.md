# Black Jack

#Start of the asignment - Make a plan:  
https://www.figma.com/design/HoUXizfgJMcKSe3ByLwB21/Black-Jack?node-id=0-1&p=f&t=N8abdxwg6ebdxGWS-0

<img width="768" height="710" alt="Skärmavbild 2025-11-18 kl  17 17 26" src="https://github.com/user-attachments/assets/6761ef22-9984-4410-a04b-b19902c85017" />


The goal of Blackjack is to get a hand with a higher total value than the dealer, while not going over 21.   
When the dealer or player goes over 21, the hand becomes what is known in Blackjack parlance as “busting” - which always results in a loss of the round.

Kards:  
Ace: Either the value 1 or 11. You decide which of these values ​​the card has.  
Faced cards: All face cards are worth 10  
Other cards: All other cards are worth their numerical value (a 3 is worth 3, a 5 is worth 5, etc.).  

Dealing of Cards:   
Players and Dealer are each dealt 2 cards. Players' cards are face up. The Dealer has one face up card and one face down card.  
Player's Choice: Before the dealer reveals his face down card, players can choose to take another card ore stand.  
Dealer's Choice: The dealer reveals the hole card and takes another card until the hand has a value of at least 17,(or take cards until over player in value, or are bursting) and then the game ends.

Vinning:  
To win a Blackjack hand, you need to have a hand with a total value closer to 21 than the dealer.

# Result:  
A simple but working BlacJack app built in Kotlin with Android Studio.  
It contains gamelogic, statistics, settings and UI  

Functions:  
Game:  
Full game ruond: Draw (Hit), Hold and New Game  
Dealer draw cards until sum 17  
Ace counts as 11 ore 1  
Result message (Vinn, lose or tie)  

Statistics:  
Saved in Shered Preferense  
Counts: Wins, Losses and Tie  
Statistics view with reset button  

Settings:  
Show/Hide gamers result sum  
Lock/rotate screen orientation (portrate/landscape)  
Close the settings fragment by clicking the settings button or outside the fragment view  
Options for language in English and Swedish (phone settings)

Card & Deck:  
Carddeck with 52 cards  
Shuffle  
Visual cards with coloring depending on the suit (red/black)  

Files:  
- MainActivity – main game + UI
- Deck – carddeck and draw-card function
- Card (data class) – defines a card
- SharedViewModel – handels Live Data for UI settings
- SettingsFragment – settings menu
- StatsActivity – shows statistics

<img width="300" height="700" alt="Screenshot BlackJack Start Swe" src="https://github.com/user-attachments/assets/f64a8805-176f-40b8-861e-0f4a8954dda4" />
<img width="300" height="700" alt="Screenshot BlackJack Stats Swe" src="https://github.com/user-attachments/assets/c81ff709-707d-4165-a1c6-8cabcf8c2383" />
<img width="300" height="700" alt="Screenshot BlackJack Settings Swe" src="https://github.com/user-attachments/assets/069d96ae-a392-43dc-8040-f5c96ec23fdb" />

<img width="700" height="300" alt="Screenshot BlackJack Main Swe Land" src="https://github.com/user-attachments/assets/13b51531-f125-4ae0-addd-20ad41289727" />  
<img width="700" height="300" alt="Screenshot BlackJack Settings Swe Land" src="https://github.com/user-attachments/assets/6f7e9929-1f96-40dd-b8f1-f5ad67da47ea" />  


