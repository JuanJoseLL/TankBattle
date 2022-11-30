package com.example.aatankbattle;

import com.example.aatankbattle.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FirstScreenController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField playerNickname1;
    @FXML
    private TextField playerNickname2;

    public String name1="";
    public String name2="";
    @FXML
    private TextField CPU;
    @FXML
    private Button primaryButton;
    @FXML
    private GraphicsContext gc;
    @FXML
    private Image bg;
    @FXML
    private Image wall;
    @FXML
    private Canvas canvas;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        drawBackground();
    }

    public void drawBackground(){

        String uri2 = "file:"+ GameMain.class.getResource("principal.jpg").getPath();
        bg = new Image(uri2);
        gc.save();
        gc.drawImage(bg, 0,0, 775,600);
        gc.restore();

    }
    @FXML
    void press(ActionEvent event) {
        Player.getInstance().createPlayers(playerNickname1.getText(), playerNickname2.getText(),CPU.getText());
        GameMain.showWindow("canvasView.fxml");
        Stage current = (Stage) playerNickname1.getScene().getWindow();
        current.hide();
    }
    @FXML
    void pressS(ActionEvent event) {
        GameMain.showWindow("scoreBoard.fxml");
        Stage current = (Stage) playerNickname1.getScene().getWindow();
        current.hide();
    }
    public String av1(){

        return playerNickname1.getText();
    }
}