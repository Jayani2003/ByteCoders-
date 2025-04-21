package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentHomeController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

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
    void navigateToStuAttendance(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentAttendance.fxml");
    }

    @FXML
    void navigateToStuCourses(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentCourse.fxml");
    }

    @FXML
    void navigateToStuGrades(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentGrades.fxml");
    }

    @FXML
    void navigateToStuMedical(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentMedical.fxml");
    }

    @FXML
    void navigateToStuNotice(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentNotice.fxml");
    }

    @FXML
    void navigateToStuTimeTable(MouseEvent event) {
        navigate(MainPane, "/Fxml/Student/StudentTimeTable.fxml");
    }

}
