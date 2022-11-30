package com.example.aatankbattle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WinCanvaController implements Initializable {

    @FXML
    private Button leaderboardBTN;
    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private Image bg;
    @FXML
    private Button home;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);

        drawBackground();

    }
    public void drawBackground(){

        String uri2 = "file:"+ GameMain.class.getResource("salute.png").getPath();
        bg = new Image(uri2);
        gc.save();
        gc.drawImage(bg, 0,0, 773,549);
        gc.restore();

    }
    @FXML
    void returnM(ActionEvent event) {
        GameMain.showWindow("firstScreen.fxml");
        Stage current = (Stage) leaderboardBTN.getScene().getWindow();
        current.hide();
    }
    @FXML
    void pressS(ActionEvent event) {
        GameMain.showWindow("scoreBoard.fxml");
        Stage current = (Stage) leaderboardBTN.getScene().getWindow();
        current.hide();
    }
}
