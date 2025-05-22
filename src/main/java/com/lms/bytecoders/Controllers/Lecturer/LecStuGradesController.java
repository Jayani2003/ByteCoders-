package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Student;
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

public class LecStuGradesController extends BaseController implements Initializable {

    private String student_id, course_code, student_grade;
    private ResultSet rs_;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<?, String> courseCodeColumn;

    @FXML
    private TextField searchBox;

    @FXML
    private TableColumn<?, String> stuGradeColumn;

    @FXML
    private TableColumn<?, String> stuIdColumn;

    @FXML
    private TableView<StudentGrade> studentGradesTable;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<StudentGrade> getTableData() {
        ObservableList<StudentGrade> studentGradeData = FXCollections.observableArrayList();
        sql = """
            SELECT
                c.Course_Id,
                m.FULL_Marks,
                m.Student_Id,
                c.Credits
            FROM ca_final_marks m
            JOIN course c ON m.Course_Id = c.Course_Id
        """;

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs_ = st.executeQuery(sql);

            while (rs_.next()) {
                student_id = rs_.getString("Student_Id");
                course_code = rs_.getString("Course_Id");
                student_grade = getGrade(rs_.getDouble("FULL_Marks"));
                studentGradeData.add(new StudentGrade(student_id, course_code, student_grade));
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

        return studentGradeData;
    }

    private void setTable() {
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("course_code"));
        stuGradeColumn.setCellValueFactory(new PropertyValueFactory<>("student_grade"));
        stuIdColumn.setCellValueFactory(new PropertyValueFactory<>("student_id"));

        studentGradesTable.setItems(getTableData());

        // filtered list
        FilteredList<StudentGrade> filteredData = new FilteredList<>(studentGradesTable.getItems(), e -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(StudentGrade -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyWord = newValue.toLowerCase();

                if (StudentGrade.getStudent_id().toLowerCase().contains(keyWord)) {
                    return true;
                } else if (StudentGrade.getCourse_code().toLowerCase().contains(keyWord)) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        SortedList<StudentGrade> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(studentGradesTable.comparatorProperty());
        studentGradesTable.setItems(sortedData);
    }

    @FXML
    public void navigateBackLecGPAGrades(ActionEvent event) {
        navigate(MainPane, "/Fxml/Lecturer/LecGPAGrades.fxml");
    }

}
