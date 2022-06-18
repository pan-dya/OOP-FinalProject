package com.blackjack;

//import
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import com.blackjack.Cards.Numbers;

public class Hand {
    private ObservableList<Node> cards;         //observable list to listen to listeners
    private SimpleIntegerProperty value = new SimpleIntegerProperty(0); //simple integer property creates a getter, setter, and allows listeners to make changes
    private int aces = 0;   //count the aces

    //constructor
    public Hand(ObservableList<Node> cards){
        this.cards = cards;
    }

    public void takeCard(Cards card){   //  add values of cards to the hand
        cards.add(card);
        if (card.num == Numbers.ACE){   // ace counter
            aces++;
        }

        if (value.get() + card.value > 21 && aces > 0){
            value.set(value.get() + card.value - 10);
            aces--;
        }
        else {
            value.set(value.get() + card.value);
        }
    }

    public void reset(){    //reset hand
        cards.clear();
        value.set(0);
        aces = 0;
    }

    public SimpleIntegerProperty valueProperty(){   //to access the value, because value is private
        return value;
    }
}
