package com.example.aatankbattle;

import com.example.aatankbattle.model.Avatar;
import com.example.aatankbattle.model.Scoreboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ScoreBoardController  implements Initializable{
        ArrayList<Avatar>avatars=new ArrayList<>();
        Avatar avatar;
        @FXML
        private Button returnBTN;
        @FXML
        private Canvas canvas;
        @FXML
        private GraphicsContext gc;
        private Image bg;

        @FXML
        private Label name1;

        @FXML
        private Label score1;

        @FXML
        private Label name2;

        @FXML
        private Label name3;

        @FXML
        private Label name4;

        @FXML
        private Label name5;

        @FXML
        private Label name6;

        @FXML
        private Label name7;

        @FXML
        private Label name8;

        @FXML
        private Label name9;

        @FXML
        private Label score2;

        @FXML
        private Label score3;

        @FXML
        private Label score4;

        @FXML
        private Label score5;

        @FXML
        private Label score6;

        @FXML
        private Label score7;

        @FXML
        private Label score8;

        @FXML
        private Label score9;

        @FXML
        private Label name10;

        @FXML
        private Label score10;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                gc = canvas.getGraphicsContext2D();
                canvas.setFocusTraversable(true);
                updateSb();
                drawBackground();
                /*Scoreboard.getInstance().insert(new Avatar("san", 5));
                Scoreboard.getInstance().insert(new Avatar("jua", 3));
                Scoreboard.getInstance().insert(new Avatar("ana", 6));
                Scoreboard.getInstance().insert(new Avatar("mat", 2));
                Scoreboard.getInstance().insert(new Avatar("mat", 2));*/

        }
       public void drawBackground(){
                String uri2 = "file:"+ GameMain.class.getResource("bcn.png").getPath();
                bg = new Image(uri2);
                gc.save();
                gc.drawImage(bg, 0,0, 773,549);
                gc.restore();

        }

    @FXML
    void returnM(ActionEvent event) {
            GameMain.showWindow("firstScreen.fxml");
            Stage current = (Stage) score9.getScene().getWindow();
            current.hide();
    }

        public void arr(String name, int victories){
                avatar=new Avatar(name,victories);
                avatars.add(avatar);
                avatars.sort(Comparator.comparing(Avatar::getWins));
                for(int i=0;i<avatars.size();i++){
                        Scoreboard.getInstance().insert(avatars.get(i));
                }
        }

        public int search(String name){
                for (int i = 0; i < avatars.size(); i++) {
                        if(avatars.get(i).getName().equals(name)){
                                return avatars.get(i).getWins();
                        }
                }
                return 0;
        }
        public void updateSb(){
                ArrayList<Avatar> avatars = Scoreboard.getInstance().updateLeaderboard();

                if(avatars.size()>=1 && avatars.get(0)!=null){
                        name1.setText(avatars.get(0).getName());
                        score1.setText(String.valueOf(avatars.get(0).getWins()));
                }
                if(avatars.size()>=2 && avatars.get(1)!=null){
                        name2.setText(avatars.get(1).getName());
                        score2.setText(String.valueOf(avatars.get(1).getWins()));
                }
                if(avatars.size()>=3 && avatars.get(2)!=null){
                        name3.setText(avatars.get(2).getName());
                        score3.setText(String.valueOf(avatars.get(2).getWins()));
                }
                if(avatars.size()>=4 && avatars.get(3)!=null){
                        name4.setText(avatars.get(3).getName());
                        score4.setText(String.valueOf(avatars.get(3).getWins()));
                }
                if(avatars.size()>=5 && avatars.get(4)!=null){
                        name5.setText(avatars.get(4).getName());
                        score5.setText(String.valueOf(avatars.get(4).getWins()));
                }
                if(avatars.size()>=6 && avatars.get(5)!=null){
                        name6.setText(avatars.get(5).getName());
                        score6.setText(String.valueOf(avatars.get(5).getWins()));
                }
                if(avatars.size()>=7 && avatars.get(6)!=null){
                        name7.setText(avatars.get(6).getName());
                        score7.setText(String.valueOf(avatars.get(6).getWins()));
                }
                if(avatars.size()>=8 && avatars.get(7)!=null){
                        name8.setText(avatars.get(7).getName());
                        score8.setText(String.valueOf(avatars.get(7).getWins()));
                }
                if(avatars.size()>=9 && avatars.get(8)!=null){
                        name9.setText(avatars.get(8).getName());
                        score9.setText(String.valueOf(avatars.get(8).getWins()));
                }
                if(avatars.size()>=10 && avatars.get(9)!=null){
                        name10.setText(avatars.get(9).getName());
                        score10.setText(String.valueOf(avatars.get(9).getWins()));
                }

        }




}
