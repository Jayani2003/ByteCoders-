package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Student.StudentDashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LecAddMarkController extends StudentDashboardController {

    @FXML
    private AnchorPane LecMainPane ;

    @FXML
    private TextField stuIdSearch;

    @FXML
    private TextField stuIdTxt;

    @FXML
    private TextField courseCodeTxt;

    @FXML
    private TextField quiz1Txt;

    @FXML
    private TextField quiz2Txt;

    @FXML
    private TextField quiz3Txt;

    @FXML
    private TextField midTTxt;

    @FXML
    private TextField midPTxt;

    @FXML
    private TextField endTTxt;

    @FXML
    private TextField endPTxt;

    @FXML
    private TextField assesmentTxt;

    @FXML
    private TableColumn stuIdTbl;

    @FXML
    private TableColumn courseCodeTbl;

    @FXML
    private TableColumn quiz1Tbl;

    @FXML
    private TableColumn quiz2Tbl;

    @FXML
    private TableColumn quiz3Tbl;

    @FXML
    private TableColumn midTTbl;

    @FXML
    private TableColumn midPTbl;

    @FXML
    private TableColumn endTTbl;

    @FXML
    private TableColumn endPTbl;

    @FXML
    private TableColumn assesmentTbl;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

}
