package com.example.aatankbattle;

import com.example.aatankbattle.model.*;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean isRunning=true;
    private Image[] explode=new Image[3];
    private Image bg;
    private Image wall;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Wall> walls;
    private Avatar avatar;
    private Avatar avatar2;
    private boolean exploding=true;
    private int cont=0;
    //Estados de las teclas
    boolean Wpressed = false;
    boolean Apressed = false;
    boolean Spressed = false;
    boolean Dpressed = false;

    boolean upPressed = false;
    boolean downPressed = false;
    boolean rightPressed = false;
    boolean leftPressed = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        //Se generan enemigos en el canvas
        enemies = new ArrayList<>();
        enemies.add(new Enemy(canvas,300,100));
        enemies.add(new Enemy(canvas,300,300));
        walls = new ArrayList<>();
        walls.add(new Wall(canvas,200,50));
        walls.add(new Wall(canvas,200,90));
        walls.add(new Wall(canvas,200,120));
        walls.add(new Wall(canvas,200,160));
        walls.add(new Wall(canvas,200,200));
        walls.add(new Wall(canvas,200,240));

        bullets=new ArrayList<>();

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);


        avatar = new Avatar(canvas);
        avatar2 = new Avatar(canvas,0);
        String uri0 = "file:"+GameMain.class.getResource("explode0.png").getPath();
        String uri1 = "file:"+GameMain.class.getResource("explode1.png").getPath();
        String uri2 = "file:"+GameMain.class.getResource("explode2.png").getPath();
        explode[0]=new Image(uri0);
        explode[1]=new Image(uri1);
        explode[2]=new Image(uri2);

        String uri4 = "file:"+ GameMain.class.getResource("davidCalvo.png").getPath();
        bg = new Image(uri4);
        String uri5 = "file:"+GameMain.class.getResource("muro.png").getPath();
        wall=new Image(uri5);
        draw();
    }
    public void drawBackground(){
        gc.save();
        gc.drawImage(bg, 0,0, 780,585);
        gc.restore();
    }
    public void drawWall(){
        gc.save();
        gc.drawImage(wall,200,200,75,50);
        gc.restore();
    }
    public void draw() {
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {

                            gc.setFill(Color.BLACK);
                            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            drawBackground();
                            //drawWall();
                            if(enemies.size() == 0 && avatar==null ){

                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                gc.fillText(avatar2.getName()+" \n won the game ",canvas.getWidth()/2, canvas.getHeight()/2);

                            }
                            if( enemies.size() == 0 && avatar2 == null){

                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                gc.fillText(avatar.getName()+" \n won the game ",canvas.getWidth()/2, canvas.getHeight()/2);

                            }
                            if(avatar!=null){
                                avatar.draw();

                                if(avatar.bullets==0 ){
                                    gc.setFont(Font.font(35));
                                    gc.setFill(Color.YELLOW);
                                    gc.fillText("Your`re out of bullets \n Press R to recharge ",canvas.getWidth()/3, canvas.getHeight()/2);
                                }
                            }
                            if(avatar2!=null){
                                avatar2.draw();
                                if(avatar2.bullets==0 ){
                                    gc.setFont(Font.font(35));
                                    gc.setFill(Color.YELLOW);
                                    gc.fillText("Your`re out of bullets \n Press R to recharge ",canvas.getWidth()/3, canvas.getHeight()/2);
                                }
                            }



                            //Pintar enemigos
                            for(int i = 0; i < walls.size(); i++){
                                walls.get(i).draw();
                            }
                            for (int i = 0; i < enemies.size(); i++) {
                                enemies.get(i).draw();
                            }
                            for (int i = 0; i < bullets.size(); i++) {
                                bullets.get(i).draw();
                                if (bullets.get(i).pos.x > canvas.getWidth() + 20 ||
                                        bullets.get(i).pos.y > canvas.getHeight() + 2 ||
                                        bullets.get(i).pos.y < -20 ||
                                        bullets.get(i).pos.x < -20) {

                                    bullets.remove(i);

                                }

                            }
                            //Colisiones
                            detectColission();
                            detectWall();
                            detectColissionAvatar();
                            doKeyboardActions();
                        });
                        //Sleep
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        ).start();
    }


    public void sequence(double x,double y){
        exploding=true;
        new Thread(
                () -> {
                    while(exploding){
                        Platform.runLater(()->{
                            if(cont<50){
                                gc.drawImage(explode[0],x-75,y-75,200,200);
                            }else if (cont > 50 && cont < 100){
                                gc.drawImage(explode[1],x-80,y-80,200,200);
                            }else if(cont > 100){
                                gc.drawImage(explode[2],x-75,y-75,200,200);
                            }
                            if(cont > 150){
                                exploding=false;
                            }
                        });
                        try{
                            Thread.sleep(5);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        cont++;
                    }

                }

        ).start();
        cont=0;
    }
    private void detectColissionAvatar() {
        for (int i=0;i<bullets.size();i++){
            Bullet b=bullets.get(i);
            double distanceAv2 = 0;
            double distanceAv1 = 0;
            if(avatar!=null){
                double cateto3 = b.pos.x - avatar.pos.x;
                double cateto4 = b.pos.y - avatar.pos.y;
                distanceAv1 = Math.sqrt(Math.pow(cateto3, 2) + Math.pow(cateto4, 2));
            }

            if (avatar2 != null) {
                double cateto5 = b.pos.x - avatar2.pos.x;
                double cateto6 = b.pos.y - avatar2.pos.y;
                distanceAv2 = Math.sqrt(Math.pow(cateto5, 2) + Math.pow(cateto6, 2));
            }

            if (distanceAv1 < 25 && b.getPlayer() != 1) {
                if(avatar!=null){
                    sequence(avatar.pos.x-15, avatar.pos.y-15);
                    System.out.println("hit al tanque 1");
                    bullets.remove(i);
                    avatar.life--;
                    System.out.println(avatar.life);
                    if (avatar.life == 0) {
                        avatar = null;
                    }
                    return;
                }

            }
            if (distanceAv2 < 25 && b.getPlayer() != 2) {
                if(avatar2!=null){
                    sequence(avatar2.pos.x-15, avatar2.pos.y-15);
                    System.out.println("hit al tanque 2");
                    bullets.remove(i);
                    avatar2.life--;
                    System.out.println(avatar2.life);
                    if (avatar2.life == 0) {
                        avatar2 = null;
                    }
                    return;
                }

            }
        }

    }
    private void detectWall(){
        for(int i = 0; i < walls.size();i++){
            Wall e = walls.get(i);

            double distanceAv1=0;
            double distanceAv2=0;

            if(avatar!=null){
                double cateto3 = avatar.pos.x - e.x;
                double cateto4 = avatar.pos.y - e.y;
                distanceAv1 = Math.sqrt(Math.pow(cateto3, 2) + Math.pow(cateto4, 2));
            }

            if (avatar2 != null) {
                double cateto5 = avatar2.pos.x - e.x;
                double cateto6 = avatar2.pos.y - e.y;
                distanceAv2 = Math.sqrt(Math.pow(cateto5, 2) + Math.pow(cateto6, 2));
            }

            if(distanceAv1 < 20){
                avatar.pos.x-=15;

            }
            if(distanceAv1 > 20){
                avatar.pos.x+=15;
            }
            if(distanceAv2 < 15){


            }
        }
    }

    private void detectColission() {

        for(int i=0;i<enemies.size();i++){
            for(int j=0;j<bullets.size();j++) {
                Bullet b = bullets.get(j);
                Enemy e = enemies.get(i);
                double cateto1 = b.pos.x - e.x;
                double cateto2 = b.pos.y - e.y;
                double distance = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                if (distance < 25) {
                    bullets.remove(j);
                    sequence(enemies.get(i).x, enemies.get(i).y);
                    enemies.remove(i);
                    return;
                }
            }
        }
    }

    private void doKeyboardActions() {
        if(avatar!=null){
            if (Wpressed) {
                if(avatar.pos.x > canvas.getWidth()-15 ){
                    avatar.pos.x = avatar.pos.x-10;
                }else if(avatar.pos.y>canvas.getHeight()-15 ){
                    avatar.pos.y = avatar.pos.y-10;
                }else if(avatar.pos.x < 10 ){
                    avatar.pos.x = avatar.pos.x+10;
                }else if(avatar.pos.y < 10){
                    avatar.pos.y = avatar.pos.y+10;
                }else{
                    avatar.moveForward();
                }

            }
            if (Apressed) {
                avatar.changeAngle(-3);
            }
            if (Spressed) {
                if(avatar.pos.x > canvas.getWidth()-15 ){
                    avatar.pos.x = avatar.pos.x-10;
                }else if(avatar.pos.y>canvas.getHeight()-15 ){
                    avatar.pos.y = avatar.pos.y-10;
                }else if(avatar.pos.x < 10 ){
                    avatar.pos.x = avatar.pos.x+10;
                }else if(avatar.pos.y < 10){
                    avatar.pos.y = avatar.pos.y+10;
                }else{
                    avatar.moveBackward();
                }
            }
            if (Dpressed) {
                avatar.changeAngle(3);
            }
        }
       if(avatar2!=null){
           if(upPressed){
               if(avatar2.pos.x > canvas.getWidth()-15 ){
                   avatar2.pos.x = avatar2.pos.x-10;
               }else if(avatar2.pos.y>canvas.getHeight()-15 ){
                   avatar2.pos.y = avatar2.pos.y-10;
               }else if(avatar2.pos.x < 10 ){
                   avatar2.pos.x = avatar2.pos.x+10;
               }else if(avatar2.pos.y < 10){
                   avatar2.pos.y = avatar2.pos.y+10;
               }else{
                   avatar2.moveForward();
               }
           }
           if(leftPressed){
               avatar2.changeAngle(-3);
           }
           if(downPressed){
               if(avatar2.pos.x > canvas.getWidth()-15 ){
                   avatar2.pos.x = avatar2.pos.x-10;
               }else if(avatar2.pos.y>canvas.getHeight()-15 ){
                   avatar2.pos.y = avatar2.pos.y-10;
               }else if(avatar2.pos.x < 10 ){
                   avatar2.pos.x = avatar2.pos.x+10;
               }else if(avatar2.pos.y < 10){
                   avatar2.pos.y = avatar2.pos.y+10;
               }else{
                   avatar2.moveBackward();
               }
           }
           if(rightPressed){
               avatar2.changeAngle(3);
           }
       }

    }

    private void onKeyReleased(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.W){
            Wpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.A){
            Apressed = false;
        }
        if(keyEvent.getCode() == KeyCode.S){
            Spressed = false;
        }
        if(keyEvent.getCode() == KeyCode.D){
            Dpressed = false;
        }
        if(keyEvent.getCode() == KeyCode.UP){
            upPressed = false;
        }
        if(keyEvent.getCode() == KeyCode.LEFT){
            leftPressed = false;
        }
        if(keyEvent.getCode() == KeyCode.DOWN){
            downPressed = false;
        }
        if(keyEvent.getCode() == KeyCode.RIGHT){
            rightPressed = false;
        }
    }
    private void onKeyPressed(KeyEvent keyEvent) {

        if (keyEvent.getCode() == KeyCode.W) {
            Wpressed = true;
        }
        if (keyEvent.getCode() == KeyCode.A) {
            Apressed = true;
        }
        if (keyEvent.getCode() == KeyCode.S) {
            Spressed = true;
        }
        if (keyEvent.getCode() == KeyCode.D) {
            Dpressed = true;
        }
        if (keyEvent.getCode() == KeyCode.UP) {
            upPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.LEFT) {
            leftPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.DOWN) {
            downPressed = true;
        }
        if (keyEvent.getCode() == KeyCode.RIGHT) {
            rightPressed = true;
        }
        if(keyEvent.getCode() == KeyCode.R){
            avatar.bullets=6;
        }
        if (keyEvent.getCode()==KeyCode.SPACE){
            if(avatar.bullets==0){

            }else{
                Bullet bullet = new Bullet(canvas,
                        new Vector(avatar.pos.x , avatar.pos.y),
                        new Vector(2*avatar.direction.x,2*avatar.direction.y),1);
                bullets.add(bullet);
                avatar.bullets--;
            }
        }
        if(keyEvent.getCode()==KeyCode.SHIFT){
            if(avatar2.bullets!=0){
                Bullet bullet = new Bullet(canvas,
                        new Vector(avatar2.pos.x , avatar2.pos.y),
                        new Vector(2*avatar2.direction.x,2*avatar2.direction.y),2);
                bullets.add(bullet);
                avatar2.bullets--;
            }
        }
        if(keyEvent.getCode() == KeyCode.ENTER){
            avatar2.bullets=6;
        }
    }
}