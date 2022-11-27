package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class Avatar {
    private Canvas canvas;
    private GraphicsContext gc;
    private Image tank;
    private Image heart;
    public Vector pos;
    public Vector direction;
    public int bullets;
    public int life;
    private String name;
    public Avatar(Canvas canvas){
        bullets=6;
        life=5;
        name="JuanJo";
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("tank.png").getPath();
        tank = new Image(uri);
        pos = new Vector(100,100);
        direction = new Vector(2,0);
        String uri2 = "file:"+ GameMain.class.getResource("heartPlus.png").getPath();
        heart = new Image(uri2);
    }
    public Avatar(Canvas canvas,int o){
        bullets=6;
        life=5;
        name="Sara";
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("tank23.png").getPath();
        tank = new Image(uri);
        pos = new Vector(100,200);
        direction = new Vector(2,0);
        String uri2 = "file:"+ GameMain.class.getResource("heartPlus.png").getPath();
        heart = new Image(uri2);

    }


    public void draw(){
        gc.save();
        gc.translate(pos.x,pos.y);
        gc.setFont(Font.font(10));
        gc.setFill(Color.WHEAT);
        gc.fillText(name,-20, -35);
        if(life>=1){
            gc.drawImage(heart,-25,-30,10,10);
        }
        if(life>=2){
            gc.drawImage(heart,-20,-30,10,10);
        }
        if(life>=3){
            gc.drawImage(heart,-15,-30,10,10);
        }
        if(life>=4){
            gc.drawImage(heart,-10,-30,10,10);
        }
        if (life >= 5) {
            gc.drawImage(heart, -5, -30, 10, 10);
        }
        gc.rotate(90+direction.getAngle());
        gc.drawImage(tank,-25,-25,50,50);
        gc.restore();

    }


    public void changeAngle(double a ){

        double amp = direction.getApmlitude();
        double angle = direction.getAngle();
        angle += a;
        direction.x = amp*Math.cos(Math.toRadians(angle));
        direction.y = amp*Math.sin(Math.toRadians(angle));

    }
    public void moveForward(){
        pos.x += direction.x;
        pos.y += direction.y;
    }

    public void moveBackward(){
        pos.x -= direction.x;
        pos.y -= direction.y;
    }

    public String getName() {
        return name;
    }
}
