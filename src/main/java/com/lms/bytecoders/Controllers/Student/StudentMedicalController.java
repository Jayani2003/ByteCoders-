package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.MedicalRecord;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.ResourceBundle;

public class StudentMedicalController implements Initializable {

    @FXML
    private TableColumn<MedicalRecord, String> mediCourseCodeTbl;
    @FXML
    private TableColumn<MedicalRecord, String> mediCourseName;
    @FXML
    private TableColumn<MedicalRecord, String> mediIDTbl;
    @FXML
    private TableColumn<MedicalRecord, String> mediSessionTbl;
    @FXML
    private TableColumn<MedicalRecord, String> mediStatusTbl;
    @FXML
    private TableView<MedicalRecord> mediTbl;
    @FXML
    private ComboBox<String> mediTypeDrop;
    @FXML
    private AnchorPane stuCourseMainPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> listlevel = FXCollections.observableArrayList("ALL", "APPROVED", "NOT APPROVED");
        mediTypeDrop.setItems(listlevel);
        mediTypeDrop.setValue("ALL"); // default selection
        setupTableColumns();
        loadMedicalData("ALL");

        mediTypeDrop.setOnAction(e -> {
            String selected = mediTypeDrop.getValue();
            loadMedicalData(selected);
        });
    }

    private void setupTableColumns() {
        mediIDTbl.setCellValueFactory(new PropertyValueFactory<>("medicalId"));
        mediCourseCodeTbl.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        mediCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        mediSessionTbl.setCellValueFactory(new PropertyValueFactory<>("type"));
        mediStatusTbl.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void loadMedicalData(String status) {
        ObservableList<MedicalRecord> medicalData = FXCollections.observableArrayList();

        String loggedInUserId = BaseController.getUserId();
        if (loggedInUserId == null || loggedInUserId.isEmpty()) {
            return;
        }

        String query = "SELECT m.MedicalRecord_Id, m.Course_Id, c.Course_Name, m.Type, m.Approval_Status " +
                "FROM medical m JOIN course c ON m.Course_Id = c.Course_Id " +
                "WHERE m.Student_Id = ? ";

        boolean isNotApproved = status.equals("NOT APPROVED");

        if (!status.equals("ALL")) {
            if (isNotApproved) {
                query += "AND m.Approval_Status IN ('UNAPPROVED', 'PENDING')";
            } else {
                query += "AND m.Approval_Status = ?";
            }
        }

        try (Connection conn = Database.Conn();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loggedInUserId);

            if (!status.equals("ALL") && !isNotApproved) {
                stmt.setString(2, status);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                medicalData.add(new MedicalRecord(
                        rs.getString("MedicalRecord_Id"),
                        rs.getString("Course_Id"),
                        rs.getString("Course_Name"),
                        rs.getString("Type"),
                        rs.getString("Approval_Status")
                ));
            }

            mediTbl.setItems(medicalData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}