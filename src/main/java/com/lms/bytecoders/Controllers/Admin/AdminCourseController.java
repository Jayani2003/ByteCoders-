package com.lms.bytecoders.Controllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminCourseController implements Initializable {

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonDelete;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private ComboBox<String> comboBox3;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
