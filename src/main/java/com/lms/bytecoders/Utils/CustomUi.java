package com.lms.bytecoders.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class CustomUi {
    public static void popUpErrorMessage(String error, String title, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(error);

        // Load custom CSS
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(CustomUi.class.getResource("/Styles/styles.css").toExternalForm());
        alert.showAndWait();
    }

}
