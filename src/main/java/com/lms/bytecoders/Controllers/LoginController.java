package com.lms.bytecoders.Controllers;

import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private String username, password, db_uid, db_hash, sql;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;


    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField userNameInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleLogin(ActionEvent actionEvent) {
        username = userNameInput.getText().trim();
        password = passwordInput.getText().trim();
        conn = Database.Conn();
        sql = "SELECT User_Id, Password FROM user WHERE Email=?";

        try {

            if (username.equals("") || password.equals("")) {
                popUpErrorMessage("Username or Password are Required");
            } else {


                ps = conn.prepareStatement(sql);
                ps.setString(1, username);

                rs = ps.executeQuery();
                if (rs.next()) {

                    db_uid = rs.getString("User_Id");
                    db_hash = rs.getString("Password");

                    if (PasswordUtils.verifyPassword(password, db_hash)) {
                        System.out.println("Successfully logged in");
                    }else {
                        popUpErrorMessage("Invalid Password or Username");
                    }

                }else {
                    popUpErrorMessage("User Not Found");
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Error in closing the Connection..." + e.getMessage());
            }
        }


    }

    private void onLogin() {
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();
    }

    private void Dashboard(String path) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            Stage stage = new Stage();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Dashboard");
            stage.show();
        } catch (Exception e) {
            System.out.println("error : " + e + "\nerror-message: " + e.getMessage());
        }
    }

    private void popUpErrorMessage(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(error);
        alert.showAndWait();
    }

}
