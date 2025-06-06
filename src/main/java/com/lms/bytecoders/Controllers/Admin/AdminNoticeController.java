package com.lms.bytecoders.Controllers.Admin;

import com.lms.bytecoders.Controllers.Base.BaseController;
import com.lms.bytecoders.Models.Notice;
import com.lms.bytecoders.Services.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminNoticeController extends BaseController implements Initializable {

    @FXML
    private TableView<Notice> to_Table;

    @FXML
    private TableColumn<Notice, String> to_No;

    @FXML
    private TableColumn<Notice, String> to_Title;

    @FXML
    private TableColumn<Notice, String> to_Description;

    @FXML
    private TableColumn<Notice, LocalDate> to_Date;

    @FXML
    private Button buttonAdd;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonDelete;

    private ObservableList<Notice> noticeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupColumns();
        loadNoticesFromDB();
    }

    private void setupColumns() {
        to_No.setCellValueFactory(new PropertyValueFactory<>("noticeId"));
        to_Title.setCellValueFactory(new PropertyValueFactory<>("title"));
        to_Description.setCellValueFactory(new PropertyValueFactory<>("description"));
        to_Date.setCellValueFactory(new PropertyValueFactory<>("datePosted"));
    }

    private void loadNoticesFromDB() {
        noticeList.clear();
        try (Connection conn = Database.Conn()) {
            String sql = "SELECT * FROM notice ORDER BY Date_Posted DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int index = 1;
            while (rs.next()) {
                String id = String.valueOf(index++);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                Date datePosted = rs.getDate("Date_Posted");

                noticeList.add(new Notice(title, id, description, datePosted));
            }
            to_Table.setItems(noticeList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAddClicked() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Notice");
        dialog.setHeaderText("Enter title, description (comma separated):");
        dialog.setContentText("Input:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String[] data = input.split(",", 2);
            if (data.length == 2) {
                String title = data[0].trim();
                String description = data[1].trim();

                if (title.isEmpty() || description.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Invalid input", "Title or description cannot be empty.");
                    return;
                }

                try (Connection conn = Database.Conn()) {
                    if (conn == null) {
                        System.out.println("Failed to establish connection.");
                        showAlert(Alert.AlertType.ERROR, "Connection Error", "Unable to connect to the database.");
                        return;
                    }

                    String sql = "INSERT INTO notice (Title, Description, Date_Posted) VALUES (?, ?, ?)";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, title);
                    ps.setString(2, description);
                    ps.setDate(3, Date.valueOf(LocalDate.now()));
                    int rowsAffected = ps.executeUpdate();


                    if (rowsAffected > 0) {
                        System.out.println(rowsAffected + " row(s) inserted.");
                        loadNoticesFromDB();
                    } else {
                        System.out.println("No rows affected.");
                        showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add notice. Please try again later.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add notice. Please try again later.");
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Invalid input", "Please use the format: title,description");
            }
        });
    }



    @FXML
    private void onUpdateClicked() {
        Notice selected = to_Table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a notice to update.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog(selected.getTitle() + "," + selected.getDescription());
        dialog.setTitle("Update Notice");
        dialog.setHeaderText("Edit title and description (comma separated):");
        dialog.setContentText("Input:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String[] data = input.split(",", 2);
            if (data.length == 2) {
                try (Connection conn = Database.Conn()) {
                    String sql = "UPDATE notice SET Title = ?, Description = ? WHERE Title = ? AND Description = ?";
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, data[0].trim());
                    ps.setString(2, data[1].trim());
                    ps.setString(3, selected.getTitle());
                    ps.setString(4, selected.getDescription());
                    ps.executeUpdate();
                    loadNoticesFromDB();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.WARNING, "Invalid input", "Please use the format: title,description");
            }
        });
    }

    @FXML
    private void onDeleteClicked() {
        Notice selected = to_Table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a notice to delete.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this notice?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Delete");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            try (Connection conn = Database.Conn()) {
                String sql = "DELETE FROM notice WHERE Title = ? AND Description = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, selected.getTitle());
                ps.setString(2, selected.getDescription());
                ps.executeUpdate();
                loadNoticesFromDB();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Button getButtonAdd() {
        return buttonAdd;
    }

    public void setButtonAdd(Button buttonAdd) {
        this.buttonAdd = buttonAdd;
    }

    public Button getButtonUpdate() {
        return buttonUpdate;
    }

    public void setButtonUpdate(Button buttonUpdate) {
        this.buttonUpdate = buttonUpdate;
    }

    public Button getButtonDelete() {
        return buttonDelete;
    }

    public void setButtonDelete(Button buttonDelete) {
        this.buttonDelete = buttonDelete;
    }
}
