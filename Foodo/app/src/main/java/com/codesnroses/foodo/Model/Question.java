package com.codesnroses.foodo.Model;

import java.util.ArrayList;

/**
 * Created by Zhan on 15-02-22.
 */
public class Question {
    private int id;
    private String name;
    private String question;
    private int gameType;
    private int level;

    private ArrayList<Answer> choices;

    public Question(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Answer> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<Answer> choices) {
        this.choices = choices;
    }

    public Question(int id, String name, String question, int gameType, int level, ArrayList<Answer> choices){
        this.id = id;
        this.name = name;
        this.question = question;
        this.gameType = gameType;
        this.level = level;
        this.choices = choices;

    }

}
