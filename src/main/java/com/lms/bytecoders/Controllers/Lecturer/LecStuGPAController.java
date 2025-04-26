package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.StudentGPA;
import com.lms.bytecoders.Models.StudentGrade;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LecStuGPAController extends BaseController implements Initializable {
    private String student_id;
    private double student_sgpa, student_cgpa;
    private ResultSet rs_;

    @FXML
    private Button backBtn;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField searchBox;

    @FXML
    private TableColumn<?, Double> stuCGPAColumn;

    @FXML
    private TableColumn<?, String> stuIdColumn;

    @FXML
    private TableColumn<?, Double> stuSGPAColumn;

    @FXML
    private TableView<StudentGPA> studentGPATable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<StudentGPA> getTableData() {
        ObservableList<StudentGPA> studentGPAData = FXCollections.observableArrayList();
        sql = "SELECT * FROM student";

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs_ = st.executeQuery(sql);

            while (rs_.next()) {
                student_id = rs_.getString("Student_Id");
                student_sgpa = calcSGPA(student_id, conn);
                student_cgpa = calcSGPA(student_id, conn);
                studentGPAData.add(new StudentGPA(student_id, student_sgpa, student_cgpa));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }

        return studentGPAData;
    }

    private void setTable() {
        stuCGPAColumn.setCellValueFactory(new PropertyValueFactory<>("student_cgpa"));
        stuIdColumn.setCellValueFactory(new PropertyValueFactory<>("student_id"));
        stuSGPAColumn.setCellValueFactory(new PropertyValueFactory<>("student_sgpa"));

        studentGPATable.setItems(getTableData());

        // filtered list
        FilteredList<StudentGPA> filteredData = new FilteredList<>(studentGPATable.getItems(), e -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(StudentGPA -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyWord = newValue.toLowerCase();

                if (StudentGPA.getStudent_id().toLowerCase().contains(keyWord)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<StudentGPA> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(studentGPATable.comparatorProperty());
        studentGPATable.setItems(sortedData);
    }

    @FXML
    public void navigateBackLecGPAGrades(ActionEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecGPAGrades.fxml");
    }

}

