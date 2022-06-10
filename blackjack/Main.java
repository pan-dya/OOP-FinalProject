package com.blackjack;

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
import javafx.stage.Stage;

public class Main extends Application{

    private Decks decks = new Decks();
    private Hand dealer, player;
    private Text winner = new Text();

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private HBox dealerCards = new HBox(20);
    private HBox playerCards = new HBox(20);

    private Parent createContent() {
        dealer = new Hand(dealerCards.getChildren());
        player = new Hand(playerCards.getChildren());

        Pane main = new Pane();
        main.setPrefSize(800, 600);

        Region background = new Region();
        background.setPrefSize(800, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        HBox mainLayout = new HBox(5);
        mainLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle left = new Rectangle(550, 550);
        left.setFill(Color.GREEN);
        Rectangle right = new Rectangle(230, 550);
        right.setFill(Color.LIGHTGREEN);

        // LEFT

        VBox leftVBox = new VBox(50);
        leftVBox.setAlignment(Pos.TOP_CENTER);

        Text dealerScore = new Text("Dealer: ");
        Text playerScore = new Text("Player: ");

        leftVBox.getChildren().addAll(dealerScore, dealerCards, winner, playerCards, playerScore);

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
        mainLayout.getChildren().addAll(new StackPane(left, leftVBox), new StackPane(right, rightVBox));
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

    private void startGame(){
        playable.set(true);
        winner.setText("");
        decks.refill();
        dealer.reset();
        player.reset();

        dealer.takeCard(decks.drawCard());
        player.takeCard(decks.drawCard());
        dealer.takeCard(decks.drawCard());
        player.takeCard(decks.drawCard());
    }

    private void endGame(){
        playable.set(false);
        int dealerValue = dealer.valueProperty().get();
        int playerValue = player.valueProperty().get();

        if(dealerValue == playerValue){
            winner.setText("DRAW");
        }

        else if (dealerValue == 21 || playerValue > 21 || dealerValue < 21 && playerValue < dealerValue){
            winner.setText("DEALER WON");
        }

        else if (playerValue == 21 || dealerValue > 21 || playerValue < 21 && dealerValue < playerValue){
            winner.setText("PLAYER WON");
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlackJack");
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}