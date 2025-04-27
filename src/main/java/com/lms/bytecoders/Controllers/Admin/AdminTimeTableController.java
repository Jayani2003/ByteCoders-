package com.lms.bytecoders.Controllers.Admin;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminTimeTableController extends BaseController implements Initializable {

    @FXML
    private ComboBox<String> combolevel;
    @FXML
    private ComboBox<String> combosemester;
    @FXML
    private ComboBox<String> combodepartment;
    @FXML
    private TextField fileTextField;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> listlevel = FXCollections.observableArrayList("Level 1", "Level 2", "Level 3", "Level 4");
        combolevel.setItems(listlevel);

        ObservableList<String> listsemester = FXCollections.observableArrayList("Semester 1", "Semester 2");
        combosemester.setItems(listsemester);

        ObservableList<String> listdepartment = FXCollections.observableArrayList("ICT", "ET", "BST");
        combodepartment.setItems(listdepartment);
    }


    public void addTimeTableToDB() {
        try {
            String level = combolevel.getValue();
            String semester = combosemester.getValue();
            String department = combodepartment.getValue();
            String imageUrl = fileTextField.getText();

            if (level == null || semester == null || department == null || imageUrl == null || imageUrl.isEmpty()) {
                System.out.println("All fields must be filled!");
                return;
            }

            String datePosted = java.time.LocalDate.now().toString();
            String sql = "INSERT INTO time_table (Date_Posted, Department, Level, Semester, Timetable) VALUES (?, ?, ?, ?, ?)";

            conn = Database.Conn();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, datePosted);
            ps.setString(2, department);
            ps.setString(3, level);
            ps.setString(4, semester);
            ps.setString(5, imageUrl);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Timetable added successfully!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Timetable added successfully!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddButton() {
        addTimeTableToDB();
        clearFields();
    }


    public void deleteTimeTableFromDB() {
        try {
            String level = combolevel.getValue();
            String semester = combosemester.getValue();
            String department = combodepartment.getValue();

            if (level == null || semester == null || department == null) {
                System.out.println("All fields must be filled!");
                return;
            }

            String sql = "DELETE FROM time_table WHERE Level = ? AND Semester = ? AND Department = ?";
            conn = Database.Conn();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, level);
            ps.setString(2, semester);
            ps.setString(3, department);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Timetable deleted successfully!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Timetable deleted successfully!");
                alert.showAndWait();
            } else {
                System.out.println("No matching timetable found to delete.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleDeleteButton() {
        deleteTimeTableFromDB();
        clearFields();
    }


    public void updateTimeTableInDB() {
        try {
            String level = combolevel.getValue();
            String semester = combosemester.getValue();
            String department = combodepartment.getValue();
            String imageUrl = fileTextField.getText();

            if (level == null || semester == null || department == null || imageUrl == null || imageUrl.isEmpty()) {
                System.out.println("All fields must be filled!");
                return;
            }

            String sql = "UPDATE time_table SET Timetable = ? WHERE Level = ? AND Semester = ? AND Department = ?";
            conn = Database.Conn();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, imageUrl);      // new timetable link
            ps.setString(2, level);         // identifying level
            ps.setString(3, semester);      // identifying semester
            ps.setString(4, department);    // identifying department

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Timetable updated successfully!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Timetable updated successfully!");
                alert.showAndWait();
            } else {
                System.out.println("No matching timetable found to update.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleUpdateButton() {
        updateTimeTableInDB();
        clearFields();
    }

    @FXML
    private void handleFileButton() {
        TextInputDialog dialog = new TextInputDialog("https://example.com/timetable.pdf");
        dialog.setTitle("Insert Timetable Link");
        dialog.setHeaderText("Enter the link to the timetable file:");
        dialog.setContentText("Link:");

        dialog.showAndWait().ifPresent(link -> fileTextField.setText(link));
    }



    private void clearFields() {
        combolevel.setValue(null);
        combosemester.setValue(null);
        combodepartment.setValue(null);
        fileTextField.clear();
    }
}
