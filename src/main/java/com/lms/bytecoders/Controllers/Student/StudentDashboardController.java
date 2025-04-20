package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentDashboardController extends BaseController implements Initializable {

    @FXML
    private BorderPane MainPane;

    @FXML
    private Label dashboardLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button profileButton;

    @FXML
    private BorderPane studentNavPane;

    @FXML
    private Label userNameLabel;

    @FXML
    private Circle userProfilePic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardLabel.setText(BaseController.getDashboardName());
        getDashBoardDataFromDB(userProfilePic, userNameLabel);
        navigate(studentNavPane, "/Fxml/Student/StudentHome.fxml");
    }

    @FXML
    public void navigateToHome(ActionEvent event) {
        navigate(studentNavPane, "/Fxml/Student/StudentHome.fxml");
    }

    @FXML
    public void navigateToUpdateProfile(ActionEvent event) {
        navigate(studentNavPane, "/Fxml/Student/StudentUpdateProfile.fxml");
    }

    @FXML
    public void logOut(ActionEvent event) throws IOException {
        loadLogin(logOutButton);
        BaseController.setUserId(null);
    }

}
