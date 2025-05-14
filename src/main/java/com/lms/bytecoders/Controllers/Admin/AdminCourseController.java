package com.lms.bytecoders.Controllers.Admin;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Admin;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminCourseController extends BaseController implements Initializable {

    @FXML
    private Button buttonAdd, buttonClear, buttonDelete, buttonUpdate;

    @FXML
    private ComboBox<String> a_level, a_semester, a_department, a_lec, a_status, a_type;

    @FXML
    private TextField a_c_name, week, code, a_credits, a_p_hours, a_t_hours;

    @FXML
    private TableView<Admin> admincourses;

    @FXML
    private TableColumn<Admin, String> level, semester, department, c_code, c_name, c_type, c_lec, c_week, c_status_combo;

    @FXML
    private TableColumn<Admin, Integer> credits, p_hours, t_hours;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        admincourses.setOnMouseClicked(e -> {
            Admin selectedAdmin = admincourses.getSelectionModel().getSelectedItem();
            if (selectedAdmin != null) {
                fillForm(selectedAdmin);
            }
        });

        ObservableList<String> c1 = FXCollections.observableArrayList("LEVEL1", "LEVEL2", "LEVEL3", "LEVEL4");
        a_level.setItems(c1);

        ObservableList<String> c2 = FXCollections.observableArrayList("SEMESTER1", "SEMESTER2");
        a_semester.setItems(c2);

        ObservableList<String> c3 = FXCollections.observableArrayList("ICT", "ET", "BST");
        a_department.setItems(c3);

        ObservableList<String> c4 = FXCollections.observableArrayList("LE0001", "LE0002", "LE0003", "LE0004", "LE0005");
        a_lec.setItems(c4);

        ObservableList<String> c5 = FXCollections.observableArrayList("CREDIT", "NON-CREDIT");
        a_status.setItems(c5);

        ObservableList<String> c6 = FXCollections.observableArrayList("THEORY", "PRACTICAL", "THEORY_PRACTICAL");
        a_type.setItems(c6);

        setupTable();
        loadCourses();

        buttonAdd.setOnAction(e -> buttonAdd());
        buttonUpdate.setOnAction(e -> buttonUpdate());
        buttonDelete.setOnAction(e -> buttonDelete());
        buttonClear.setOnAction(e -> clearForm());
    }

    private void fillForm(Admin admin) {
        a_level.setValue(admin.getLevel());
        a_semester.setValue(admin.getSemester());
        a_department.setValue(admin.getDepartment());
        code.setText(admin.getCourseId());
        a_c_name.setText(admin.getCourseName());
        a_type.setValue(admin.getCourseType());
        a_lec.setValue(admin.getLecturerId());
        week.setText(admin.getWeek());
        a_status.setValue(admin.getCreditStatus());
        a_credits.setText(String.valueOf(admin.getCredits()));
        a_p_hours.setText(String.valueOf(admin.getP_Hours()));
        a_t_hours.setText(String.valueOf(admin.getT_Hours()));
    }

    private void setupTable() {
        level.setCellValueFactory(new PropertyValueFactory<>("level"));
        semester.setCellValueFactory(new PropertyValueFactory<>("semester"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        c_code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        c_name.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        c_type.setCellValueFactory(new PropertyValueFactory<>("courseType"));
        c_lec.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        c_week.setCellValueFactory(new PropertyValueFactory<>("week"));
        c_status_combo.setCellValueFactory(new PropertyValueFactory<>("creditStatus"));
        credits.setCellValueFactory(new PropertyValueFactory<>("credits"));
        p_hours.setCellValueFactory(new PropertyValueFactory<>("P_Hours"));
        t_hours.setCellValueFactory(new PropertyValueFactory<>("T_Hours"));
    }

    private void loadCourses() {
        ObservableList<Admin> adminCourses = FXCollections.observableArrayList();
        adminCourses.clear();

        String sql = "SELECT * FROM course";

        try (Connection conn = Database.Conn();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                adminCourses.add(new Admin(
                        rs.getString("Level"),
                        rs.getString("Semester"),
                        rs.getString("Department"),
                        rs.getString("Course_Id"),
                        rs.getString("Course_Name"),
                        rs.getString("Type"),
                        rs.getString("Lecturer_Id"),
                        rs.getString("Week"),
                        rs.getString("CreditStatus"),
                        rs.getInt("Credits"),
                        rs.getInt("P_Hours"),
                        rs.getInt("T_Hours")
                ));
            }

            admincourses.setItems(adminCourses);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load courses.");
        }
    }

    private void buttonAdd() {
        if (a_level.getValue() == null ||
                a_semester.getValue() == null ||
                a_department.getValue() == null ||
                code.getText().isEmpty() ||
                a_c_name.getText().isEmpty() ||
                a_type.getValue() == null ||
                a_lec.getValue() == null ||
                week.getText().isEmpty() ||
                a_status.getValue() == null ||
                a_credits.getText().isEmpty() ||
                a_p_hours.getText().isEmpty() ||
                a_t_hours.getText().isEmpty()) {

            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
            return;
        }

        try {
            int credits = Integer.parseInt(a_credits.getText().trim());
            int p_hours = Integer.parseInt(a_p_hours.getText().trim());
            int t_hours = Integer.parseInt(a_t_hours.getText().trim());

            if (a_c_name.getText().length() > 100) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Course name must be 100 characters or less.");
                return;
            }
            if (week.getText().length() > 10) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Week must be 10 characters or less.");
                return;
            }

            try (Connection conn = Database.Conn();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO course (Level, Semester, Department, Course_Id, Course_Name, Type, Lecturer_Id, Week, CreditStatus, Credits, P_Hours, T_Hours) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                ps.setString(1, a_level.getValue());
                ps.setString(2, a_semester.getValue());
                ps.setString(3, a_department.getValue());
                ps.setString(4, code.getText().trim());
                ps.setString(5, a_c_name.getText().trim());
                ps.setString(6, a_type.getValue());
                ps.setString(7, a_lec.getValue());
                ps.setString(8, week.getText().trim());
                ps.setString(9, a_status.getValue());
                ps.setInt(10, credits);
                ps.setInt(11, p_hours);
                ps.setInt(12, t_hours);

                int rowsInserted = ps.executeUpdate();
                if (rowsInserted > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Course added successfully!");
                    loadCourses();
                    clearForm();
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Credits, Practical Hours, and Theory Hours must be valid integers.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add course: " + e.getMessage());
        }
    }

    private void buttonUpdate() {
        if (code.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID is required to update.");
            return;
        }

        try {
            int credits = Integer.parseInt(a_credits.getText().trim());
            int p_hours = Integer.parseInt(a_p_hours.getText().trim());
            int t_hours = Integer.parseInt(a_t_hours.getText().trim());

            if (a_c_name.getText().length() > 100) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Course name must be 100 characters or less.");
                return;
            }
            if (week.getText().length() > 10) {
                showAlert(Alert.AlertType.WARNING, "Validation Error", "Week must be 10 characters or less.");
                return;
            }

            try (Connection conn = Database.Conn();
                 PreparedStatement ps = conn.prepareStatement(
                         "UPDATE course SET Course_Name=?, Type=?, Lecturer_Id=?, Week=?, CreditStatus=?, Credits=?, P_Hours=?, T_Hours=? " +
                                 "WHERE Course_Id=?")) {

                ps.setString(1, a_c_name.getText().trim());
                ps.setString(2, a_type.getValue());
                ps.setString(3, a_lec.getValue());
                ps.setString(4, week.getText().trim());
                ps.setString(5, a_status.getValue());
                ps.setInt(6, credits);
                ps.setInt(7, p_hours);
                ps.setInt(8, t_hours);
                ps.setString(9, code.getText().trim());

                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Course updated successfully!");
                    loadCourses();
                    clearForm();
                } else {
                    showAlert(Alert.AlertType.WARNING, "Warning", "No course found with the specified Course ID.");
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Credits, Practical Hours, and Theory Hours must be valid integers.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update course: " + e.getMessage());
        }
    }

    private void buttonDelete() {
        if (code.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Course ID is required to delete.");
            return;
        }

        try (Connection conn = Database.Conn();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM course WHERE Course_Id=?")) {

            ps.setString(1, code.getText().trim());

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Course deleted successfully!");
                loadCourses();
                clearForm();
            } else {
                showAlert(Alert.AlertType.WARNING, "Warning", "No course found with the specified Course ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete course: " + e.getMessage());
        }
    }

    private void clearForm() {
        a_level.setValue(null);
        a_semester.setValue(null);
        a_department.setValue(null);
        code.clear();
        a_c_name.clear();
        a_type.setValue(null);
        a_lec.setValue(null);
        week.clear();
        a_status.setValue(null);
        a_credits.clear();
        a_p_hours.clear();
        a_t_hours.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}