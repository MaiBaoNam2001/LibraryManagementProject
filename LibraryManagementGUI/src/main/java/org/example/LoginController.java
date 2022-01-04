package org.example;

import configs.JDBCUtils;
import configs.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.Employee;
import pojo.User;
import services.LoginServices;

public class LoginController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    private Connection connection;
    public static User user;

    public LoginController() throws SQLException {
        this.connection = JDBCUtils.getConnection();
    }

    @FXML
    void loginHandler(ActionEvent event) throws SQLException, IOException {
        if (this.txtUsername.getText() != "" && this.txtPassword.getText() != "") {
            PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM user WHERE Username = ? and Password = ?");
            ps.setString(1, this.txtUsername.getText());
            ps.setString(2, DigestUtils.md5Hex(this.txtPassword.getText()));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đăng nhập thành công", "");
                user = LoginServices.getUserInformation(this.txtUsername.getText(), DigestUtils.md5Hex(this.txtPassword.getText()));
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader(App.class.getResource("home.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                stage.setScene(scene);
                stage.setTitle("Quản lý thư viện | Trang chủ");
                stage.show();
            } else
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng nhập thất bại", "Tên tài khoản hoặc mật khẩu không đúng!");
        } else
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng nhập thất bại", "Vui lòng nhập đầy đủ thông tin!");
    }

    @FXML
    void registerHandler(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Quản lý thư viện | Đăng ký tài khoản");
        stage.show();
    }

}
