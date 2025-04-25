package com.lms.bytecoders.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

public class MaterialCardController {
    @FXML
    private HBox root;
    @FXML private Label titleLabel;
    @FXML private Text descriptionText;
    @FXML private Label dateLabel;
    @FXML private Button openButton;

    private String link;

    public void setMaterialData(String title, String description, String date, String link) {
        titleLabel.setText(title);
        descriptionText.setText(description);
        dateLabel.setText("Uploaded: " + date);
        this.link = link;
    }

    @FXML
    private void handleOpen() {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateToLink(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}