package com.blackjack;

//import from Cards.java
import com.blackjack.Cards.Numbers;
import com.blackjack.Cards.Types;

public class Decks{

    //create an array of the deck
    private Cards[] cards = new Cards[52];

    public Decks(){
        refill();
    }

    //refill method to refill the deck
    public final void refill(){
        int i = 0;
        for (Types types : Types.values()){         // for each loop
            for(Numbers num : Numbers.values()){    // for each loop
                cards[i++] = new Cards(types, num); // add the card to the deck
            }
        }
    }

    //draw card from the deck
    public Cards drawCard(){
        Cards card = null;          // a temporary card
        while (card == null){
            int index = (int)(Math.random()*cards.length);     //random index of card
            card = cards[index];
            cards[index] = null;    //empty the index of the deck
        }
        return card;
    }
}
