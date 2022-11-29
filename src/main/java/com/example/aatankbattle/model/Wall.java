package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Wall {
    private Canvas canvas;
    private GraphicsContext gc;
    public int life;
    public int x,y;
    private Image wall;
    private Rectangle hitbox;
    public Wall(Canvas canvas,int x,int y){
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri="file:"+ GameMain.class.getResource("muro.png").getPath();
        wall=new Image(uri);
        this.x=x;
        this.y=y;
        life=3;
    }
    public void draw(){
        hitbox=new Rectangle(x,y,60,40);
        gc.drawImage(wall,x,y,70,50);

    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }


}
