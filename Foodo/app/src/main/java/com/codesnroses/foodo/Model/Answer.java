package com.codesnroses.foodo.Model;

/**
 * Created by Zhan on 15-02-22.
 */
public class Answer {

    private int id;
    private String choice;
    private String image;
    private Boolean isAnswer;

    public Answer(){}

    public Answer(int id, String choice, String image, Boolean isAnswer){
        this.id = id;
        this.choice = choice;
        this.image = image;
        this.isAnswer = isAnswer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsAnswer() {
        return isAnswer;
    }

    public void setIsAnswer(Boolean isAnswer) {
        this.isAnswer = isAnswer;
    }
}
