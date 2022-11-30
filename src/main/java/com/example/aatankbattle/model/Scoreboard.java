package com.example.aatankbattle.model;

import java.util.ArrayList;

public class Scoreboard {
    private Avatar root;
    private ArrayList<Avatar> avatars = new ArrayList<>();
    private static Scoreboard instance;
    public Scoreboard(){

    }
    public static Scoreboard getInstance(){
        if(instance==null){
            instance= new Scoreboard();
        }

        return instance;
    }

    public void insert(Avatar current) {
        if (root == null) {
            root = current;
        } else {
            insert(current, root);
        }
    }

    private void insert(Avatar avatar, Avatar current) {
        if (avatar.getWins() == 0) return;
        if (avatar.getWins() == current.getWins()) {

        }
        if (avatar.getWins() < current.getWins()) {
            //izquierda
            if (current.getLeft() != null) {
                insert(avatar, current.getLeft());
            } else {
                current.setLeft(avatar);
            }
        } else if (avatar.getWins() > current.getWins()) {
            //derecha
            if (current.getRight() != null) {
                insert(avatar, current.getRight());
            } else {
                current.setRight(avatar);
            }
        }
    }
    public ArrayList<Avatar> printScore(Avatar current, int n) {
        if (current == null) {
            return avatars;
        }
        printScore(current.getRight(), n);
        avatars.add(new Avatar(current.getName(), current.getWins()));
        n++;

        return printScore(current.getLeft(), n);
    }
    public ArrayList<Avatar> updateLeaderboard(){
        avatars.clear();
        return printScore(root, 1);
    }
}
