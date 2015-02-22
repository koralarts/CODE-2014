package com.codesnroses.foodo.Model;

/**
 * Created by Zhan on 15-02-22.
 */
public class Achievement {
    private String title;
    private String description;
    private Boolean isAchieved;

    public Achievement(){}

    public Achievement(String title, String description, Boolean isAchieved){
        this.title = title;
        this.description = description;
        this.isAchieved = isAchieved;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsAchieved() {
        return isAchieved;
    }

    public void setIsAchieved(Boolean isAchieved) {
        this.isAchieved = isAchieved;
    }
}
