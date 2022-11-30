package com.example.aatankbattle.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scoreboard {

    private ArrayList<Avatar> avatars = new ArrayList<>();
    private static Scoreboard instance;
    public Scoreboard(){
        loadData();
    }
    public static Scoreboard getInstance(){
        if(instance==null){
            instance = new Scoreboard();

        }

        return instance;
    }
    public void loadData(){
        String temp="";
        try {
            File file = new File("playerScore.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            while ((line = reader.readLine()) != null) {
                temp += line;
                String[] parts=line.split(",");
                int a=Integer.parseInt(parts[1]);
                avatars.add(new Avatar(parts[0],a));
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void insert(Avatar p){

        if(search(p)==null){
            avatars.add(p);
        }else wonAGame(search(p));;
        avatars.sort(new Comparator<Avatar>() {
            @Override
            public int compare(Avatar o1, Avatar o2) {
                return o1.getWins() - o2.getWins();
            }
        });
        System.out.println(avatars);
        Collections.reverse(avatars);

    }
    public Avatar search(Avatar p){
        for(Avatar x : avatars){
            if(p.getName().equals(x.getName()))
                return x;
        }
        return null;
    }

    public void wonAGame(Avatar p){
        p.setWins(p.getWins()+1);
    }
    /*public ArrayList<Avatar> printScore(Avatar current, int n) {
        if (current == null) {
            return avatars;
        }
        printScore(current.getRight(), n);
        avatars.add(new Avatar(current.getName(), current.getWins()));
        n++;
        System.out.println(avatars.size());
        return printScore(current.getLeft(), n);
    }
    public ArrayList<Avatar> updateLeaderboard(){
        avatars.clear();
        return printScore(root, 1);
    }*/

    public ArrayList<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(ArrayList<Avatar> avatars) {
        this.avatars = avatars;
    }
}
