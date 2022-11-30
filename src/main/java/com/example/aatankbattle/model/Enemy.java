package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Enemy {
    private Canvas canvas;
    private GraphicsContext gc;
    public int x,y;
    private Image tank;
    private Image heart;
    private Image bullet;
    private String name;
    public int bullets;
    public Vector pos;
    public Vector direction;
    public int life;
    public Enemy(Canvas canvas, int x, int y){
        name=" ";
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("tank22.png").getPath();
        tank=new Image(uri);
        pos=new Vector(x,y);
        direction=new Vector(2,0);
        String uri2 = "file:"+ GameMain.class.getResource("heartPlus.png").getPath();
        heart = new Image(uri2);
        String uri3="file:"+GameMain.class.getResource("bullet.png").getPath();
        bullet=new Image(uri3);
    }
    public Enemy(){
        x=300;
        y=300;
    }

    public void draw(){
        gc.save();
        gc.setFont(Font.font(10));
        gc.setFill(Color.WHITE);
        gc.fillText(name,-20, -35);
        //Hearts
        if(life>=1){
            gc.drawImage(heart,-25,-30,10,10);
        }
        if(life>=2){
            gc.drawImage(heart,-15,-30,10,10);
        }
        if(life>=3){
            gc.drawImage(heart,-5,-30,10,10);
        }
        if(life>=4){
            gc.drawImage(heart,5,-30,10,10);
        }
        if (life >= 5) {
            gc.drawImage(heart, 15, -30, 10, 10);
        }
        //bullets
        if(bullets>=1){
            gc.drawImage(bullet,-25,20,10,10);
        }
        if(bullets>=2){
            gc.drawImage(bullet,-15,20,10,10);
        }
        if(bullets>=3){
            gc.drawImage(bullet,-5,20,10,10);
        }
        if(bullets>=4){
            gc.drawImage(bullet,5,20,10,10);
        }
        if (bullets >= 5) {
            gc.drawImage(bullet, 15, 20, 10, 10);
        }
        //gc.rotate(90+ direction.getAngle());
        gc.drawImage(tank, pos.x-12.5, pos.y-12.5,50,50);
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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
