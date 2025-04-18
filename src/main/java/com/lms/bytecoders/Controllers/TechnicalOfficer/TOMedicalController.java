package com.lms.bytecoders.Controllers.TechnicalOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TOMedicalController implements Initializable {

    @FXML
    private ComboBox<String> comboBox1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<String> listlevel = FXCollections.observableArrayList("THEORY", "PRACTICAL");
        comboBox1.setItems(listlevel);

    }
}
