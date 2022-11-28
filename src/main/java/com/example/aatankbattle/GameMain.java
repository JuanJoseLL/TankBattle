package com.example.aatankbattle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        showWindow("firstScreen.fxml");
    }


    public static void showWindow(String fxml){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(GameMain.class.getResource(fxml));
            Scene scene=new Scene(fxmlLoader.load(),773,548);
            Stage window=new Stage();
            window.setScene(scene);
            window.show();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}