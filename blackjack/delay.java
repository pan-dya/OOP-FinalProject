package com.blackjack;
import javafx.concurrent.Task;

public interface delay {
    //delay method
    static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {     //stop the task
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
