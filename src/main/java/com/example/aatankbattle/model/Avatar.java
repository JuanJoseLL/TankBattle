package com.example.aatankbattle.model;

import com.example.aatankbattle.GameMain;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



public class Avatar {
    private Canvas canvas;
    private GraphicsContext gc;
    private Avatar left;
    private Avatar right;
    private Image tank;
    private Image heart;
    private Image bullet;
    public Vector pos;
    public Vector direction;
    public int bullets;
    public int life;
    private String name;
    private int wins;

    public Avatar(Canvas canvas){
        name=" ";
        bullets=5;
        life=5;
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("tank.png").getPath();
        tank = new Image(uri);
        pos = new Vector(100,100);
        direction = new Vector(2,0);
        String uri2 = "file:"+ GameMain.class.getResource("heartPlus.png").getPath();
        heart = new Image(uri2);
        String uri3="file:"+GameMain.class.getResource("bullet.png").getPath();
        bullet=new Image(uri3);
    }
    public Avatar(int type){

        if(type == 1){
            pos = new Vector(250, 250);
            direction = new Vector(1,0);
        } else if (type == 2){
            pos = new Vector(750, 250);
            direction = new Vector(-1,0);
        } else {
            pos = new Vector(500, 500);
            direction = new Vector(0,1);
        }

        bullets = 5;
        life = 5;
    }
    public Avatar(Canvas canvas,int o){
        name= Player.getInstance().player1.getName();
        bullets=6;
        life=5;
        this.canvas=canvas;
        gc=canvas.getGraphicsContext2D();
        String uri = "file:"+ GameMain.class.getResource("tank23.png").getPath();
        tank = new Image(uri);
        pos = new Vector(100,200);
        direction = new Vector(2,0);
        String uri2 = "file:"+ GameMain.class.getResource("heartPlus.png").getPath();
        heart = new Image(uri2);
        String uri3="file:"+GameMain.class.getResource("bullet.png").getPath();
        bullet=new Image(uri3);
    }
    public Avatar(String name,int wins){
        this.name=name;
        this.wins=wins;
    }


    public void draw(){
        gc.save();
        gc.translate(pos.x,pos.y);
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

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Avatar getLeft() {
        return left;
    }

    public void setLeft(Avatar left) {
        this.left = left;
    }

    public Avatar getRight() {
        return right;
    }

    public void setRight(Avatar right) {
        this.right = right;
    }
}
