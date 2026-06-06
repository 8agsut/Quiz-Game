package org.example.quiz;

public class MultipleChoiceQuestion extends Question {
    private String[] options;
    private String correctAnswer;

    public MultipleChoiceQuestion(String text, String[] options, String correctAnswer) {
        super(text);
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public boolean checkAnswer(String playerGuess) {
        return this.correctAnswer.equalsIgnoreCase(playerGuess.trim());
    }
}
