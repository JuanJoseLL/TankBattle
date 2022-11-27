package com.example.aatankbattle.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet {

    private Canvas canvas;
    private GraphicsContext gc;
    public Vector pos;
    public Vector direction;
    private int player;
    public Bullet(Canvas canvas, Vector pos, Vector dir,int player) {
        this.canvas = canvas;
        gc= canvas.getGraphicsContext2D();
        this.player=player;
        this.pos = pos;
        this.direction = dir;
    }
    public void draw(){
        gc.setFill(Color.YELLOW);
        gc.fillOval(pos.x-5,pos.y-5,10,10);

        pos.x+=direction.x;
        pos.y+=direction.y;

    }

    public int getPlayer() {
        return player;
    }
}
