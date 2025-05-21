package com.lms.bytecoders.Controllers.Admin;

import com.lms.bytecoders.Models.User;
import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Controllers.Base.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.ResourceBundle;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdminUserController extends BaseController implements Initializable  {
    @FXML
    private ComboBox<String> combo;

    @FXML private TextField userId, fname, lname, email, p_no, address, password;

    @FXML
    private DatePicker dob;
    @FXML private Button buttonAdd, buttonUpdate, buttonDelete, buttonClear, uploadButton;
    @FXML private TableView<User> tableUsers;
    @FXML private TableColumn<User, String> t_userId, t_combo ,t_fname, t_lname, t_email, t_pnumber, t_dob, t_password, t_address;

    @FXML private Circle userImageCircle;

    private byte[] imageBytes;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tableUsers.setOnMouseClicked(e -> {
            User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                fillForm(selectedUser);
            }
        });


        ObservableList<String> list2 = FXCollections.observableArrayList("Student", "Technical Officer","Lecturer");
        combo.setItems(list2);


        setupTable();
        loadUsers();

        uploadButton.setOnAction(e -> uploadImage());
        buttonAdd.setOnAction(e -> addUser());
        buttonUpdate.setOnAction(e -> updateUser());
        buttonDelete.setOnAction(e -> deleteUser());
        buttonClear.setOnAction(e -> clearForm());

    }

    private void fillForm(User user) {
        userId.setText(user.getUserId());
        fname.setText(user.getFirstName());
        lname.setText(user.getLastName());
        email.setText(user.getEmail());
        p_no.setText(user.getTelephone());
        address.setText(user.getAddress());
        dob.setValue(user.getDob());
        password.setText(user.getPassword());

        combo.setValue(user.getUserType());

        userImageCircle.setFill(null);
        imageBytes = null;



    }
    public int calculateAge(LocalDate dob) {
        return Period.between(dob, LocalDate.now()).getYears();
    }


    private void setupTable() {
        t_userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        t_combo.setCellValueFactory(new PropertyValueFactory<>("userType"));
        t_fname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        t_lname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        t_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        t_pnumber.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        t_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        t_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        t_address.setCellValueFactory(new PropertyValueFactory<>("address"));
    }


    private void loadUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        userList.clear();

        String sql = "SELECT User_Id, First_Name, Last_Name, DOB, Email, Telephone, Address, Password, Role FROM user";

        try {
            conn = Database.Conn();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                userList.add(new User(
                        rs.getString("User_Id"),
                        rs.getString("First_Name"),
                        rs.getString("Last_Name"),
                        rs.getString("Email"),
                        rs.getString("Telephone"),
                        rs.getString("Role"),
                        rs.getDate("DOB").toLocalDate(),
                        rs.getString("Password"),
                        rs.getString("Address"),

                        calculateAge(rs.getDate("DOB").toLocalDate())
                ));
            }

            tableUsers.setItems(userList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





private void addUser() {
    // Validate input fields first
    if (userId.getText().isEmpty() ||
            fname.getText().isEmpty() ||
            lname.getText().isEmpty() ||
            email.getText().isEmpty() ||
            p_no.getText().isEmpty() ||
            address.getText().isEmpty() ||
            password.getText().isEmpty() ||
            dob.getValue() == null ||
            combo.getValue() == null ||

            imageBytes == null) {

        showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields and upload an image.");
        return; // Stop the method if validation fails
    }

    try {
        conn = Database.Conn();
        String sql = "INSERT INTO user (User_Id, First_Name, Last_Name, DOB, Telephone, Address, Email, Password, Age, User_Image, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        int age = LocalDate.now().getYear() - dob.getValue().getYear();

        ps.setString(1, userId.getText());
        ps.setString(2, fname.getText());
        ps.setString(3, lname.getText());
        ps.setDate(4, Date.valueOf(dob.getValue()));
        ps.setString(5, p_no.getText());
        ps.setString(6, address.getText());
        ps.setString(7, email.getText());
        ps.setString(8, hashPassword(password.getText()));
        ps.setInt(9, age);
        ps.setBytes(10, imageBytes);

        ps.setString(11, combo.getValue());

        int rowsInserted = ps.executeUpdate();
        if (rowsInserted > 0) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "User added successfully!");
        }
        loadUsers();
        clearForm();
    } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user.");
    }
}


    private void updateUser() {
        try {
            conn = Database.Conn();

            String sql = "UPDATE user SET First_Name=?, Last_Name=?, DOB=?, Telephone=?, Address=?, Email=?, Password=?, Age=?, User_Image=?, Role=? WHERE User_Id=?";

            PreparedStatement ps = conn.prepareStatement(sql);


            int age = LocalDate.now().getYear() - dob.getValue().getYear();


            ps.setString(1, fname.getText());
            ps.setString(2, lname.getText());
            ps.setDate(3, Date.valueOf(dob.getValue()));
            ps.setString(4, p_no.getText());
            ps.setString(5, address.getText());
            ps.setString(6, email.getText());
            ps.setString(7, hashPassword(password.getText()));
            ps.setInt(8, age);
            ps.setBytes(9, imageBytes);

            ps.setString(10, combo.getValue());
            ps.setString(11, userId.getText());



            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully!");
            }
            loadUsers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user.");
        }
    }
    private void deleteUser() {
        try {
            conn = Database.Conn();
            String sql = "DELETE FROM user WHERE User_Id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userId.getText());




            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully!");
            }
            loadUsers();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user.");
        }
    }

    @FXML
    private void uploadImage() {
        setProfilePicUpload(userImageCircle);
        try {
            if (is != null) imageBytes = is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void clearForm() {
        userId.clear();
        fname.clear();
        lname.clear();
        email.clear();
        p_no.clear();
        address.clear();
        dob.setValue(null);
        password.clear();
        combo.setValue(null);

        userImageCircle.setFill(null);
        imageBytes = null;
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }



    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

}
