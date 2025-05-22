package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.CourseMaterial;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class LecAddMaterialController extends BaseController implements Initializable {

    @FXML
    private AnchorPane LecMainPane;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnUpdate;

    @FXML
    private TableColumn<CourseMaterial, String> courseIdCol;

    @FXML
    private TableColumn<CourseMaterial, String> descriptionCol;

    @FXML
    private TextArea descriptionTxt;

    @FXML
    private TableColumn<CourseMaterial, String> lecturerIdCol;

    @FXML
    private TableColumn<CourseMaterial, Hyperlink> linkCol;

    @FXML
    private TextField linkTxt;

    @FXML
    private TableColumn<CourseMaterial, Integer> materialIdCol;

    @FXML
    private TableView<CourseMaterial> materialsTable;

    @FXML
    private TableColumn<CourseMaterial, String> titleCol;

    @FXML
    private TextField titleTxt;

    @FXML
    private TableColumn<CourseMaterial, Date> uploadDateCol;

    private String courseId_;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public void setCourseId(String courseId) {
        this.courseId_ = courseId;
        materialsTable.setItems(getTableData());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
        setupButtonActions();
    }

    private ObservableList<CourseMaterial> getTableData() {
        ObservableList<CourseMaterial> materials = FXCollections.observableArrayList();
        String sql = "SELECT * FROM course_materials WHERE Lecturer_Id = ? AND Course_Id = ?;";
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            ps.setString(2, courseId_); // Default to all if courseId_ is null
            rs = ps.executeQuery();
            while (rs.next()) {
                Hyperlink link = new Hyperlink(rs.getString("Link"));
                link.setOnAction(event -> {
                    try {
                        Desktop.getDesktop().browse(new URI(link.getText()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        showAlert("Error", "Failed to open link: " + e.getMessage());
                    }
                });
                materials.add(new CourseMaterial(
                        rs.getInt("Material_Id"),
                        rs.getString("Course_Id"),
                        rs.getString("Lecturer_Id"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        link,
                        rs.getDate("Upload_Date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load materials: " + e.getMessage());
        } finally {
            closeResources();
        }
        return materials;
    }

    private void setTable() {
        materialIdCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        lecturerIdCol.setCellValueFactory(new PropertyValueFactory<>("lecturerId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        linkCol.setCellValueFactory(new PropertyValueFactory<>("link"));
        uploadDateCol.setCellValueFactory(new PropertyValueFactory<>("uploadDate"));
    }

    private void setupButtonActions() {
        btnAdd.setOnAction(event -> addMaterial());
        btnUpdate.setOnAction(event -> updateMaterial());
        btnDelete.setOnAction(event -> deleteMaterial());
        btnClear.setOnAction(event -> clearFields());

        materialsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void addMaterial() {
        if (!validateInputs()) return;

        String title = titleTxt.getText().trim();
        String description = descriptionTxt.getText().trim();
        String linkUrl = linkTxt.getText().trim();
        LocalDate currentDate = LocalDate.now(); // Current date: May 17, 2025, 03:10 PM +0530
        Hyperlink link = new Hyperlink(linkUrl);

        if (courseId_ == null) {
            showAlert("Error", "Course ID is not set.");
            return;
        }

        String sql = """
            INSERT INTO course_materials (Course_Id, Lecturer_Id, Title, Description, Link, Upload_Date)
            VALUES (?, ?, ?, ?, ?, ?);
            """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseId_);
            ps.setString(2, BaseController.getUserId());
            ps.setString(3, title);
            ps.setString(4, description);
            ps.setString(5, linkUrl);
            ps.setDate(6, java.sql.Date.valueOf(currentDate));
            ps.executeUpdate();
            materialsTable.setItems(getTableData());
            clearFields();
            showAlert("Success", "Material added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add material: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void updateMaterial() {
        CourseMaterial selectedMaterial = materialsTable.getSelectionModel().getSelectedItem();
        if (selectedMaterial == null) {
            showAlert("Error", "Please select a material to update.");
            return;
        }
        if (!validateInputs()) return;

        String title = titleTxt.getText().trim();
        String description = descriptionTxt.getText().trim();
        String linkUrl = linkTxt.getText().trim();
        LocalDate currentDate = LocalDate.now();

        String sql = """
            UPDATE course_materials
            SET Title = ?, Description = ?, Link = ?, Upload_Date = ?
            WHERE Material_Id = ?;
            """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, linkUrl);
            ps.setDate(4, java.sql.Date.valueOf(currentDate));
            ps.setInt(5, selectedMaterial.getMaterialId());
            ps.executeUpdate();
            materialsTable.setItems(getTableData());
            clearFields();
            showAlert("Success", "Material updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update material: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void deleteMaterial() {
        CourseMaterial selectedMaterial = materialsTable.getSelectionModel().getSelectedItem();
        if (selectedMaterial == null) {
            showAlert("Error", "Please select a material to delete.");
            return;
        }

        String sql = "DELETE FROM course_materials WHERE Material_Id = ?;";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedMaterial.getMaterialId());
            ps.executeUpdate();
            materialsTable.setItems(getTableData());
            clearFields();
            showAlert("Success", "Material deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete material: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void clearFields() {
        titleTxt.clear();
        descriptionTxt.clear();
        linkTxt.clear();
        materialsTable.getSelectionModel().clearSelection();
    }

    private void populateFields(CourseMaterial material) {
        titleTxt.setText(material.getTitle());
        descriptionTxt.setText(material.getDescription());
        linkTxt.setText(material.getLink().getText());
    }

    private boolean validateInputs() {
        if (titleTxt.getText().trim().isEmpty() || linkTxt.getText().trim().isEmpty() || descriptionTxt.getText().trim().isEmpty()) {
            showAlert("Error", "Title, Link, and Description are required.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}