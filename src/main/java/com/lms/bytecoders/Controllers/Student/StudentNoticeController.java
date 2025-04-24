package com.lms.bytecoders.Controllers.Student;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Notice;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

public class StudentNoticeController extends BaseController implements Initializable {

    @FXML
    private TableColumn<?, Date> dateColumn;

    @FXML
    private TableColumn<?, String> descriptionColumn;

    @FXML
    private TableColumn<?, String> noticeIdColumn;

    @FXML
    private TableView<Notice> noticeTable;

    @FXML
    private TableColumn<?, String> titleColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTable();
    }

    private ObservableList<Notice> getTableData() {
        ObservableList<Notice> notices = FXCollections.observableArrayList();
        sql = "SELECT * FROM notice";

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                notices.add(new Notice(rs.getString("Notice_Id"), rs.getString("Title"), rs.getString("Description"), rs.getDate("Date_Posted")));
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

        return notices;
    }

    private void setTable() {
        noticeIdColumn.setCellValueFactory(new PropertyValueFactory<>("noticeId"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datePosted"));

        noticeTable.setItems(getTableData());
    }
}
