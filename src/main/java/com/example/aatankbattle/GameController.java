package com.example.aatankbattle;

import com.example.aatankbattle.model.Avatar;
import com.example.aatankbattle.model.Bullet;
import com.example.aatankbattle.model.Enemy;
import com.example.aatankbattle.model.Vector;


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
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
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
        enemies=new ArrayList<>();
        enemies.add(new Enemy(canvas,300,100));
        enemies.add(new Enemy(canvas,300,300));

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

        draw();
    }
    public void drawBackground(){
        gc.save();
        gc.drawImage(bg, 0,0, 780,585);
        gc.restore();

    }
    public void draw() {
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {
                            gc.setFill(Color.BLACK);
                            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                            if(avatar.bullets==0){
                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                gc.fillText("Your`re out of bullets \n Press R to recharge ",canvas.getWidth()/3, canvas.getHeight()/2);
                            }
                            drawBackground();
                            avatar.draw();
                            avatar2.draw();

                            //System.out.println(avatar.pos.x);
                            //System.out.println(avatar.pos.y);
                            //Pintar enemigos
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
    public void sequence(Enemy a){
        exploding=true;
        new Thread(
                () -> {
                    while(exploding){
                        Platform.runLater(()->{
                            if(cont<50){
                                gc.drawImage(explode[0],a.x-75,a.y-75,200,200);
                            }else if (cont > 50 && cont < 100){
                                gc.drawImage(explode[1],a.x-80,a.y-80,200,200);
                            }else if(cont > 100){
                                gc.drawImage(explode[2],a.x-75,a.y-75,200,200);
                            }
                            if(cont > 150){
                                exploding=false;
                            }
                        });
                        try{
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        cont++;
                    }

                }

        ).start();
        cont=0;
    }
    private void detectColission() {

        for(int i=0;i<enemies.size();i++){
            for(int j=0;j<bullets.size();j++){
                Bullet b=bullets.get(j);
                Enemy e=enemies.get(i);
                double cateto1 = b.pos.x-e.x;
                double cateto2 = b.pos.y-e.y;
                double distance = Math.sqrt(Math.pow(cateto1,2) + Math.pow(cateto2,2));
                if(distance < 15){
                    bullets.remove(j);
                    sequence(enemies.get(i));
                    enemies.remove(i);
                    return;
                }
            }
        }
    }

    private void doKeyboardActions() {
        if (Wpressed) {
            avatar.moveForward();
        }
        if (Apressed) {
            avatar.changeAngle(-3);
        }
        if (Spressed) {
            avatar.moveBackward();
        }
        if (Dpressed) {
            avatar.changeAngle(3);
        }
        if(upPressed){
            avatar2.moveForward();
        }
        if(leftPressed){
            avatar2.changeAngle(-3);
        }
        if(downPressed){
            avatar2.moveBackward();
        }
        if(rightPressed){
            avatar2.changeAngle(3);
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
                        new Vector(2*avatar.direction.x,2*avatar.direction.y));
                bullets.add(bullet);
                avatar.bullets--;
            }
        }
    }
}