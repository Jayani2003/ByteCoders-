package com.lms.bytecoders.Controllers.TechnicalOfficer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.lms.bytecoders.Models.Attendance;
import java.sql.*;
import java.text.SimpleDateFormat;

public class TOAttendanceController {

    @FXML
    private TextField tec_StuId_txt;
    @FXML
    private TextField tec_stuId2_txt;
    @FXML
    private TextField tec_course_code;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private DatePicker tec_date;

    @FXML
    private TableView<Attendance> table_lec_type;
    @FXML
    private TableColumn<Attendance, String> table_stu_id;
    @FXML
    private TableColumn<Attendance, String> table_course_code;
    @FXML
    private TableColumn<Attendance, String> table_atten_status;
    @FXML
    private TableColumn<Attendance, String> atten_type;
    @FXML
    private TableColumn<Attendance, Date> table_date;

    private final ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();


    public TOAttendanceController() {

    }

    private void loadAttendanceData() {
        String query = "SELECT * FROM attendance";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/teclms", "root", "1234");
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            attendanceList.clear();

            while (rs.next()) {
                String studentId = rs.getString("Student_Id");
                String courseId = rs.getString("Course_Id");
                String status = rs.getString("Status");
                String type = rs.getString("Type");
                Date date = rs.getDate("Date");

                Attendance attendance = new Attendance(studentId, courseId, status, type, date);
                attendanceList.add(attendance);
            }


            table_lec_type.setItems(attendanceList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {

        table_stu_id.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        table_course_code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        table_atten_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        atten_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_date.setCellValueFactory(new PropertyValueFactory<>("date"));


        loadAttendanceData();


        comboBox1.setItems(FXCollections.observableArrayList("THEORY", "PRACTICAL"));
        comboBox2.setItems(FXCollections.observableArrayList("PRESENT", "ABSENT"));

        tec_StuId_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                table_lec_type.setItems(attendanceList);
            }
        });
    }

    @FXML
    private void searchAttendance() {
        String searchId = tec_StuId_txt.getText().trim();
        if (searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter a student ID to search.");
            return;
        }


        ObservableList<Attendance> foundAttendances = FXCollections.observableArrayList();

        for (Attendance attendance : attendanceList) {
            if (attendance.getStudentId().equalsIgnoreCase(searchId)) {
                foundAttendances.add(attendance);
            }
        }


        if (!foundAttendances.isEmpty()) {
            table_lec_type.setItems(foundAttendances);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No records found for Student ID: " + searchId);
        }
    }


    @FXML
    private void addAttendance() {
        if (tec_stuId2_txt.getText().isEmpty() || tec_course_code.getText().isEmpty() ||
                comboBox1.getValue() == null || comboBox2.getValue() == null || tec_date.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill all fields before adding.");
            return;
        }

        Attendance newAttendance = new Attendance(
                tec_stuId2_txt.getText(),
                tec_course_code.getText(),
                comboBox2.getValue(),
                comboBox1.getValue(),
                Date.valueOf(tec_date.getValue())
        );


        attendanceList.add(newAttendance);



        clearFields();
    }

    @FXML
    private void updateAttendance() {
        Attendance selected = table_lec_type.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a record to update.");
            return;
        }


        Attendance updatedAttendance = new Attendance(
                tec_stuId2_txt.getText(),
                tec_course_code.getText(),
                comboBox2.getValue(),
                comboBox1.getValue(),
                Date.valueOf(tec_date.getValue())
        );

        int selectedIndex = table_lec_type.getSelectionModel().getSelectedIndex();
        attendanceList.set(selectedIndex, updatedAttendance);


        clearFields();
    }

    @FXML
    private void deleteAttendance() {
        Attendance selected = table_lec_type.getSelectionModel().getSelectedItem();
        if (selected != null) {
            attendanceList.remove(selected);


            clearFields();
        } else {
            showAlert(Alert.AlertType.WARNING, "Please select a record to delete.");
        }
    }

    @FXML
    private void clearAttendance() {
        clearFields();
    }

    private void clearFields() {
        tec_stuId2_txt.clear();
        tec_course_code.clear();
        comboBox1.getSelectionModel().clearSelection();
        comboBox2.getSelectionModel().clearSelection();
        tec_date.setValue(null);
        table_lec_type.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Attendance Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
