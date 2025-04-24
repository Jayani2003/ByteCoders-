package com.lms.bytecoders.Controllers.TechnicalOfficer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class TOHomeController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Pane TOAttendancePane;

    @FXML
    private Pane TOMedicalPane;

    @FXML
    private Pane TONoticePane;

    @FXML
    private Pane TOTimeTablePane;

    @FXML
    private Label userGreetLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userGreetLabel.setText(BaseController.getName());
    }


    @FXML
    void navigateToTOAttendance(MouseEvent event) {
        navigate(MainPane, "/Fxml/TechnicalOfficer/TOAttendance.fxml");
    }

    @FXML
    void navigateToTOMedical(MouseEvent event) {
        navigate(MainPane, "/Fxml/TechnicalOfficer/TOMedical.fxml");
    }

    @FXML
    void navigateToTONotice(MouseEvent event) {
        navigate(MainPane, "/Fxml/TechnicalOfficer/TONotice.fxml");
    }

    @FXML
    void navigateToTOTimeTable(MouseEvent event) {
        navigate(MainPane, "/Fxml/TechnicalOfficer/TOTimeTable.fxml");
    }

}
