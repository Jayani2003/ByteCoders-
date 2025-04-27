package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Attendance;
import com.lms.bytecoders.Models.StuAttendance;
import com.lms.bytecoders.Models.StudentGPA;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LecStuAttendanceController extends BaseController implements Initializable {

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
    private ComboBox<String> mediTypeDrop;

    @FXML
    private TextField searchBox;

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
                SELECT sc.Student_Id, c.Course_Id, c.Type
                FROM course c
                JOIN stu_course sc ON c.Course_Id = sc.Course_Id
            """;
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                String studentId = rs.getString("Student_Id");
                String courseId = rs.getString("Course_Id");
                String courseType = rs.getString("Type");;
                if (val > 0){
                    percentage = getAttendanceWithMedical(studentId, courseId, conn);
                }else {
                    percentage = getAttendanceWithoutMedical(studentId, courseId, conn);
                }
                String eligibility = percentage >= 80 ? "Eligible" : "Not Eligible";
                tableData_.add(new StuAttendance(
                        eligibility,
                        Double.toString(percentage),
                        courseId,
                        courseType,
                        studentId
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

        // filtered list
        FilteredList<StuAttendance> filteredData = new FilteredList<>(attendanceTable.getItems(), e -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(attendance -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyWord = newValue.toLowerCase();

                if (attendance.getStudentId().toLowerCase().contains(keyWord)) {
                    return true;
                }else if (attendance.getCourseCode().toLowerCase().contains(keyWord)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<StuAttendance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(attendanceTable.comparatorProperty());
        attendanceTable.setItems(sortedData);
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
