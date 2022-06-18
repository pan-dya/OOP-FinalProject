package com.blackjack;

//import
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Main extends Application implements delay{

    private Decks decks = new Decks();  //create a deck
    private Hand dealer, player;        //create a hand for dealer and player
    private Text winner = new Text();   //winner text, will be shown when the game is over

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false); //set playable to false, for buttons

    //create a horizontal box for the dealer and player
    private HBox dealerCards = new HBox(20);
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        dealer = new Hand(dealerCards.getChildren());   //create hand objects
        player = new Hand(playerCards.getChildren());   //

        Pane main = new Pane();             //create the main layout
        main.setPrefSize(800, 600);

        Region background = new Region();       //create background
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        HBox mainLayout = new HBox(5);  //horizontal box for the main layout
        mainLayout.setPadding(new Insets(5, 5, 5, 5));
        Image table = new Image(getClass().getResourceAsStream("images/table.png"), 550, 550, true, true);
        ImageView view = new ImageView(table);
        view.setX(550);
        view.setY(550);;
        Rectangle right = new Rectangle(230, 550);
        right.setFill(Color.LIGHTGREEN);

        // LEFT

        VBox leftVBox = new VBox(40);
        leftVBox.setAlignment(Pos.CENTER);

        Text dealerScore = new Text("Dealer: ");
        HBox dealerBox = new HBox(15, dealerScore, dealerCards);
        dealerBox.setPrefHeight(200);
        dealerBox.setAlignment(Pos.TOP_CENTER);
        Text playerScore = new Text("Player: ");
        HBox playerBox = new HBox(15, playerScore, playerCards);
        playerBox.setPrefHeight(200);
        playerBox.setAlignment(Pos.TOP_CENTER);

        leftVBox.getChildren().addAll(dealerBox, winner, playerBox);

        // RIGHT

        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);

        Button playBtn = new Button("PLAY");
        Button hitBtn = new Button("HIT");
        Button standBtn = new Button("STAND");

        HBox btnBox = new HBox(15, hitBtn, standBtn);
        btnBox.setAlignment(Pos.CENTER);

        rightVBox.getChildren().addAll(playBtn, btnBox);

        // ADD BOTH PART TO MAIN LAYOUT
        mainLayout.getChildren().addAll(new StackPane(view,  leftVBox),  new StackPane(right, rightVBox));
        main.getChildren().addAll(background, mainLayout);

        //BIND PROPERTIES
        playBtn.disableProperty().bind(playable);
        hitBtn.disableProperty().bind(playable.not());
        standBtn.disableProperty().bind(playable.not());

        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

        player.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 21) {
                endGame();
            }
        });
        dealer.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() >= 21) {
                endGame();
            }
        });

        // INIT BUTTONS

        playBtn.setOnAction(event -> startGame());

        hitBtn.setOnAction(event -> player.takeCard(decks.drawCard()));

        standBtn.setOnAction(event -> {
            while(dealer.valueProperty().get() < 17){
                dealer.takeCard(decks.drawCard());
            }
            endGame();
        });
    return main;
    }
    //START GAME
    private void startGame(){
        playable.set(true);
        winner.setText("");
        decks.refill();
        dealer.reset();
        player.reset();

        dealer.takeCard(decks.drawCard());
        dealer.takeCard(decks.drawCard());
        delay.delay(500, ()->{
            player.takeCard(decks.drawCard());
            player.takeCard(decks.drawCard());
        });

    }

    //END GAME
    private void endGame(){
        playable.set(false);
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();

        if(dealerValue == playerValue){
            delay.delay(300, ()->{
                winner.setText("DRAW");
                winner.setFont(Font.font(24));
                winner.setFill(Color.WHITE);
            });
        }

        else if (dealerValue == 21 || playerValue > 21 || dealerValue < 21 && playerValue < dealerValue){
            delay.delay(300, ()->{
                winner.setText("DEALER WON");
                winner.setFont(Font.font(24));
                winner.setFill(Color.WHITE);
            });
        }

        else {
            delay.delay(300, ()->{
                winner.setText("PLAYER WON");
                winner.setFont(Font.font(24));
                winner.setFill(Color.WHITE);
            });
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));  //create the content
        primaryStage.setWidth(800);                         //set size
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);                   //make the window not resizable
        primaryStage.setTitle("BlackJack");                 //name
        primaryStage.show();                                //show the window
    }

    public static void main(String[] args){ //start the game
        launch(args);
    }
}
