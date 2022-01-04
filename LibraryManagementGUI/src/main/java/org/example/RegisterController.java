package org.example;

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
import pojo.Employee;
import pojo.User;
import services.RegisterServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController {

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    void registerHandler(ActionEvent event) {
        if (this.isEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            if (this.txtPassword.getText().equals(this.txtConfirmPassword.getText())) {
                User user = new User();
                user.setRole("Người dùng");
                user.setUsername(this.txtUsername.getText());
                user.setPassword(this.txtPassword.getText());
                Employee employee = new Employee();
                employee.setEmployeeId(this.txtEmployeeId.getText());
                user.setEmployee(employee);
                try {
                    RegisterServices.addUser(user);
                    this.clearDetails();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đăng ký thành công", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    if (e.getMessage().contains("uq_username"))
                        Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Tên tài khoản đã tồn tại!");
                    else if (e.getMessage().contains("uq_employee_id"))
                        Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Mã nhân viên đã tồn tại!");
                    else if (e.getErrorCode() == 1452)
                        Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Mã nhân viên không hợp lệ!");
                } catch (UnknownError e) {
                    e.printStackTrace();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Admin đã tồn tại!");
                }
            } else
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đăng ký thất bại", "Mật khẩu nhập lại không chính xác!");
        }
    }

    @FXML
    void backHandler(ActionEvent event) throws IOException {
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
    }

    public boolean isEmpty() {
        return this.txtEmployeeId.getText().equals("") || this.txtUsername.getText().equals("")
                || this.txtPassword.getText().equals("") || this.txtConfirmPassword.getText().equals("");
    }

    public void clearDetails() {
        this.txtEmployeeId.clear();
        this.txtUsername.clear();
        this.txtPassword.clear();
        this.txtConfirmPassword.clear();
    }
}
