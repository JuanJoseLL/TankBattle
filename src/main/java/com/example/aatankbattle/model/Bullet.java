package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet {

    private Canvas canvas;
    private GraphicsContext gc;
    public Vector pos;
    public Vector direction;
    private int player;
    private Image bullet;
    public Bullet(Canvas canvas, Vector pos, Vector dir,int player) {
        this.canvas = canvas;
        gc= canvas.getGraphicsContext2D();
        this.player=player;
        this.pos = pos;
        this.direction = dir;
        String uri = "file:"+ GameMain.class.getResource("mine.png").getPath();
        bullet=new Image(uri);
    }
    public void draw(){

        gc.drawImage(bullet,pos.x-5,pos.y-5,10,10);
        pos.x+=direction.x;
        pos.y+=direction.y;

    }

    public int getPlayer() {
        return player;
    }
}
