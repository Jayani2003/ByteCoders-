package com.lms.bytecoders.Controllers.Admin;

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

public class AdminUserController implements Initializable {
    @FXML
    private ComboBox<String> combo;

    @FXML
    private ComboBox<String> combo2;


    @FXML
    private ImageView profileImageView;


    @FXML
    private Button uploadButton;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set combo box items
        ObservableList<String> list = FXCollections.observableArrayList("Male", "Female","Other");
        combo.setItems(list);

        // Set combo box items
        ObservableList<String> list2 = FXCollections.observableArrayList("Student", "Technical Officer","Lecturer");
        combo2.setItems(list2);

        //upload image
        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(uploadButton.getScene().getWindow());
            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                profileImageView.setImage(image);


            }

        });



    }

}
