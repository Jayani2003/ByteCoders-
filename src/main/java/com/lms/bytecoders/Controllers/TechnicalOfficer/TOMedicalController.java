package com.lms.bytecoders.Controllers.TechnicalOfficer;

import com.lms.bytecoders.Models.MedicalRecord;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TOMedicalController implements Initializable {

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private TextField tec_stuId;

    @FXML
    private TextField tec_courseCode;

    @FXML
    private DatePicker tecDate;

    @FXML
    private TableView<MedicalRecord> medicalTable;

    @FXML
    private TableColumn<MedicalRecord, String> med_id;

    @FXML
    private TableColumn<MedicalRecord, String> stu_id;

    @FXML
    private TableColumn<MedicalRecord, String> c_code;

    @FXML
    private TableColumn<MedicalRecord, String> l_type;

    @FXML
    private TableColumn<MedicalRecord, LocalDate> t_date;

    @FXML
    private TableColumn<MedicalRecord, String> t_approval;

    private ObservableList<MedicalRecord> medicalList = FXCollections.observableArrayList();

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> lectureTypeList = FXCollections.observableArrayList("THEORY", "PRACTICAL");
        comboBox1.setItems(lectureTypeList);

        ObservableList<String> approvalList = FXCollections.observableArrayList("APPROVED", "UNAPPROVED");
        comboBox2.setItems(approvalList);

        setupTable();
        loadMedicalRecords();

        medicalTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                loadSelectedRecord();
            }
        });

        tec_stuId.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                loadMedicalRecords();
            }
        });

    }

    private void setupTable() {
        med_id.setCellValueFactory(new PropertyValueFactory<>("medicalId"));
        stu_id.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        c_code.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        l_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        t_approval.setCellValueFactory(new PropertyValueFactory<>("status"));
        t_date.setCellValueFactory(new PropertyValueFactory<>("submissionDate"));

    }

    private void loadMedicalRecords() {
        medicalList.clear();
        String query = "SELECT * FROM medical";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {


                MedicalRecord record = new MedicalRecord(
                        rs.getString("MedicalRecord_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getString("Type"),
                        rs.getString("Approval_Status"),
                        rs.getDate("Submission_Date").toLocalDate()

                );
                medicalList.add(record);
            }
            medicalTable.setItems(medicalList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void addMedicalRecord() {

        String medicalId = generateMedicalId();
        String studentId = tec_stuId.getText();
        String courseId = tec_courseCode.getText();
        String type = comboBox1.getValue();
        String approvalStatus = comboBox2.getValue();
        LocalDate submissionDate = tecDate.getValue();

        if (studentId.isEmpty() || courseId.isEmpty() || type == null || approvalStatus == null || submissionDate == null) {

            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please fill in all the fields.");
            return;
        }

        String insertQuery = "INSERT INTO medical (MedicalRecord_Id, Student_Id, Course_Id, Approval_Status, Submission_Date, Type) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(insertQuery);


            ps.setString(1, medicalId);
            ps.setString(2, studentId);
            ps.setString(3, courseId);
            ps.setString(4, approvalStatus);
            ps.setDate(5, java.sql.Date.valueOf(submissionDate));
            ps.setString(6, type);

            int result = ps.executeUpdate();

            if (result > 0) {
                loadMedicalRecords();
                clearMedicalRecord();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record added successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add medical record.");
        }
    }


    @FXML
    private void updateMedicalRecord() {
        MedicalRecord selected = medicalTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a record to update.");
            return;
        }

        String updateQuery = "UPDATE medical SET Student_Id=?, Course_Id=?, Type=?, Approval_Status=?, Submission_Date=? WHERE MedicalRecord_Id=?";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(updateQuery);

            ps.setString(1, tec_stuId.getText());
            ps.setString(2, tec_courseCode.getText());
            ps.setString(3, comboBox1.getValue());
            ps.setString(4, comboBox2.getValue());
            ps.setDate(5, java.sql.Date.valueOf(tecDate.getValue()));
            ps.setString(6, selected.getMedicalId());

            int result = ps.executeUpdate();

            if (result > 0) {
                loadMedicalRecords();
                clearMedicalRecord();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update medical record.");
        }
    }

    @FXML
    private void deleteMedicalRecord() {
        MedicalRecord selected = medicalTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a record to delete.");
            return;
        }

        String deleteQuery = "DELETE FROM medical WHERE MedicalRecord_Id=?";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(deleteQuery);
            ps.setString(1, selected.getMedicalId());

            int result = ps.executeUpdate();

            if (result > 0) {
                loadMedicalRecords();
                clearMedicalRecord();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Medical record deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete medical record.");
        }
    }

    @FXML
    private void searchStudentId() {
        String searchId = tec_stuId.getText();

        if (searchId.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please enter a student ID to search.");
            return;
        }

        String searchQuery = "SELECT * FROM medical WHERE Student_Id=?";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(searchQuery);
            ps.setString(1, searchId);
            rs = ps.executeQuery();

            medicalList.clear();

            while (rs.next()) {
                MedicalRecord record = new MedicalRecord(
                        rs.getString("MedicalRecord_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getString("Type"),
                        rs.getString("Approval_Status"),
                        rs.getDate("Submission_Date").toLocalDate()
                );
                medicalList.add(record);
            }
            medicalTable.setItems(medicalList);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to search medical records.");
        }
    }

    @FXML
    private void clearMedicalRecord() {
        tec_stuId.clear();
        tec_courseCode.clear();
        comboBox1.getSelectionModel().clearSelection();
        comboBox2.getSelectionModel().clearSelection();
        tecDate.setValue(null);
        loadMedicalRecords();
    }

    private void loadSelectedRecord() {
        MedicalRecord selected = medicalTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            tec_stuId.setText(selected.getCourseId());
            tec_courseCode.setText(selected.getCourseName());
            comboBox1.setValue(selected.getType());
            comboBox2.setValue(selected.getStatus());

        }
    }

    private String generateMedicalId() {
        String prefix = "MD";
        String query = "SELECT MedicalRecord_Id FROM medical ORDER BY MedicalRecord_Id DESC LIMIT 1";
        String newId = "";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                String lastId = rs.getString("MedicalRecord_Id");
                int idNum = Integer.parseInt(lastId.substring(2));
                idNum++;
                newId = String.format(prefix + "%04d", idNum);
            } else {
                newId = prefix + "0001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            newId = prefix + "0001";
        }

        return newId;
    }


    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public void setT_date(TableColumn<MedicalRecord, LocalDate> t_date) {
        this.t_date = t_date;
    }



}