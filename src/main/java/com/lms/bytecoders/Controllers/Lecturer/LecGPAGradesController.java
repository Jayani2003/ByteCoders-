package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LecGPAGradesController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Pane lectureStuGPAPane;

    @FXML
    private Pane lectureStuGradesPane;

    @FXML
    void navigateToLectureStuGPA(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecStuGPA.fxml");
    }

    @FXML
    void navigateToLectureStuGrades(MouseEvent event) {
        navigate(MainPane,"/Fxml/Lecturer/LecStuGrades.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
