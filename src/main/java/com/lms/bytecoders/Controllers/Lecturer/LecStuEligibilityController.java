package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.StuEligible;
import com.lms.bytecoders.Models.StudentGPA;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LecStuEligibilityController extends BaseController implements Initializable {

    @FXML
    private TableColumn<StuEligible, String> attendance;

    @FXML
    private TableColumn<StuEligible, String> ca_marks;

    @FXML
    private Label courseCode;

    @FXML
    private TableView<StuEligible> stuEligibilityTable;
    @FXML
    private TableColumn<StuEligible, String> course_code;

    @FXML
    private TableColumn<StuEligible, String> stu_id;

    @FXML
    private Label studentId;

    @FXML
    private TextField searchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<StuEligible> getTableData() {
        ObservableList<StuEligible> stuEligibilityData = FXCollections.observableArrayList();
        sql = "SELECT * FROM ca_final_marks";

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);


            while (rs.next()) {
                String sid = rs.getString("Student_Id");
                String cid = rs.getString("Course_Id");
                stuEligibilityData.add(new StuEligible(
                        sid,
                        cid,
                        checkCAEligibility(cid, rs.getDouble("CA_Marks")),
                        getAttendanceWithMedical(sid, cid, conn) >= 80 ? "Eligible" : "Not Eligible"
                ));
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

        return stuEligibilityData;
    }

    private void setTable() {
        stu_id.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        course_code.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        ca_marks.setCellValueFactory(new PropertyValueFactory<>("caMarks"));
        attendance.setCellValueFactory(new PropertyValueFactory<>("Attendance"));

        stuEligibilityTable.setItems(getTableData());

        // filtered list
        FilteredList<StuEligible> filteredData = new FilteredList<>(stuEligibilityTable.getItems(), e -> true);
        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(StuEligible -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyWord = newValue.toLowerCase();

                if (StuEligible.getStudentId().toLowerCase().contains(keyWord)) {
                    return true;
                }else if (StuEligible.getCourseId().toLowerCase().contains(keyWord)) {
                    return true;
                }else {
                    return false;
                }
            });
        });

        SortedList<StuEligible> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(stuEligibilityTable.comparatorProperty());
        stuEligibilityTable.setItems(sortedData);
    }

}
