package org.example.quiz;

public abstract class Question {
    private String text;
    public Question(String text) {
        this.text = text;
    }
    public String getText() {
        return text;
    }

    public abstract boolean checkAnswer(String playerGuess);
}
