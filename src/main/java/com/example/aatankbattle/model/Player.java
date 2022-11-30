package com.example.aatankbattle.model;

import javafx.scene.Node;

public class Player {
    private String name;
    private int victories;

    public Player(int type) {

    }
    public Player(String name, int victories){
        this.name=name;
        this.victories=victories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVictories() {
        return victories;
    }

    public void setVictories(int victories) {
        this.victories = victories;
    }
}
