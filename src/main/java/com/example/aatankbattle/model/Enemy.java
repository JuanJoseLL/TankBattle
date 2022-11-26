package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Enemy {
    private Canvas canvas;
    private GraphicsContext gc;
    public int x,y;
    private Image tank;

    public Enemy(Canvas canvas, int x, int y){
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("prueba2.png").getPath();
        tank=new Image(uri);
        this.x = x;
        this.y = y;
    }

    public void draw(){
        gc.save();
        gc.drawImage(tank,x-12.5,y-12.5,50,50);
    }

}
