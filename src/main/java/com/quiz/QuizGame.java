package com.quiz;

import java.util.*;

public class QuizGame {

    private Map<String, String> data;
    private List<String> countries;
    private int score = 0;
    private int index = 0;

    public QuizGame() {
        data = ApiService.getCountries();
        if (data == null || data.isEmpty()){
            throw new RuntimeException("no countries loaded from API");
        }
        countries = new ArrayList<>(data.keySet());
        Collections.shuffle(countries);
        System.out.println("Countries loaded: " + data.size());
    }

    public boolean hasNext() {
        return index < countries.size();
    }

    public String getQuestion() {
        return "Capital of " + countries.get(index) + "?";
    }

    public boolean checkAnswer(String answer) {
        String country = countries.get(index);
        String correct = data.get(country);

        boolean isCorrect = answer.equalsIgnoreCase(correct);

        if (isCorrect) score++;

        index++;
        return isCorrect;
    }

    public String getCorrectAnswer() {
        String country = countries.get(index - 1);
        return data.get(country);
    }

    public int getScore() {
        return score;
    }

    public int getTotal() {
        return countries.size();
    }
}