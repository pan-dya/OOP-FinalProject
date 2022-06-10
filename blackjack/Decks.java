package com.blackjack;

import com.blackjack.Cards.Numbers;
import com.blackjack.Cards.Types;

public class Decks {

    private Cards[] cards = new Cards[52];

    public Decks(){
        refill();
    }

    public final void refill(){
        int i = 0;
        for (Types types : Types.values()){
            for(Numbers num : Numbers.values()){
                cards[i++] = new Cards(types, num);
            }
        }
    }

    public Cards drawCard(){
        Cards card = null;
        while (card == null){
            int index = (int)(Math.random()*cards.length);
            card = cards[index];
            cards[index] = null;
        }
        return card;
    }
}
