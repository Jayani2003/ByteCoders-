package com.lms.bytecoders.Controllers.TechnicalOfficer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Attendance;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class TOAttendanceController extends BaseController {

    @FXML
    private TextField tec_StuId_txt;
    @FXML
    private TextField tec_attendanceRecordId_txt;
    @FXML
    private TextField tec_technicalId_txt;
    @FXML
    private TextField tec_stuId2_txt;
    @FXML
    private TextField tec_course_code;
    @FXML
    private TextField tec_sessionNo_txt;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private ComboBox<String> comboBox2;
    @FXML
    private DatePicker tec_date;
    @FXML
    private TableView<Attendance> table_lec_type;
    @FXML
    private TableColumn<Attendance, String> table_attendanceRecordId;
    @FXML
    private TableColumn<Attendance, String> table_technicalId;
    @FXML
    private TableColumn<Attendance, String> table_stu_id;
    @FXML
    private TableColumn<Attendance, String> table_course_code;
    @FXML
    private TableColumn<Attendance, Integer> table_sessionNo;
    @FXML
    private TableColumn<Attendance, String> table_atten_status;
    @FXML
    private TableColumn<Attendance, String> atten_type;
    @FXML
    private TableColumn<Attendance, Date> table_date;

    private final ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        table_attendanceRecordId.setCellValueFactory(new PropertyValueFactory<>("attendanceRecordId"));
        table_technicalId.setCellValueFactory(new PropertyValueFactory<>("technicalId"));
        table_stu_id.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        table_course_code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        table_sessionNo.setCellValueFactory(new PropertyValueFactory<>("sessionNo"));
        table_atten_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        atten_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        table_date.setCellValueFactory(new PropertyValueFactory<>("date"));


        comboBox1.setItems(FXCollections.observableArrayList("THEORY", "PRACTICAL"));
        comboBox2.setItems(FXCollections.observableArrayList("PRESENT", "ABSENT", "MC"));


        tec_technicalId_txt.setText(getUserId());
        tec_technicalId_txt.setEditable(false);


        loadAttendanceData();


        tec_StuId_txt.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadAttendanceData();

            }
        });

        
        table_lec_type.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tec_attendanceRecordId_txt.setText(newSelection.getAttendanceRecordId());
                tec_stuId2_txt.setText(newSelection.getStudentId());
                tec_course_code.setText(newSelection.getCourseId());
                tec_sessionNo_txt.setText(String.valueOf(newSelection.getSessionNo()));
                comboBox1.setValue(newSelection.getType());
                comboBox2.setValue(newSelection.getStatus());
                tec_date.setValue(newSelection.getDate().toLocalDate());
            }
        });
    }

    private void loadAttendanceData() {
        String query = "SELECT * FROM attendance";
        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(query);

            attendanceList.clear();
            while (rs.next()) {
                Attendance attendance = new Attendance(
                        rs.getString("AttendanceRecord_Id"),
                        rs.getString("Technical_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getInt("Session_No"),
                        rs.getString("Status"),
                        rs.getString("Type"),
                        rs.getDate("Date")
                );
                attendanceList.add(attendance);
            }
            table_lec_type.setItems(attendanceList);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error loading attendance data: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @FXML
    private void searchAttendance() {
        String searchId = tec_StuId_txt.getText().trim();
        if (searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please enter a student ID to search.");
            return;
        }

        String courseId = tec_course_code.getText().trim();
        String query = "SELECT * FROM attendance WHERE Student_Id = ?" + (courseId.isEmpty() ? "" : " AND Course_Id = ?");
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            ps.setString(1, searchId);
            if (!courseId.isEmpty()) {
                ps.setString(2, courseId);
            }
            rs = ps.executeQuery();

            ObservableList<Attendance> foundAttendances = FXCollections.observableArrayList();
            while (rs.next()) {
                Attendance attendance = new Attendance(
                        rs.getString("AttendanceRecord_Id"),
                        rs.getString("Technical_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getInt("Session_No"),
                        rs.getString("Status"),
                        rs.getString("Type"),
                        rs.getDate("Date")
                );
                foundAttendances.add(attendance);
            }

            table_lec_type.setItems(foundAttendances);
            if (!foundAttendances.isEmpty()) {
                String selectedCourseId = courseId.isEmpty() ? foundAttendances.get(0).getCourseId() : courseId;
                try {
                    double percentage = getAttendanceWithMedical(searchId, selectedCourseId, conn);

                } catch (Exception e) {

                    showAlert(Alert.AlertType.WARNING, "Unable to calculate attendance percentage: " + e.getMessage());
                }
            } else {

                showAlert(Alert.AlertType.INFORMATION, "No records found for Student ID: " + searchId);
            }

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error searching attendance: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @FXML
    private void addAttendance() {
        if (!validateInput()) {
            return;
        }

        String query = "INSERT INTO attendance (AttendanceRecord_Id, Technical_Id, Student_Id, Course_Id, Session_No, Status, Type, Date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            ps.setString(1, tec_attendanceRecordId_txt.getText().trim());
            ps.setString(2, getUserId());
            ps.setString(3, tec_stuId2_txt.getText().trim());
            ps.setString(4, tec_course_code.getText().trim());
            ps.setInt(5, Integer.parseInt(tec_sessionNo_txt.getText().trim()));
            ps.setString(6, comboBox2.getValue());
            ps.setString(7, comboBox1.getValue());
            ps.setDate(8, Date.valueOf(tec_date.getValue()));

            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Attendance record added successfully.");
            loadAttendanceData();
            clearFields();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error adding attendance: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Session Number must be a valid integer.");
        } finally {
            closeResources();
        }
    }

    @FXML
    private void updateAttendance() {
        Attendance selected = table_lec_type.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a record to update.");
            return;
        }

        if (!validateInput()) {
            return;
        }

        String query = "UPDATE attendance SET Technical_Id = ?, Student_Id = ?, Course_Id = ?, Session_No = ?, Status = ?, Type = ?, Date = ? WHERE AttendanceRecord_Id = ?";
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            ps.setString(1, getUserId());
            ps.setString(2, tec_stuId2_txt.getText().trim());
            ps.setString(3, tec_course_code.getText().trim());
            ps.setInt(4, Integer.parseInt(tec_sessionNo_txt.getText().trim()));
            ps.setString(5, comboBox2.getValue());
            ps.setString(6, comboBox1.getValue());
            ps.setDate(7, Date.valueOf(tec_date.getValue()));
            ps.setString(8, tec_attendanceRecordId_txt.getText().trim());

            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Attendance record updated successfully.");
            loadAttendanceData();
            clearFields();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error updating attendance: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Session Number must be a valid integer.");
        } finally {
            closeResources();
        }
    }

    @FXML
    private void deleteAttendance() {
        Attendance selected = table_lec_type.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a record to delete.");
            return;
        }

        String query = "DELETE FROM attendance WHERE AttendanceRecord_Id = ?";
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            ps.setString(1, selected.getAttendanceRecordId());
            ps.executeUpdate();
            showAlert(Alert.AlertType.INFORMATION, "Attendance record deleted successfully.");
            loadAttendanceData();
            clearFields();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error deleting attendance: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    @FXML
    private void clearAttendance() {
        clearFields();
    }



    private boolean validateInput() {
        if (tec_attendanceRecordId_txt.getText().trim().isEmpty() ||
                tec_stuId2_txt.getText().trim().isEmpty() ||
                tec_course_code.getText().trim().isEmpty() ||
                tec_sessionNo_txt.getText().trim().isEmpty() ||
                comboBox1.getValue() == null ||
                comboBox2.getValue() == null ||
                tec_date.getValue() == null) {
            showAlert(Alert.AlertType.WARNING, "Please fill all fields before proceeding.");
            return false;
        }
        try {
            Integer.parseInt(tec_sessionNo_txt.getText().trim());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Session Number must be a valid integer.");
            return false;
        }
        return true;
    }

    private void clearFields() {
        tec_attendanceRecordId_txt.clear();
        tec_stuId2_txt.clear();
        tec_course_code.clear();
        tec_sessionNo_txt.clear();
        comboBox1.getSelectionModel().clearSelection();
        comboBox2.getSelectionModel().clearSelection();
        tec_date.setValue(null);

        table_lec_type.getSelectionModel().clearSelection();

    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Attendance Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}