package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Wall {
    private Canvas canvas;
    private GraphicsContext gc;
    public int x,y;
    private Image wall;

    public Wall(Canvas canvas,int x,int y){
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri="file:"+ GameMain.class.getResource("muro.png").getPath();
        wall=new Image(uri);
        this.x=x;
        this.y=y;
    }
    public void draw(){
        gc.save();
        gc.drawImage(wall,x,y,70,50);
    }
}
