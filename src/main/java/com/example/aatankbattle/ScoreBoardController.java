package com.example.aatankbattle;

import com.example.aatankbattle.model.Avatar;
import com.example.aatankbattle.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ScoreBoardController {
        ArrayList<Avatar>avatars=new ArrayList<>();
        Avatar avatar;
        Avatar avatar2;

        @FXML
        private Button returnBTN;
        @FXML
        private Canvas canvas;
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

       /* @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                gc = canvas.getGraphicsContext2D();
                canvas.setFocusTraversable(true);

                drawBackground();

        }*/
       /* public void drawBackground(){

                String uri2 = "file:"+ GameMain.class.getResource("leel.png").getPath();
                bg = new Image(uri2);
                gc.save();
                gc.drawImage(bg, 0,0, 773,549);
                gc.restore();

        }*/

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
            for (int i = 0; i < avatars.size(); i++) {
                    System.out.println(avatars.get(i).getName()+avatars.get(i).getWins());
            }
    }
    int search(String name){

                for (int i = 0; i < avatars.size(); i++) {
                        if(avatars.get(i).getName().equals(name)){
                                return avatars.get(i).getWins();
                        }
                }
                return 0;
        }




}
