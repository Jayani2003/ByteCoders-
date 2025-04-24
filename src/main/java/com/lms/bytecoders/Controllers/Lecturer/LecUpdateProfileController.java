package com.lms.bytecoders.Controllers.Lecturer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LecUpdateProfileController implements Initializable {
    @FXML
    private ComboBox<String> combo;


    @FXML
    private ImageView profileImageView;


    @FXML
    private Button uploadButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
