package com.blackjack;

import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Cards extends Parent {

    public Cards(Types types, Numbers num) {
        this.types = types;
        this.num = num;
        this.value = num.value;

        Rectangle bg = new Rectangle(80,100);
        bg.setArcWidth(20);
        bg.setArcHeight(20);
        bg.setFill(Color.WHITE);

        Text text = new Text(toString());
        text.setWrappingWidth(70);

        getChildren().add(new StackPane(bg,text));
    }

    enum Types{
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    enum Numbers{
      TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        final int value;
        private Numbers(int value) {
            this.value = value;
        }
    }

    public final Types types;
    public final Numbers num;
    public final int value;

    @Override
    public String toString(){
        return num.toString() + " of " + types.toString();
    }
}
