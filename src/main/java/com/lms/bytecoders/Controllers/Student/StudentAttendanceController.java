package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Attendance;
import com.lms.bytecoders.Models.Department;
import com.lms.bytecoders.Models.TimeTable;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentAttendanceController extends BaseController implements Initializable {

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableColumn<?, String> attEligibility;

    @FXML
    private TableColumn<?, Double> attPercentage;

    @FXML
    private TableView<Attendance> attendanceTable;

    @FXML
    private TableColumn<?, String> courseCode;

    @FXML
    private TableColumn<?, String> lecType;

    @FXML
    private TableColumn<?, String> studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
