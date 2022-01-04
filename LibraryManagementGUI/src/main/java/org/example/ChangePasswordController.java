package org.example;

import configs.JDBCUtils;
import configs.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.User;
import services.ChangePasswordServices;
import services.EmployeeManagementServices;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {

    @FXML
    private PasswordField txtConfirmNewPassword;

    @FXML
    private TextField txtCurrentPassword;

    @FXML
    private TextField txtNewPassword;

    private boolean isChangeSuccessful;

    @FXML
    void changePasswordHandler(ActionEvent event) throws SQLException {
        if (this.isEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đổi mật khẩu thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            if (this.txtNewPassword.getText().equals(this.txtConfirmNewPassword.getText())) {
                if (DigestUtils.md5Hex(this.txtCurrentPassword.getText()).equals(LoginController.user.getPassword())) {
                    try {
                        ChangePasswordServices.changePassword(LoginController.user, this.txtNewPassword.getText());
                        this.isChangeSuccessful = true;
                        this.clearDetails();
                        Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đổi mật khẩu thành công", "");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đổi mật khẩu thất bại", "Mật khẩu hiện tại không chính xác!");
            } else
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đổi mật khẩu thất bại", "Mật khẩu mới nhập lại không chính xác!");
        }
    }

    @FXML
    void backHandler(ActionEvent event) throws IOException {
        if (this.isChangeSuccessful) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Quản lý thư viện | Đăng nhập");
            stage.show();
        } else {
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
        }
    }

    public boolean isEmpty() {
        return this.txtCurrentPassword.getText().equals("") || this.txtNewPassword.getText().equals("") || this.txtConfirmNewPassword.getText().equals("");
    }

    public void clearDetails() {
        this.txtCurrentPassword.clear();
        this.txtNewPassword.clear();
        this.txtConfirmNewPassword.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.isChangeSuccessful = false;
    }
}