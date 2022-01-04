package org.example;

import configs.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pojo.Employee;
import pojo.User;
import services.EmployeeManagementServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeManagementController implements Initializable {

    @FXML
    private TableColumn<Employee, String> colAddress;

    @FXML
    private TableColumn<Employee, Date> colBirthDay;

    @FXML
    private TableColumn<Employee, String> colEmail;

    @FXML
    private TableColumn<Employee, String> colEmployeeId;

    @FXML
    private TableColumn<User, String> colEmployeeId2;

    @FXML
    private TableColumn<Employee, String> colEmployeeName;

    @FXML
    private TableColumn<User, String> colEmployeeName2;

    @FXML
    private TableColumn<User, String> colPassword;

    @FXML
    private TableColumn<Employee, String> colPhoneNumber;

    @FXML
    private TableColumn<User, String> colRole;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private DatePicker dpkBirthDay;

    @FXML
    private TableView<Employee> tbvEmployee;

    @FXML
    private TableView<User> tbvUser;

    @FXML
    private TabPane tpEmployee;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private ComboBox<Employee> cbxEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeName2;

    @FXML
    private TextField txtEmployeeSearch;

    @FXML
    private TextArea txtPassword;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtRole;

    @FXML
    private TextField txtUserSearch;

    @FXML
    private TextField txtUsername;

    @FXML
    void searchEmployeeHandler(ActionEvent event) throws SQLException {
        this.clearEmployeeDetails();
        this.loadEmployeeTableData(this.txtEmployeeSearch.getText());
    }

    @FXML
    void addEmployeeHandler(ActionEvent event) {
        if (this.isEmployeeEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm nhân viên thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Employee employee = new Employee();
            employee.setEmployeeId(this.txtEmployeeId.getText());
            employee.setEmployeeName(this.txtEmployeeName.getText());
            employee.setBirthDay(Date.from(this.dpkBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            employee.setAddress(this.txtAddress.getText());
            employee.setEmail(this.txtEmail.getText());
            employee.setPhoneNumber(this.txtPhoneNumber.getText());
            try {
                EmployeeManagementServices.addEmployee(employee);
                this.clearEmployeeDetails();
                this.loadEmployeeTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm nhân viên thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm nhân viên thất bại", "Mã nhân viên đã tồn tại!");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm nhân viên thất bại", "Không thể thêm mã nhân viên Admin!");
            }
        }
    }

    @FXML
    void deleteEmployeeHandler(ActionEvent event) {
        if (this.isEmployeeEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa nhân viên thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            try {
                EmployeeManagementServices.deleteEmployee(this.txtEmployeeId.getText());
                this.clearEmployeeDetails();
                this.loadEmployeeTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa nhân viên thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa nhân viên thất bại", "Mã nhân viên đang được liên kết!");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa nhân viên thất bại", "Không thể xóa mã nhân viên Admin!");
            }
        }
    }

    @FXML
    void editEmployeeHandler(ActionEvent event) {
        if (this.isEmployeeEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa nhân viên thất bại", "Vui lòng nhập đầy thông tin!");
        else {
            Employee selectedEmployee = this.tbvEmployee.getSelectionModel().getSelectedItem();
            Employee employee = new Employee();
            employee.setEmployeeId(this.txtEmployeeId.getText());
            employee.setEmployeeName(this.txtEmployeeName.getText());
            employee.setBirthDay(Date.from(this.dpkBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            employee.setAddress(this.txtAddress.getText());
            employee.setEmail(this.txtEmail.getText());
            employee.setPhoneNumber(this.txtPhoneNumber.getText());
            try {
                EmployeeManagementServices.editEmployee(selectedEmployee.getEmployeeId(), employee);
                this.clearEmployeeDetails();
                this.loadEmployeeTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa nhân viên thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                if (e.getErrorCode() == 1062)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa nhân viên thất bại", "Mã nhân viên mới đã tồn tại!");
                else if (e.getErrorCode() == 1451)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa nhân viên thất bại", "Mã nhân viên đang được liên kết!");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearEmployeeDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa nhân viên thất bại", "Không thể sửa mã nhân viên Admin!");
            }
        }
    }

    @FXML
    void backEmployeeHandler(ActionEvent event) throws IOException {
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

    @FXML
    void clickEmployeeHandler(MouseEvent event) {
        Employee selectedEmployee = this.tbvEmployee.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) return;
        this.txtEmployeeId.setText(selectedEmployee.getEmployeeId());
        this.txtEmployeeName.setText(selectedEmployee.getEmployeeName());
        this.dpkBirthDay.setValue(LocalDate.parse(Utils.dateFormat.format(selectedEmployee.getBirthDay()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.txtAddress.setText(selectedEmployee.getAddress());
        this.txtEmail.setText(selectedEmployee.getEmail());
        this.txtPhoneNumber.setText(selectedEmployee.getPhoneNumber());
    }

    @FXML
    void searchUserHandler(ActionEvent event) throws SQLException {
        this.clearUserDetails();
        this.loadUserTableData(this.txtUserSearch.getText());
    }

    @FXML
    void resetPasswordHandler(ActionEvent event) {
        if (this.tbvUser.getSelectionModel().getSelectedItem() != null && !this.isUserEmpty()) {
            User user = new User();
            user.setEmployee(this.cbxEmployeeId.getSelectionModel().getSelectedItem());
            user.setRole(this.txtRole.getText());
            user.setUsername(this.txtUsername.getText());
            user.setPassword(this.txtPassword.getText());
            try {
                EmployeeManagementServices.resetPassword(user, "12345678");
                this.clearUserDetails();
                this.loadUserTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Đặt lại mật khẩu thành công", "Mật khẩu đặt lại là: 12345678");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Đặt lại mật khẩu thất bại", "Vui lòng nhập đầy đủ thông tin!");
    }

    @FXML
    void deleteUserHandler(ActionEvent event) {
        if (this.tbvUser.getSelectionModel().getSelectedItem() != null && !this.isUserEmpty()) {
            try {
                EmployeeManagementServices.deleteUser(this.cbxEmployeeId.getSelectionModel().getSelectedItem().getEmployeeId());
                this.clearUserDetails();
                this.loadUserTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa người dùng thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearUserDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa người dùng thất bại", "Không thể xóa mã nhân viên Admin!");
            }
        } else
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa người dùng thất bại", "Vui lòng nhập đầy đủ thông tin!");
    }

    @FXML
    void editUserHandler(ActionEvent event) {
        if (this.tbvUser.getSelectionModel().getSelectedItem() != null && !this.isUserEmpty()) {
            User selectedUser = this.tbvUser.getSelectionModel().getSelectedItem();
            User user = new User();
            user.setEmployee(this.cbxEmployeeId.getSelectionModel().getSelectedItem());
            user.setRole(this.txtRole.getText());
            user.setUsername(this.txtUsername.getText());
            user.setPassword(this.txtPassword.getText());
            try {
                EmployeeManagementServices.editUser(selectedUser.getEmployee().getEmployeeId(), user);
                this.clearUserDetails();
                this.loadUserTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa người dùng thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearUserDetails();
                if (e.getMessage().contains("uq_employee_id"))
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa người dùng thất bại", "Mã nhân viên mới đã tồn tại");
                else if (e.getMessage().contains("uq_username"))
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa người dùng thất bại", "Tên tài khoản mới đã tồn tại");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearUserDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa người dùng thất bại", "Không thể sửa mã nhân viên Admin!");
            }
        } else
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa người dùng thất bại", "Vui lòng nhập đầy đủ thông tin!");
    }

    @FXML
    void clickUserHandler(MouseEvent event) {
        User selectedUser = this.tbvUser.getSelectionModel().getSelectedItem();
        if (selectedUser == null) return;
        this.cbxEmployeeId.getSelectionModel().select(this.cbxEmployeeId.getItems().stream().filter(employee -> employee.getEmployeeId().equals(selectedUser.getEmployee().getEmployeeId())).findFirst().orElse(null));
        this.txtRole.setText(selectedUser.getRole());
        this.txtUsername.setText(selectedUser.getUsername());
        this.txtPassword.setText(selectedUser.getPassword());
    }

    @FXML
    void backUserHandler(ActionEvent event) throws IOException {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadEmployeeTableData(null);
            this.loadUserTableData(null);
            this.cbxEmployeeId.setItems(FXCollections.observableArrayList(EmployeeManagementServices.getEmployeeListByKeyword(null)));
            this.cbxEmployeeId.getSelectionModel().selectFirst();
            this.cbxEmployeeId.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
                this.txtEmployeeName2.setText(this.cbxEmployeeId.getSelectionModel().getSelectedItem().getEmployeeName());
            });
            this.txtEmployeeName2.setText(this.cbxEmployeeId.getSelectionModel().getSelectedItem().getEmployeeName());
            this.tpEmployee.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
                try {
                    this.txtEmployeeSearch.clear();
                    this.clearEmployeeDetails();
                    this.loadEmployeeTableData(null);
                    this.txtUserSearch.clear();
                    this.clearUserDetails();
                    this.loadUserTableData(null);
                    this.cbxEmployeeId.setItems(FXCollections.observableArrayList(EmployeeManagementServices.getEmployeeListByKeyword(null)));
                    this.cbxEmployeeId.getSelectionModel().selectFirst();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadEmployeeTableData(String keyword) throws SQLException {
        this.colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("EmployeeId"));
        this.colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("EmployeeName"));
        this.colBirthDay.setCellValueFactory(new PropertyValueFactory<>("BirthDay"));
        this.colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        this.colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        this.tbvEmployee.setItems(FXCollections.observableArrayList(EmployeeManagementServices.getEmployeeListByKeyword(keyword)));
    }

    public void clearEmployeeDetails() {
        this.txtEmployeeId.clear();
        this.txtEmployeeName.clear();
        this.dpkBirthDay.setValue(null);
        this.txtAddress.clear();
        this.txtEmail.clear();
        this.txtPhoneNumber.clear();
    }

    public boolean isEmployeeEmpty() {
        return this.txtEmployeeId.getText().equals("") || this.txtEmployeeName.getText().equals("") || this.dpkBirthDay.getValue() == null
                || this.txtAddress.getText().equals("") || this.txtEmail.getText().equals("") || this.txtPhoneNumber.getText().equals("");
    }

    public void loadUserTableData(String keyword) throws SQLException {
        this.colEmployeeId2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEmployee().getEmployeeId()));
        this.colEmployeeName2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getEmployee().getEmployeeName()));
        this.colRole.setCellValueFactory(new PropertyValueFactory<>("Role"));
        this.colUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        this.colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));
        this.tbvUser.setItems(FXCollections.observableArrayList(EmployeeManagementServices.getUserListByKeyword(keyword)));
    }

    public void clearUserDetails() {
        this.cbxEmployeeId.getSelectionModel().selectFirst();
        this.txtRole.clear();
        this.txtUsername.clear();
        this.txtPassword.clear();
    }

    public boolean isUserEmpty() {
        return this.txtRole.getText().equals("") || this.txtUsername.getText().equals("") || this.txtPassword.getText().equals("");
    }
}
