package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Attendance;
import com.lms.bytecoders.Models.StuAttendance;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StudentAttendanceController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> mediTypeDrop;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TableColumn<?, String> attEligibility;

    @FXML
    private TableColumn<?, String> attPercentage;

    @FXML
    private TableView<StuAttendance> attendanceTable;

    @FXML
    private TableColumn<?, String> courseCode;

    @FXML
    private TableColumn<?, String> lecType;

    @FXML
    private TableColumn<?, String> studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> list_level = FXCollections.observableArrayList("WITH MC", "WITHOUT MC");
        mediTypeDrop.setItems(list_level);
        mediTypeDrop.setValue("WITHOUT MC");
        setTableData(0);
    }

    private void setTableData(int val) {
        ObservableList<StuAttendance> tableData_ = FXCollections.observableArrayList();
        double percentage = 0;
        try {
            conn = Database.Conn();
            String sql = """
                SELECT c.Course_Id, c.Type
                FROM course c
                JOIN stu_course sc ON c.Course_Id = sc.Course_Id
                WHERE sc.Student_Id = ?
            """;
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                String courseId = rs.getString("Course_Id");
                String courseType = rs.getString("Type");;
                if (val > 0){
                    percentage = getAttendanceWithMedical(BaseController.getUserId(), courseId, conn);
                }else {
                    percentage = getAttendanceWithoutMedical(BaseController.getUserId(), courseId, conn);
                }
                String eligibility = percentage >= 80 ? "Eligible" : "Not Eligible";
                tableData_.add(new StuAttendance(
                        eligibility,
                        Double.toString(percentage),
                        courseId,
                        courseType,
                        BaseController.getUserId()
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }

        setTable(tableData_);
    }

    private void setTable(ObservableList<StuAttendance> data) {
        attEligibility.setCellValueFactory(new PropertyValueFactory<>("eligibility"));
        attPercentage.setCellValueFactory(new PropertyValueFactory<>("attendancePercentage"));
        courseCode.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        lecType.setCellValueFactory(new PropertyValueFactory<>("lecType"));
        studentId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        attendanceTable.setItems(data);
    }

    @FXML
    private void updateTableData(ActionEvent event) {
        if (mediTypeDrop.getValue().equals("WITH MC")) {
            setTableData(1);
        }else if (mediTypeDrop.getValue().equals("WITHOUT MC")) {
            setTableData(0);
        }else {
            System.out.println("ComboBox value is null");
        }
    }
}