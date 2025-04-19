package com.lms.bytecoders.Controllers.Base;

import com.lms.bytecoders.Services.Database;
import com.lms.bytecoders.Utils.SceneHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class BaseController {
    protected Parent root;
    private static String userId;

    public static String getFname() {
        return Fname;
    }

    public static void setFname(String fname) {
        Fname = fname;
    }

    private static String Fname;
    protected int id;
    protected String sql, fname, lname;
    protected Connection conn;
    protected Statement st;
    protected PreparedStatement ps;
    protected ResultSet rs;
    protected InputStream is;
    protected FileOutputStream fos;
    protected BufferedImage bi;

    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }


    @FXML
    public void navigate(BorderPane pane, String path) {
        try {
            root = FXMLLoader.load(getClass().getResource(path));
            pane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLogin(Node ob) throws IOException {
        FXMLLoader loader = SceneHandler.createLoader("/Fxml/Login.fxml");
        root = loader.load();
        SceneHandler.switchScene(ob, root, "Login");
    }

    public void setProfilePic(Circle proPic, byte[] imgData) {
        Image image;
        try {
            image = new Image(new ByteArrayInputStream(imgData));
        } catch (Exception e) {
            e.printStackTrace();
            image = new Image(getClass().getResource("/Images/temp.jpg").toExternalForm());
        }
        proPic.setFill(new ImagePattern(image));
    }

    public void getDashBoardDataFromDB(Circle proPic, Label Uname) {
        try {
            conn = Database.Conn();
            sql = "SELECT First_Name, Last_Name, User_Image FROM user WHERE User_Id=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, BaseController.getUserId());
            rs = ps.executeQuery();

            if (rs.next()) {
                try {
                    fname = rs.getString("First_Name");
                    lname = rs.getString("Last_Name");
                    BaseController.setFname(fname);
                    Uname.setText(fname + " " + lname);
                    byte[] imageData = rs.getBytes("User_Image");
                    setProfilePic(proPic, imageData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
