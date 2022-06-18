package com.blackjack;

//import
import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

//extends Parent needed to use getChildren()
public class Cards extends Parent{

    //Constants for card size
    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;

    //Cards constructor
    public Cards(Types types, Numbers num) {
        this.types = types;
        this.num = num;
        this.value = num.value;

        //set the size of the cards
        Rectangle bg = new Rectangle(CARD_WIDTH,CARD_HEIGHT);
        bg.setArcWidth(20);         //add curve to the cards
        bg.setArcHeight(20);        //add curve to the cards
        bg.setFill(Color.WHITE);    //fill the cards with color

        //display the number on the top right
        Text text = new Text(num.displayName());
        text.setFont(Font.font(18));
        text.setX(CARD_WIDTH - text.getLayoutBounds().getWidth() - 10);
        text.setY(text.getLayoutBounds().getHeight());

        //display the number on bottom left
        Text text2 = new Text(text.getText());
        text2.setFont(Font.font(18));
        text2.setX(10);
        text2.setY(CARD_HEIGHT - 10);

        //adding the card suits(types) //top left
        ImageView view = new ImageView(types.image);
        view.setX(CARD_WIDTH - 32);
        view.setY(CARD_HEIGHT - 32);

        //adding the contents to the scene
        getChildren().addAll(bg, new ImageView(types.image), view, text, text2);
    }

    //use of enum for different card types
    enum Types{
        HEARTS, DIAMONDS, CLUBS, SPADES;

        // creating image
        Image image;

        Types() {
            this.image = new Image(Cards.class.getResourceAsStream("images/".concat(name().toLowerCase()).concat(".png")), 32, 32, true, true);
        }
    }

    //use of enum for numbers
    enum Numbers{
      TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

      //constructor for the value of the cards
        final int value;
        private Numbers(int value) {
            this.value = value;
        }

        //method to display the number / rank of the card
        String displayName(){
            if (ordinal() < 9){
                return String.valueOf(value);
            } else {
                return name().substring(0,1);
            }
        }
    }

    // Objects
    public Types types;
    public Numbers num;
    public int value;
}
