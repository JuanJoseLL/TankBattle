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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Random;

public class GameController implements Initializable {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private boolean isRunning=true;
    private Image[] explode=new Image[3];
    private Image bg;
    private Image wall;
    private ArrayList<Avatar> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Wall> walls;
    private Avatar avatar;
    private Avatar avatar2;
    private Avatar enemy;
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
    private Scoreboard scoreboard;
    private ScoreBoardController scoreBoardController;
    public int hitsP1=0;
    public int hitsP2=0;

    File path1= new File("C://Users//Sara Cardona//Downloads//tank_shot-_online-audio-converter.com_.wav/");
    File path2= new File("C:/Users/Sara Cardona/Downloads/mixkit-empty-tube-hit-3197.wav");
    File path3= new File("C:/Users/Sara Cardona/Downloads/lock-load-reload-3.wav");
    File path4=new File("C:/Users/Sara Cardona/Downloads/mixkit-orchestra-trumpets-ending-2292.wav");
    File path5= new File("C:/Users/Sara Cardona/Downloads/mixkit-medieval-show-fanfare-announcement-226.wav");
    FirstScreenController fs;
    Avatar av1Temp;
    Avatar av2Temp;
    Avatar av3Temp;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scoreboard=new Scoreboard();
        scoreBoardController=new ScoreBoardController();
        fs=new FirstScreenController();
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        //Se generan enemigos en el canvas
        enemies = new ArrayList<>();
        enemies.add(new Avatar(canvas,0,0));
        walls = new ArrayList<>();


        generateWalls();

        bullets=new ArrayList<>();

        canvas.setOnKeyPressed(this::onKeyPressed);
        canvas.setOnKeyReleased(this::onKeyReleased);


        avatar = new Avatar(canvas);
        avatar2 = new Avatar(canvas,0);
        enemy=new Avatar(canvas,0,0);

        av1Temp=avatar;
        av2Temp=avatar2;
        av3Temp=enemy;
        avatar.setName(Player.getInstance().player1.getName());
        avatar2.setName(Player.getInstance().player2.getName());
        enemy.setName(Player.getInstance().player3.getName());

        String uri0 = "file:"+GameMain.class.getResource("explode0.png").getPath();
        String uri1 = "file:"+GameMain.class.getResource("explode1.png").getPath();
        String uri2 = "file:"+GameMain.class.getResource("explode2.png").getPath();
        explode[0]=new Image(uri0);
        explode[1]=new Image(uri1);
        explode[2]=new Image(uri2);

        String fondo="";
        int random = (int) (Math.floor(Math.random()*(4-1)+1));
        if(random==1){
            String uri4 = "file:"+ GameMain.class.getResource("fondo2.jpg").getPath();
            bg = new Image(uri4);
        } else if (random==2) {
            String uri4 = "file:"+ GameMain.class.getResource("fondo3.jpg").getPath();
            bg = new Image(uri4);
        } else if (random==3) {
            String uri4 = "file:"+ GameMain.class.getResource("fondo4.jpg").getPath();
            bg = new Image(uri4);
        }else{
            String uri4 = "file:"+ GameMain.class.getResource("fondo1.jpg").getPath();
            bg = new Image(uri4);
        }
        String uri5 = "file:"+GameMain.class.getResource("muro.png").getPath();
        wall=new Image(uri5);
        if (path4.exists()) {
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path4);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("No existe");
        }
        draw();
        drawBot();

        //attack();

    }

    public void generateWalls(){

        //Muro vertical izquierdo

        walls.add(new Wall(canvas,150,50));
        walls.add(new Wall(canvas,150,88));
        walls.add(new Wall(canvas,150,125));
        walls.add(new Wall(canvas,150,162));
        walls.add(new Wall(canvas,150,200));
        walls.add(new Wall(canvas,150,238));

        //Muro horizontal derecho

        walls.add(new Wall(canvas,400,300));
        walls.add(new Wall(canvas,425,300));
        walls.add(new Wall(canvas,452,300));
        walls.add(new Wall(canvas,480,300));
        walls.add(new Wall(canvas,508,300));
        walls.add(new Wall(canvas,536,300));

        //Muro vertical derecho

        walls.add(new Wall(canvas,500,50));
        walls.add(new Wall(canvas,500,85));
        walls.add(new Wall(canvas,500,120));
        walls.add(new Wall(canvas,500,155));
        walls.add(new Wall(canvas,500,190));
        walls.add(new Wall(canvas,500,227));

        //Muro diagonal izquierdo

        walls.add(new Wall(canvas,100,400));
        walls.add(new Wall(canvas,130,360));
        walls.add(new Wall(canvas,160,320));
        walls.add(new Wall(canvas,190,280));
        walls.add(new Wall(canvas,220,240));
        walls.add(new Wall(canvas,250,200));

        //Muro vertical derecho abajo
        walls.add(new Wall(canvas,300,400+100));
        walls.add(new Wall(canvas,300,365+100));
        walls.add(new Wall(canvas,300,327+100));
        walls.add(new Wall(canvas,300,290+100));
        walls.add(new Wall(canvas,300,253+100));
        walls.add(new Wall(canvas,300,215+100));

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
                            drawBackground();
                            if(enemies.size() == 0 && avatar==null ){
                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                avatar2.setWins(avatar2.getWins()+1);
                                scoreboard.insert(avatar2);
                                gc.fillText(avatar2.getName()+" \n won the game ",canvas.getWidth()/2, canvas.getHeight()/2);
                                isRunning=false;
                                if (path5.exists()) {
                                    try {
                                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path5);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioInputStream);
                                        clip.start();
                                    } catch (UnsupportedAudioFileException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    } catch (LineUnavailableException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    System.out.println("No existe");
                                }
                                GameMain.showWindow("winCanva.fxml");
                                Stage current = (Stage) canvas.getScene().getWindow();
                                current.hide();
                            }
                            if( enemies.size() == 0 && avatar2 == null){

                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                avatar.setWins(avatar.getWins()+1);
                                scoreboard.insert(avatar);
                                gc.fillText(avatar.getName()+" \n won the game ",canvas.getWidth()/2, canvas.getHeight()/2);
                                isRunning=false;
                                if (path5.exists()) {
                                    try {
                                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path5);
                                        Clip clip = AudioSystem.getClip();
                                        clip.open(audioInputStream);
                                        clip.start();
                                    } catch (UnsupportedAudioFileException e) {
                                        throw new RuntimeException(e);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    } catch (LineUnavailableException e) {
                                        throw new RuntimeException(e);
                                    }
                                } else {
                                    System.out.println("No existe");
                                }
                                GameMain.showWindow("winCanva.fxml");
                                Stage current = (Stage) canvas.getScene().getWindow();
                                current.hide();
                            }
                            if( enemies.size() != 0 && avatar2 == null && avatar==null){

                                gc.setFont(Font.font(35));
                                gc.setFill(Color.YELLOW);
                                //enemy.setWins(enemy.getWins()+1);
                                scoreboard.insert(enemy);
                                gc.fillText(enemy.getName()+" \n  won the game ",canvas.getWidth()/2, canvas.getHeight()/2);
                                isRunning=false;
                                GameMain.showWindow("winCanva.fxml");
                                Stage current = (Stage) canvas.getScene().getWindow();
                                current.hide();
                            }
                            if(avatar!=null){
                                avatar.draw();

                                if(avatar.bullets==0 ){
                                    gc.setFont(Font.font(35));
                                    gc.setFill(Color.YELLOW);
                                    gc.fillText(avatar.getName()+" Out of bullets \n Press R to recharge ",canvas.getWidth()/3, canvas.getHeight()/2);
                                }
                            }
                            if(avatar2!=null){
                                avatar2.draw();
                                if(avatar2.bullets==0 ){
                                    gc.setFont(Font.font(35));
                                    gc.setFill(Color.YELLOW);
                                    gc.fillText(avatar2.getName()+" Out of bullets \n Press ENTER to recharge ",canvas.getWidth()/3, canvas.getHeight()/2);
                                }
                            }

                            //Pintar enemigos
                            for(int i = 0; i < walls.size(); i++){
                                walls.get(i).draw();
                            }
                            for (int i = 0; i < enemies.size(); i++) {
                                enemies.get(i).drawEnemy();
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

                            detectColissionAvatar();
                            doKeyboardActions();
                            keyboardActionEnemy();
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
    private int a=0;
    public void drawBot() {

        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).changeAngle(45);
        }

        final boolean[] flag = {true};
        new Thread(
                () -> {
                    while (isRunning) {
                        Platform.runLater(() -> {
                            for (int i = 0; i < enemies.size(); i++) {

                                Avatar bot = enemies.get(i);

                                bot.moveForward();

                                if (bot.pos.y >= 180) {
                                    bot.pos.y = 179;
                                    bot.changeAngle(90);
                                }
                                if (bot.pos.x <= 220) {
                                    bot.pos.x = 221;
                                    bot.changeAngle(90);
                                }
                                if (bot.pos.y <= 71) {
                                    bot.pos.y = 72;
                                    bot.changeAngle(90);
                                }
                                if (bot.pos.x >= 490) {
                                    bot.pos.x = 489;
                                    bot.changeAngle(90);
                                }
                                if(a==20){
                                    if(bot.bullets!=0){
                                        if(path1.exists()){
                                            try {
                                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                                Clip clip= AudioSystem.getClip();
                                                clip.open(audioInputStream);
                                                clip.start();
                                            } catch (UnsupportedAudioFileException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            } catch (LineUnavailableException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }else{
                                            System.out.println("No existe");
                                        }
                                        Bullet bullet = new Bullet(canvas,
                                                new Vector(bot.pos.x , bot.pos.y),
                                                new Vector(2*bot.direction.x,2*bot.direction.y),3);
                                        bullets.add(bullet);
                                        System.out.println("addawdaw");
                                        bot.bullets--;
                                    }

                                }
                                if(a==60){
                                    if(bot.bullets!=0){
                                        if(path1.exists()){
                                            try {
                                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                                Clip clip= AudioSystem.getClip();
                                                clip.open(audioInputStream);
                                                clip.start();
                                            } catch (UnsupportedAudioFileException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            } catch (LineUnavailableException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }else{
                                            System.out.println("No existe");
                                        }
                                        Bullet bullet = new Bullet(canvas,
                                                new Vector(bot.pos.x , bot.pos.y),
                                                new Vector(2*bot.direction.x,2*bot.direction.y),3);
                                        bullets.add(bullet);
                                        System.out.println("addawdaw");
                                        bot.bullets--;
                                    }

                                }
                                if(a==100){
                                    if(bot.bullets!=0){
                                        if(path1.exists()){
                                            try {
                                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                                Clip clip= AudioSystem.getClip();
                                                clip.open(audioInputStream);
                                                clip.start();
                                            } catch (UnsupportedAudioFileException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            } catch (LineUnavailableException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }else{
                                            System.out.println("No existe");
                                        }
                                        Bullet bullet = new Bullet(canvas,
                                                new Vector(bot.pos.x , bot.pos.y),
                                                new Vector(2*bot.direction.x,2*bot.direction.y),3);
                                        bullets.add(bullet);
                                        System.out.println("addawdaw");
                                        bot.bullets--;
                                    }

                                }
                                if(a==150){
                                    if(bot.bullets!=0){
                                        if(path1.exists()){
                                            try {
                                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                                Clip clip= AudioSystem.getClip();
                                                clip.open(audioInputStream);
                                                clip.start();
                                            } catch (UnsupportedAudioFileException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            } catch (LineUnavailableException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }else{
                                            System.out.println("No existe");
                                        }
                                        Bullet bullet = new Bullet(canvas,
                                                new Vector(bot.pos.x , bot.pos.y),
                                                new Vector(2*bot.direction.x,2*bot.direction.y),3);
                                        bullets.add(bullet);
                                        System.out.println("addawdaw");
                                        bot.bullets--;
                                    }

                                }
                                if(a==200){
                                    if(bot.bullets!=0){
                                        if(path1.exists()){
                                            try {
                                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                                Clip clip= AudioSystem.getClip();
                                                clip.open(audioInputStream);
                                                clip.start();
                                            } catch (UnsupportedAudioFileException e) {
                                                throw new RuntimeException(e);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            } catch (LineUnavailableException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }else{
                                            System.out.println("No existe");
                                        }
                                        Bullet bullet = new Bullet(canvas,
                                                new Vector(bot.pos.x , bot.pos.y),
                                                new Vector(2*bot.direction.x,2*bot.direction.y),3);
                                        bullets.add(bullet);
                                        System.out.println("addawdaw");
                                        bot.bullets--;
                                    }

                                }
                                if(a==300){
                                    if(path3.exists()){
                                        try {
                                            AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path3);
                                            Clip clip= AudioSystem.getClip();
                                            clip.open(audioInputStream);
                                            clip.start();
                                        } catch (UnsupportedAudioFileException e) {
                                            throw new RuntimeException(e);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        } catch (LineUnavailableException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }else{
                                        System.out.println("No existe");
                                    }
                                    bot.bullets=5;
                                }
                                if(a==310) a=0;


                            }

                        });
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        a++;
                    }

                }
        ).start();
    }
    public void keyboardActionEnemy(){
        if(enemy !=null){
            if(enemy.pos.x > canvas.getWidth()-15 ){
                enemy.pos.x = enemy.pos.x-10;
            }else if(enemy.pos.y>canvas.getHeight()-15 ){
                enemy.pos.y = enemy.pos.y-10;
            }else if(enemy.pos.x < 10 ){
                enemy.pos.x = enemy.pos.x+10;
            }else if(enemy.pos.y < 10){
                enemy.pos.y = enemy.pos.y+10;
            }else{

                enemy.moveForward();

            }
            boolean flag2 = false;
            for (int f = 0; f < walls.size(); f++) {
                if (walls.get(f).getHitbox().intersects(enemy.pos.x - enemy.direction.x - 15, enemy.pos.y - enemy.direction.y - 15, 10, 10)) {
                    flag2 = true;
                } else if (enemy.pos.x > canvas.getWidth() - 15) {
                    enemy.pos.x = enemy.pos.x - 10;
                } else if (enemy.pos.y > canvas.getHeight() - 15) {
                    enemy.pos.y = enemy.pos.y - 10;
                } else if (enemy.pos.x < 10) {
                    enemy.pos.x = enemy.pos.x + 10;
                } else if (enemy.pos.y < 10) {
                    enemy.pos.y = enemy.pos.y + 10;
                }
            }
            if(!flag2){
                enemy.moveBackward();
            }
        }

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
            double distanceAv3 = 0;
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
            if (enemy != null) {
                double cateto7 = b.pos.x - enemy.pos.x;
                double cateto8 = b.pos.y - enemy.pos.y;
                distanceAv3 = Math.sqrt(Math.pow(cateto7, 2) + Math.pow(cateto8, 2));
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
            if (distanceAv3 < 25) {
                if(enemy!=null){
                    sequence(enemy.pos.x-15, enemy.pos.y-15);
                    System.out.println("hit al enemy 2");
                    bullets.remove(i);
                    enemy.life--;
                    System.out.println(enemy.life);
                    if (enemy.life == 0) {
                        enemy = null;
                    }
                    return;
                }

            }
        }

    }
    private void detectColission() {

        for(int i=0;i<enemies.size();i++){
            for(int j=0;j<bullets.size();j++) {
                Bullet b = bullets.get(j);
                Avatar e = enemies.get(i);
                double cateto1 = b.pos.x - e.pos.x;
                double cateto2 = b.pos.y - e.pos.y;
                double distance = Math.sqrt(Math.pow(cateto1, 2) + Math.pow(cateto2, 2));
                if (distance < 25 && b.getPlayer()!=3) {
                    bullets.remove(j);
                    sequence(enemies.get(i).pos.x, enemies.get(i).pos.y);
                    enemies.get(i).life--;
                    if(enemies.get(i).life==0){
                        enemies.remove(i);
                    }
                    return;
                }
            }
        }
        for (int i=0;i<walls.size();i++){
            for (int j=0;j<bullets.size();j++){
                Bullet b=bullets.get(j);
                Rectangle w =walls.get(i).getHitbox();
                if(w.intersects(b.pos.x-15,b.pos.y-15,10,10)){
                    bullets.remove(j);
                    sequence(walls.get(i).x+20, walls.get(i).y+10);
                    walls.get(i).life--;
                    if(walls.get(i).life==0){
                        walls.remove(i);
                    }
                    return;
                }
            }
        }
    }

    private void doKeyboardActions() {
        if(avatar!=null){
            if (Wpressed) {
                boolean flag = false;
                for (int i = 0; i < walls.size(); i++) {
                    if (walls.get(i).getHitbox().intersects(avatar.pos.x + avatar.direction.x -15 , avatar.pos.y + avatar.direction.y -15, 10, 10 )){
                        flag = true;
                    }else if(avatar.pos.x > canvas.getWidth()-15 ){
                        avatar.pos.x = avatar.pos.x-10;
                    }else if(avatar.pos.y>canvas.getHeight()-15 ){
                        avatar.pos.y = avatar.pos.y-10;
                    }else if(avatar.pos.x < 10 ){
                        avatar.pos.x = avatar.pos.x+10;
                    }else if(avatar.pos.y < 10) {
                        avatar.pos.y = avatar.pos.y + 10;
                    }
                }
                if(!flag){
                    avatar.moveForward();
                }

            }
            if (Apressed) {
                avatar.changeAngle(-3);
            }
            if (Spressed) {
                boolean flag = false;
                for (int i = 0; i < walls.size(); i++) {
                    if (walls.get(i).getHitbox().intersects(avatar.pos.x - avatar.direction.x - 15, avatar.pos.y - avatar.direction.y - 15, 10, 10)) {
                        flag = true;
                    } else if (avatar.pos.x > canvas.getWidth() - 15) {
                        avatar.pos.x = avatar.pos.x - 10;
                    } else if (avatar.pos.y > canvas.getHeight() - 15) {
                        avatar.pos.y = avatar.pos.y - 10;
                    } else if (avatar.pos.x < 10) {
                        avatar.pos.x = avatar.pos.x + 10;
                    } else if (avatar.pos.y < 10) {
                        avatar.pos.y = avatar.pos.y + 10;
                    }
                }
                if(!flag){
                    avatar.moveBackward();
                }

            }
            if (Dpressed) {
                avatar.changeAngle(3);
            }
        }
       if(avatar2!=null){
           if(upPressed) {
               boolean flag=false;
               for (int i = 0; i < walls.size(); i++) {
                   if (walls.get(i).getHitbox().intersects(avatar2.pos.x + avatar2.direction.x - 15, avatar2.pos.y + avatar2.direction.y - 15, 10, 10)) {
                       flag = true;
                   } else if (avatar2.pos.x > canvas.getWidth() - 15) {
                       avatar2.pos.x = avatar2.pos.x - 10;
                   } else if (avatar2.pos.y > canvas.getHeight() - 15) {
                       avatar2.pos.y = avatar2.pos.y - 10;
                   } else if (avatar2.pos.x < 10) {
                       avatar2.pos.x = avatar2.pos.x + 10;
                   } else if (avatar2.pos.y < 10) {
                       avatar2.pos.y = avatar2.pos.y + 10;
                   }

               }
               if(!flag){
                   avatar2.moveForward();
               }
           }
           if(leftPressed){
               avatar2.changeAngle(-3);
           }
           if(downPressed){
               boolean flag=false;
               for (int i = 0; i < walls.size(); i++) {
                   if (walls.get(i).getHitbox().intersects(avatar2.pos.x - avatar2.direction.x - 15, avatar2.pos.y - avatar2.direction.y - 15, 10, 10)) {
                       flag = true;
                   }  else if(avatar2.pos.x > canvas.getWidth()-15 ){
                       avatar2.pos.x = avatar2.pos.x-10;
                   }else if(avatar2.pos.y>canvas.getHeight()-15 ){
                       avatar2.pos.y = avatar2.pos.y-10;
                   }else if(avatar2.pos.x < 10 ){
                       avatar2.pos.x = avatar2.pos.x+10;
                   }else if(avatar2.pos.y < 10){
                       avatar2.pos.y = avatar2.pos.y+10;
                   }

               }
               if(!flag){
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
        boolean flag;

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
            if(avatar!=null) {
                if (path3.exists()) {
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path3);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("No existe");
                }
                avatar.bullets = 6;
            }
        }
        if (keyEvent.getCode()==KeyCode.SPACE){
            if(avatar!=null) {
                if (avatar.bullets == 0) {
                    if (path2.exists()) {
                        try {
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path2);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                        } catch (UnsupportedAudioFileException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("No existe");
                    }
                } else {
                    if (path1.exists()) {
                        try {
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path1);
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            clip.start();
                        } catch (UnsupportedAudioFileException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (LineUnavailableException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("No existe");
                    }
                    Bullet bullet = new Bullet(canvas,
                            new Vector(avatar.pos.x, avatar.pos.y),
                            new Vector(2 * avatar.direction.x, 2 * avatar.direction.y), 1);
                    bullets.add(bullet);
                    avatar.bullets--;
                }
            }
        }
        if(keyEvent.getCode()==KeyCode.SHIFT){
            if(avatar2.bullets!=0){
                if(path1.exists()){
                    try {
                        AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                        Clip clip= AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                    }else{
                        System.out.println("No existe");
                    }
                Bullet bullet = new Bullet(canvas,
                        new Vector(avatar2.pos.x , avatar2.pos.y),
                        new Vector(2*avatar2.direction.x,2*avatar2.direction.y),2);
                bullets.add(bullet);
                avatar2.bullets--;
            }else {
                if(path2.exists()){
                    try {
                        AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path2);
                        Clip clip= AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (LineUnavailableException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("No existe");
                }
            }
        }
        if(keyEvent.getCode() == KeyCode.ENTER){
            if (path3.exists()) {
                try {
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(path3);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (LineUnavailableException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("No existe");
            }
            avatar2.bullets=6;
        }

    }
    /*public void attack() {
        //Random random = new Random();

        new Thread(
                () -> {
                    boolean bitchPlease=true;
        while (bitchPlease) {

            if (Wpressed) {
                if (enemy != null) {
                    if (enemy.bullets != 0) {
                        if(path1.exists()){
                            try {
                                AudioInputStream audioInputStream= AudioSystem.getAudioInputStream(path1);
                                Clip clip= AudioSystem.getClip();
                                clip.open(audioInputStream);
                                clip.start();
                            } catch (UnsupportedAudioFileException e) {
                                throw new RuntimeException(e);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            } catch (LineUnavailableException e) {
                                throw new RuntimeException(e);
                            }
                        }else{
                            System.out.println("No existe");
                        }
                        Bullet bullet = new Bullet(canvas,
                                new Vector(enemy.pos.x, enemy.pos.y),
                                new Vector(2 * enemy.direction.x, 2 * enemy.direction.y), 3);
                        bullets.add(bullet);
                        enemy.bullets--;
                    } else {
                        System.out.println("no bullets left");
                        bitchPlease=false;
                    }
                } else {
                    System.out.println("enemy null");
                }
            } else {
            }
        }
    }
    }*/
}