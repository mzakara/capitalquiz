package com.quiz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    static QuizGame game;

    @Override
    public void start(Stage stage) {

        Label questionLabel = new Label();
        TextField answerField = new TextField();
        Label resultLabel = new Label();

        Button submit = new Button("Submit");
        VBox root = new VBox(10, questionLabel, answerField, submit, resultLabel);
        
        if (game.hasNext()) {
            questionLabel.setText(game.getQuestion());
        } else {
            questionLabel.setText("No data loaded!");
        }

        submit.setOnAction(e -> {
            if (!game.hasNext()) return;

            // Reset to white for new answer
            root.setStyle("-fx-background-color: white;");

            boolean correct = game.checkAnswer(answerField.getText());

            if (correct) {
                resultLabel.setText("Correct!");
                root.setStyle("-fx-background-color: #90EE90;");
            } else {
                resultLabel.setText("Wrong! Answer: " + game.getCorrectAnswer());
                root.setStyle("-fx-background-color: #FF6B6B;");
            }

            answerField.clear();

            if (game.hasNext()) {
                questionLabel.setText(game.getQuestion());
            } else {
                questionLabel.setText("Finished!");
                resultLabel.setText("Score: " + game.getScore() + "/" + game.getTotal());
                submit.setDisable(true);
            }
        });

        stage.setScene(new Scene(root, 400, 200));
        stage.setTitle("Capital Quiz");
        stage.show();
    }

    public static void main(String[] args) {
        game = new QuizGame(); // load ONCE here
        launch();
    }
}