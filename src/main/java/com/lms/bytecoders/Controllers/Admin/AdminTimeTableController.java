package com.lms.bytecoders.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminTimeTableController implements Initializable {

    @FXML
    private ComboBox<String> combolevel;

    @FXML
    private ComboBox<String> combosemester;

    @FXML
    private ComboBox<String> combodepartment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set combo box items
        ObservableList<String> listlevel = FXCollections.observableArrayList("Level 1", "Level 2","Level 3","Level 4");
        combolevel.setItems(listlevel);

        ObservableList<String> listsemester = FXCollections.observableArrayList("Semester 1", "Semester 2");
        combosemester.setItems(listsemester);

        ObservableList<String> listdepartment = FXCollections.observableArrayList("ICT", "ET","BST");
        combodepartment.setItems(listdepartment);




    }
}
