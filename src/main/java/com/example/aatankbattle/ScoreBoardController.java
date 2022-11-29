package com.example.aatankbattle;

import com.example.aatankbattle.model.Avatar;
import com.example.aatankbattle.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ScoreBoardController {
        ArrayList<Player>players=new ArrayList<>();
        Player player;
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

    void arr(String name, int victories){
            player=new Player(name,victories);
            players.add(player);
            players.sort(Collections.reverseOrder());
            for (int i = 0; i < players.size(); i++) {
                    System.out.println(players.get(i).getName()+players.get(i).getVictories());
            }
}
int search(String name){
        for (int i = 0; i < players.size(); i++) {
                if(players.get(i).getName().equals(name)){
                        return players.get(i).getVictories();
                }
        }
return 0;
}




}
