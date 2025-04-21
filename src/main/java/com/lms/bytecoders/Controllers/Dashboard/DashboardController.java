package com.lms.bytecoders.Controllers.Dashboard;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController extends BaseController implements Initializable {

    @FXML
    private BorderPane DashboardPane;

    @FXML
    private Label dashboardLabel;

    @FXML
    private Button homeButton;

    @FXML
    private Button logOutButton;

    @FXML
    private AnchorPane navPane;

    @FXML
    private Button profileButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Circle userProfilePic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dashboardLabel.setText(BaseController.getDashboardName());
        getDashBoardDataFromDB(userProfilePic, userNameLabel);

        try {
            navigateToDashboardHome(navPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (BaseController.getDashboardName().equals("ADMIN")) {
            profileButton.setDisable(true);
            profileButton.setVisible(false);

        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        loadLogin(logOutButton);
        BaseController.setUserId(null);
    }

    @FXML
    void navigateToHome(ActionEvent event) throws IOException {
        navigateToDashboardHome(navPane);
    }

    @FXML
    void navigateToUpdateProfile(ActionEvent event) throws IOException  {
        navigateToDashboardUpdate(navPane);
    }
}
