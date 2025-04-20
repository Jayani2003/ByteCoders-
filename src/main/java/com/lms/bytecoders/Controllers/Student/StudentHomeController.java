package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentHomeController extends StudentDashboardController {

    @FXML
    private BorderPane MainPane;

    @FXML
    private Pane attendancePane;

    @FXML
    private Pane coursesPane;

    @FXML
    private Pane gradesPane;

    @FXML
    private Pane medicalPane;

    @FXML
    private Pane noticePane;

    @FXML
    private Pane timeTablePane;

    @FXML
    private Label userGreetLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userGreetLabel.setText(BaseController.getName());
    }


    @FXML
    public void navigateToAttendance(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/MyAttendance.fxml");
    }

    @FXML
    public void navigateToCourses(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentCourse.fxml");
    }

    @FXML
    public void navigateToGrades(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentGrades.fxml");
    }

    @FXML
    public void navigateToMedical(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/MyMedical.fxml");
    }

    @FXML
    public void navigateToTimeTable(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentTimeTable.fxml");
    }

    @FXML
    public void navigteToNotice(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentNotice.fxml");
    }

}
