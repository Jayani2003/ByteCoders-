package com.lms.bytecoders.Controllers.TechnicalOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TOAttendanceController implements Initializable {

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set combo box items
        ObservableList<String> listlevel = FXCollections.observableArrayList("THEORY", "PRACTICAL");
        comboBox1.setItems(listlevel);

        ObservableList<String> listlevel2 = FXCollections.observableArrayList("YES", "NO");
        comboBox2.setItems(listlevel2);

    }
}
