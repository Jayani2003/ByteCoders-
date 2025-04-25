package com.lms.bytecoders.Controllers.Admin;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminHomeController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Pane adminCoursesPane;

    @FXML
    private Pane adminNoticePane;

    @FXML
    private Pane adminTimeTablePane;

    @FXML
    private Pane adminUserPane;

    @FXML
    private Label userGreetLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userGreetLabel.setText(BaseController.getName());
    }

    @FXML
    void navigateToAdminNotice(MouseEvent event) {
        navigate(MainPane, "/Fxml/Admin/AdminNotice.fxml");
    }

    @FXML
    void navigateToAdminTimeTable(MouseEvent event) {
        navigate(MainPane, "/Fxml/Admin/AdminTimeTable.fxml");
    }

    @FXML
    void navigateToAdminUser(MouseEvent event) {
        navigate(MainPane, "/Fxml/Admin/AdminUser.fxml");
    }

    @FXML
    void navigateToAdminCourse(MouseEvent event) {
        navigate(MainPane, "/Fxml/Admin/AdminCourse.fxml");
    }
}
