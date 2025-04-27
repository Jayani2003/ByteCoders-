package com.lms.bytecoders.Controllers.Lecturer;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Department;
import com.lms.bytecoders.Models.Student;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LecStuDetailsController extends BaseController implements Initializable {

    private String stId, stTelephone, stLevel, stDepartment, stAddresses, stName;

    @FXML
    private TableColumn<?, String> stuAddressColumn;

    @FXML
    private TableColumn<?, String> stuDepartmentColumn;

    @FXML
    private TableColumn<?, String> stuIdColumn;

    @FXML
    private TableColumn<?, String> stuLevelColumn;

    @FXML
    private TableColumn<?, String> stuNameColumn;

    @FXML
    private TableView<Student> tableStuDetails;

    @FXML
    private TextField stuSearch;

    @FXML
    private TableColumn<?, String> stuTpColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<Student> getTableData() {
        ObservableList<Student> studentData = FXCollections.observableArrayList();
        sql = """
                    SELECT
                        u.User_Id,
                        u.First_Name,
                        u.Last_Name,
                        u.Telephone,
                        u.Address,
                        s.Level,
                        s.Department
                    FROM
                        user u
                    INNER JOIN
                        student s ON u.User_Id = s.Student_Id;
                """;

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                stId = rs.getString("User_Id");
                stTelephone = rs.getString("Telephone");
                stLevel = rs.getString("Level");
                stDepartment = rs.getString("Department");
                stAddresses = rs.getString("Address");
                stName = rs.getString("First_Name") + " " + rs.getString("Last_Name");

                studentData.add(new Student(stId, stName, stAddresses, stTelephone, stLevel, stDepartment));
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

        return studentData;
    }

    private void setTable() {
        stuAddressColumn.setCellValueFactory(new PropertyValueFactory<>("stu_address"));
        stuDepartmentColumn.setCellValueFactory(new PropertyValueFactory<>("stu_department"));
        stuIdColumn.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        stuLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stu_level"));
        stuNameColumn.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
        stuTpColumn.setCellValueFactory(new PropertyValueFactory<>("stu_telephone"));

        tableStuDetails.setItems(getTableData());

        // filtered list
        FilteredList<Student> filteredData = new FilteredList<>(tableStuDetails.getItems(), e -> true);
        stuSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Student -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String keyWord = newValue.toLowerCase();

                if (Student.getStu_id().toLowerCase().contains(keyWord)) {
                    return true;
                } else if (Student.getStu_name().toLowerCase().contains(keyWord)) {
                    return true;
                } else if (Student.getStu_department().toLowerCase().contains(keyWord)) {
                    return true;
                } else {
                    return false;
                }

            });
        });

        SortedList<Student> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableStuDetails.comparatorProperty());
        tableStuDetails.setItems(sortedData);
    }

}
