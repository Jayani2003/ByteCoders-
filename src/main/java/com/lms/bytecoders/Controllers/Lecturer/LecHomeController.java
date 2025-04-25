package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class LecHomeController  extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Pane lectureAttendancePane;

    @FXML
    private Pane lectureCoursesPane;

    @FXML
    private Pane lectureEligibilityPane;

    @FXML
    private Pane lectureGradesPane;

    @FXML
    private Pane lectureMarksPane;

    @FXML
    private Pane lectureNoticePane;

    @FXML
    private Pane lectureStudentPane;

    @FXML
    private Pane lectureTimeTablePane;

    @FXML
    private Label userGreetLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userGreetLabel.setText(BaseController.getName());
    }

    @FXML
    void navigateToLectureAttendance(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecStuAttendace.fxml");
    }

    @FXML
    void navigateToLectureCourses(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LectureCourses.fxml");
    }

    @FXML
    void navigateToLectureEligibility(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecStuEligibility.fxml");
    }

    @FXML
    void navigateToLectureGrades(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecStuGrades.fxml");
    }

    @FXML
    void navigateToLectureMarks(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecAddMark.fxml");
    }

    @FXML
    void navigateToLectureNotice(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecNotice.fxml");
    }

    @FXML
    void navigateToLectureStudent(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecStuDetails.fxml");
    }

    @FXML
    void navigateToLectureTimeTable(MouseEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LectureTimeTable.fxml");
    }
}
