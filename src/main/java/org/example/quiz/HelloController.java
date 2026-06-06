package org.example.quiz;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML private Label questionLabel;
    @FXML private Button A;
    @FXML private Button B;
    @FXML private Button C;
    @FXML private Button D;
    @FXML private Label scoreLabel;

    private MultipleChoiceQuestion[] quizData;
    private int index = 0;
    private int correct_guess = 0;
    private int total_questions;

    @FXML
    public void initialize() {
        loadQuestionsFromFile();
        nextQuestion();
    }

    private void loadQuestionsFromFile() {
        List<MultipleChoiceQuestion> temporaryList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader (getClass().getResourceAsStream ("/org/example/quiz/questions.txt"))))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                // Splits records cleanly using the pipe delimiter
                String[] parts = line.split("\\|");
                if (parts.length == 6)
                {
                    String questionText = parts[0];
                    String[] options = {parts[1], parts[2], parts[3], parts[4]};
                    String answer = parts[5];
                    temporaryList.add(new MultipleChoiceQuestion(questionText, options, answer));
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Critical Error loading questions file: " + e.getMessage());
            e.printStackTrace();
        }

        quizData = temporaryList.toArray(new MultipleChoiceQuestion[0]);
        total_questions = quizData.length;
    }

    public void nextQuestion() {
        if (index >= total_questions) {
            results();
        } else {
            MultipleChoiceQuestion currentQ = quizData[index];

            questionLabel.setText(currentQ.getText());
            A.setText(currentQ.getOptions()[0]);
            B.setText(currentQ.getOptions()[1]);
            C.setText(currentQ.getOptions()[2]);
            D.setText(currentQ.getOptions()[3]);
            scoreLabel.setText(String.valueOf(correct_guess));
        }
    }

    @FXML
    public void answerClick(ActionEvent event) {
        Object source = event.getSource();
        String playerGuess = " ";
        if(source == A){
            playerGuess = "A";
        }
        else if (source == B) {
            playerGuess = "B";
        }
        else if(source == C){
            playerGuess = "C";
        }
        else if(source == D){
            playerGuess = "D";
        }

        if(quizData[index].checkAnswer(playerGuess)){
            correct_guess++;
            scoreLabel.setText(String.valueOf(correct_guess));
        }
        displayAnswer(playerGuess);
    }

    public void displayAnswer(String playerGuess) {
        A.setDisable(true);
        B.setDisable(true);
        C.setDisable(true);
        D.setDisable(true);

        String correctAnswer = quizData[index].getCorrectAnswer();

        if (correctAnswer.equals("A")){
            A.getStyleClass().add("correct");
        }
        if (correctAnswer.equals("B")){
            B.getStyleClass().add("correct");
        }
        if (correctAnswer.equals("C")) {
            C.getStyleClass().add("correct");
        }
        if (correctAnswer.equals("D")){
            D.getStyleClass().add("correct");
        }

        if (!playerGuess.equals(correctAnswer)) {
            if (playerGuess.equals("A")) {
                A.getStyleClass().add("wrong");
            }
            if (playerGuess.equals("B")) {
                B.getStyleClass().add("wrong");
            }
            if (playerGuess.equals("C")){
                C.getStyleClass().add("wrong");
            }
            if (playerGuess.equals("D")){
                D.getStyleClass().add("wrong");
            }
        }

        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));

        pause.setOnFinished(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                A.getStyleClass().remove("correct");
                A.getStyleClass().remove("wrong");
                B.getStyleClass().remove("correct");
                B.getStyleClass().remove("wrong");
                C.getStyleClass().remove("correct");
                C.getStyleClass().remove("wrong");
                D.getStyleClass().remove("correct");
                D.getStyleClass().remove("wrong");
                A.setDisable(false);
                B.setDisable(false);
                C.setDisable(false);
                D.setDisable(false);
                index++;
                nextQuestion();
            }
        });
        pause.play();
    }

    public void results() {
        A.setDisable(true);
        B.setDisable(true);
        C.setDisable(true);
        D.setDisable(true);
        questionLabel.setText("Quiz Completed!");
        int percentage = (int) (((double) correct_guess / total_questions) * 100);
        A.setText("Total Questions: " + total_questions);
        B.setText("Correct: " + correct_guess);
        C.setText("Wrong: " + (total_questions - correct_guess));
        D.setText("Final Score: " + percentage + "%");
    }
}