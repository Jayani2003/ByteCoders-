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

        ObservableList<String> listlevel3 = FXCollections.observableArrayList("THEORY", "PRACTICAL");
        comboBox3.setItems(listlevel3);

        ObservableList<String> listlevel2 = FXCollections.observableArrayList("THEORY", "PRACTICAL");
        comboBox2.setItems(listlevel3);

        ObservableList<String> listlevel1 = FXCollections.observableArrayList("ICT2113", "ICT2122", "ICT2133", "ICT2142", "ICT2152");
        comboBox1.setItems(listlevel3);

    }
}
