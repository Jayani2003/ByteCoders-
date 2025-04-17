package com.lms.bytecoders.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneHandler {
    public static void switchScene(Node ob, Parent sceneName, String title) {
        Scene scene = new Scene(sceneName);
        Stage stage = (Stage) ob.getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(scene);
    }

    public static FXMLLoader createLoader(String path) {
        return new FXMLLoader(SceneHandler.class.getResource(path));
    }
}
