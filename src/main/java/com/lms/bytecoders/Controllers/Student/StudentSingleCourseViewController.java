package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentSingleCourseViewController extends BaseController implements Initializable {

    @FXML
    private Label singleCourseHeading;

    @FXML
    private Label singleCourseID;

    @FXML
    private AnchorPane stuCourseMainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        singleCourseHeading.setText();
//        singleCourseID.setText();
    }
}
