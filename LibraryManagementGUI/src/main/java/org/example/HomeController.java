package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtRole;

    @FXML
    void bookManagementHandler(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        FXMLLoader loader = new FXMLLoader(App.class.getResource("book_management.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Quản lý thư viện | Quản lý sách");
        stage.show();
    }

    @FXML
    void borrowedBookManagementHandler(ActionEvent event) {

    }

    @FXML
    void readerManagementHandler(ActionEvent event) {

    }

    @FXML
    void reportHandler(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.txtEmployeeName.setText(LoginController.user.getEmployeeName());
        this.txtRole.setText(LoginController.user.getUser().getRole());
    }
}
