package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Mark;
import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.CalcMarks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.UUID;

public class LecAddMarkController extends BaseController implements Initializable {

    @FXML
    private AnchorPane LecMainPane;

    @FXML
    private TableView<Mark> stuMarks;

    @FXML
    private TableColumn<Mark, String> stuId;

    @FXML
    private TableColumn<Mark, String> courseId;

    @FXML
    private TableColumn<Mark, Double> quiz1;

    @FXML
    private TableColumn<Mark, Double> quiz2;

    @FXML
    private TableColumn<Mark, Double> quiz3;

    @FXML
    private TableColumn<Mark, Double> quiz4;

    @FXML
    private TableColumn<Mark, Double> assignment1;

    @FXML
    private TableColumn<Mark, Double> assignment2;

    @FXML
    private TableColumn<Mark, Double> midTerm;

    @FXML
    private TableColumn<Mark, Double> finalTheory;

    @FXML
    private TableColumn<Mark, Double> finalPractical;

    @FXML
    private TableColumn<Mark, Integer> markRecordId;

    @FXML
    private TextField stuIdTxt;

    @FXML
    private TextField courseIdTxt;

    @FXML
    private TextField quiz1Txt;

    @FXML
    private TextField quiz2Txt;

    @FXML
    private TextField quiz3Txt;

    @FXML
    private TextField quiz4Txt;

    @FXML
    private TextField assignment1Txt;

    @FXML
    private TextField assignment2Txt;

    @FXML
    private TextField midTermTxt;

    @FXML
    private TextField finalTheoryTxt;

    @FXML
    private TextField finalPracticalTxt;

    @FXML
    private TextField stuIdSearch;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnClear;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
        setupButtonActions();
        stuMarks.setItems(getTableData());
    }

    private ObservableList<Mark> getTableData() {
        ObservableList<Mark> marks = FXCollections.observableArrayList();

        sql = """
                SELECT * FROM mark WHERE Lecturer_Id = ? ;
                """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            while (rs.next()) {
                marks.add(new Mark(
                        rs.getInt("MarkRecord_Id"),
                        rs.getString("Lecturer_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getDouble("Quiz_01"),
                        rs.getDouble("Quiz_02"),
                        rs.getDouble("Quiz_03"),
                        rs.getDouble("Quiz_04"),
                        rs.getDouble("Assignment_01"),
                        rs.getDouble("Assignment_02"),
                        rs.getDouble("Mid_Term"),
                        rs.getDouble("Final_Theory"),
                        rs.getDouble("Final_Practical")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load marks: " + e.getMessage());
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }

        return marks;
    }

    private void setTable() {
        stuId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        markRecordId.setCellValueFactory(new PropertyValueFactory<>("markRecordId"));
        courseId.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        quiz1.setCellValueFactory(new PropertyValueFactory<>("quiz01"));
        quiz2.setCellValueFactory(new PropertyValueFactory<>("quiz02"));
        quiz3.setCellValueFactory(new PropertyValueFactory<>("quiz03"));
        quiz4.setCellValueFactory(new PropertyValueFactory<>("quiz04"));
        assignment1.setCellValueFactory(new PropertyValueFactory<>("assignment01"));
        assignment2.setCellValueFactory(new PropertyValueFactory<>("assignment02"));
        midTerm.setCellValueFactory(new PropertyValueFactory<>("midTerm"));
        finalTheory.setCellValueFactory(new PropertyValueFactory<>("finalTheory"));
        finalPractical.setCellValueFactory(new PropertyValueFactory<>("finalPractical"));
    }

    private void setupButtonActions() {
        btnAdd.setOnAction(event -> addMark());
        btnUpdate.setOnAction(event -> updateMark());
        btnDelete.setOnAction(event -> deleteMark());
        btnSearch.setOnAction(event -> searchMarks());
        btnClear.setOnAction(event -> clearFields());

        stuMarks.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void addMark() {
        if (!validateInputs()) return;

        sql = """
            INSERT INTO mark (Lecturer_Id, Student_Id, Course_Id, Quiz_01, Quiz_02, Quiz_03, Quiz_04, 
                              Assignment_01, Assignment_02, Mid_Term, Final_Theory, Final_Practical)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
            """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            ps.setString(2, stuIdTxt.getText());
            ps.setString(3, courseIdTxt.getText());
            ps.setDouble(4, parseMark(quiz1Txt.getText()));
            ps.setDouble(5, parseMark(quiz2Txt.getText()));
            ps.setDouble(6, parseMark(quiz3Txt.getText()));
            ps.setDouble(7, parseMark(quiz4Txt.getText()));
            ps.setDouble(8, parseMark(assignment1Txt.getText()));
            ps.setDouble(9, parseMark(assignment2Txt.getText()));
            ps.setDouble(10, parseMark(midTermTxt.getText()));
            ps.setDouble(11, parseMark(finalTheoryTxt.getText()));
            ps.setDouble(12, parseMark(finalPracticalTxt.getText()));

            CalcMarks cm = new CalcMarks(
                    stuIdTxt.getText(),
                    courseIdTxt.getText(),
                    parseMark(quiz1Txt.getText()),
                    parseMark(quiz2Txt.getText()),
                    parseMark(quiz3Txt.getText()),
                    parseMark(quiz4Txt.getText()),
                    parseMark(assignment1Txt.getText()),
                    parseMark(assignment2Txt.getText()),
                    parseMark(midTermTxt.getText()),
                    parseMark(finalTheoryTxt.getText()),
                    parseMark(finalPracticalTxt.getText())
            );

            cm.addToFinalCAMarks(conn);

            ps.executeUpdate();
            stuMarks.setItems(getTableData());
            clearFields();

            showAlert("Success", "Mark added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to add mark: " + e.getMessage());
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
    }

    private void updateMark() {
        Mark selectedMark = stuMarks.getSelectionModel().getSelectedItem();
        if (selectedMark == null) {
            showAlert("Error", "Please select a mark to update.");
            return;
        }
        if (!validateInputs()) return;

        sql = """
                UPDATE mark
                SET Quiz_01 = ?, Quiz_02 = ?, Quiz_03 = ?, Quiz_04 = ?, Assignment_01 = ?, Assignment_02 = ?, 
                    Mid_Term = ?, Final_Theory = ?, Final_Practical = ?
                WHERE MarkRecord_Id = ?;
                """;

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, parseMark(quiz1Txt.getText()));
            ps.setDouble(2, parseMark(quiz2Txt.getText()));
            ps.setDouble(3, parseMark(quiz3Txt.getText()));
            ps.setDouble(4, parseMark(quiz4Txt.getText()));
            ps.setDouble(5, parseMark(assignment1Txt.getText()));
            ps.setDouble(6, parseMark(assignment2Txt.getText()));
            ps.setDouble(7, parseMark(midTermTxt.getText()));
            ps.setDouble(8, parseMark(finalTheoryTxt.getText()));
            ps.setDouble(9, parseMark(finalPracticalTxt.getText()));
            ps.setInt(10, selectedMark.getMarkRecordId());

            CalcMarks cm = new CalcMarks(
                    stuIdTxt.getText(),
                    courseIdTxt.getText(),
                    parseMark(quiz1Txt.getText()),
                    parseMark(quiz2Txt.getText()),
                    parseMark(quiz3Txt.getText()),
                    parseMark(quiz4Txt.getText()),
                    parseMark(assignment1Txt.getText()),
                    parseMark(assignment2Txt.getText()),
                    parseMark(midTermTxt.getText()),
                    parseMark(finalTheoryTxt.getText()),
                    parseMark(finalPracticalTxt.getText())
            );

            cm.updateFinalCAMarks(conn);

            ps.executeUpdate();
            stuMarks.setItems(getTableData());
            clearFields();

            showAlert("Success", "Mark updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to update mark: " + e.getMessage());
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
    }

    private void deleteMark() {
        Mark selectedMark = stuMarks.getSelectionModel().getSelectedItem();
        if (selectedMark == null) {
            showAlert("Error", "Please select a mark to delete.");
            return;
        }

        sql = "DELETE FROM mark WHERE MarkRecord_Id = ?;";

        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedMark.getMarkRecordId());
            ps.executeUpdate();
            stuMarks.setItems(getTableData());
            clearFields();
            showAlert("Success", "Mark deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to delete mark: " + e.getMessage());
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
    }

    private void searchMarks() {
        String searchId = stuIdSearch.getText().trim();
        if (searchId.isEmpty()) {
            stuMarks.setItems(getTableData());
            return;
        }

        sql = """
                SELECT * FROM mark
                WHERE Lecturer_Id = ? AND Student_Id = ?;
                """;

        ObservableList<Mark> filteredMarks = FXCollections.observableArrayList();
        try {
            conn = Database.Conn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, BaseController.getUserId());
            ps.setString(2, searchId);
            rs = ps.executeQuery();

            while (rs.next()) {
                filteredMarks.add(new Mark(
                        rs.getInt("MarkRecord_Id"),
                        rs.getString("Lecturer_Id"),
                        rs.getString("Student_Id"),
                        rs.getString("Course_Id"),
                        rs.getDouble("Quiz_01"),
                        rs.getDouble("Quiz_02"),
                        rs.getDouble("Quiz_03"),
                        rs.getDouble("Quiz_04"),
                        rs.getDouble("Assignment_01"),
                        rs.getDouble("Assignment_02"),
                        rs.getDouble("Mid_Term"),
                        rs.getDouble("Final_Theory"),
                        rs.getDouble("Final_Practical")
                ));
            }
            stuMarks.setItems(filteredMarks);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to search marks: " + e.getMessage());
        }finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error closing resources");
            }
        }
    }

    private void clearFields() {
        stuIdTxt.clear();
        courseIdTxt.clear();
        quiz1Txt.clear();
        quiz2Txt.clear();
        quiz3Txt.clear();
        quiz4Txt.clear();
        assignment1Txt.clear();
        assignment2Txt.clear();
        midTermTxt.clear();
        finalTheoryTxt.clear();
        finalPracticalTxt.clear();
        stuIdSearch.clear();
        stuMarks.getSelectionModel().clearSelection();
    }

    private void populateFields(Mark mark) {
        stuIdTxt.setText(mark.getStudentId());
        courseIdTxt.setText(mark.getCourseId());
        quiz1Txt.setText(mark.getQuiz01() != null ? String.valueOf(mark.getQuiz01()) : "");
        quiz2Txt.setText(mark.getQuiz02() != null ? String.valueOf(mark.getQuiz02()) : "");
        quiz3Txt.setText(mark.getQuiz03() != null ? String.valueOf(mark.getQuiz03()) : "");
        quiz4Txt.setText(mark.getQuiz04() != null ? String.valueOf(mark.getQuiz04()) : "");
        assignment1Txt.setText(mark.getAssignment01() != null ? String.valueOf(mark.getAssignment01()) : "");
        assignment2Txt.setText(mark.getAssignment02() != null ? String.valueOf(mark.getAssignment02()) : "");
        midTermTxt.setText(mark.getMidTerm() != null ? String.valueOf(mark.getMidTerm()) : "");
        finalTheoryTxt.setText(mark.getFinalTheory() != null ? String.valueOf(mark.getFinalTheory()) : "");
        finalPracticalTxt.setText(mark.getFinalPractical() != null ? String.valueOf(mark.getFinalPractical()) : "");
    }

    private boolean validateInputs() {
        if (stuIdTxt.getText().trim().isEmpty() || courseIdTxt.getText().trim().isEmpty()) {
            showAlert("Error", "Student ID and Course ID are required.");
            return false;
        }

        // Validate mark fields
        TextField[] markFields = {quiz1Txt, quiz2Txt, quiz3Txt, quiz4Txt, assignment1Txt, assignment2Txt,
                midTermTxt, finalTheoryTxt, finalPracticalTxt};
        for (TextField field : markFields) {
            String text = field.getText().trim();
            if (!text.isEmpty()) {
                try {
                    double value = Double.parseDouble(text);
                    if (value < 0 || value > 100) {
                        showAlert("Error", "Marks must be between 0 and 100.");
                        return false;
                    }
                } catch (NumberFormatException e) {
                    showAlert("Error", "All mark fields must be valid numbers.");
                    return false;
                }
            }
        }

        return true;
    }

    private double parseMark(String text) {
        return text.trim().isEmpty() ? 0.0 : Double.parseDouble(text);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}