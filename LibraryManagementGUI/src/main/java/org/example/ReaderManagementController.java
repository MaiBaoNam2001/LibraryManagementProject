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
import pojo.Reader;
import pojo.ReaderCard;
import services.ReaderManagementServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class ReaderManagementController implements Initializable {
    @FXML
    private TabPane tpReader;

    @FXML
    private ComboBox<Reader> cbxReaderId;

    @FXML
    private TableColumn<ReaderCard, String> colReaderId2;

    @FXML
    private TableColumn<ReaderCard, String> colReaderName2;

    @FXML
    private TableColumn<Reader, String> colAddress;

    @FXML
    private TableColumn<Reader, Date> colBirthDay;

    @FXML
    private TableColumn<Reader, String> colDepartment;

    @FXML
    private TableColumn<Reader, String> colEmail;

    @FXML
    private TableColumn<ReaderCard, Date> colExpirationDate;

    @FXML
    private TableColumn<Reader, String> colGender;

    @FXML
    private TableColumn<Reader, String> colObject;

    @FXML
    private TableColumn<Reader, String> colPhoneNumber;

    @FXML
    private TableColumn<ReaderCard, String> colReaderCardId;

    @FXML
    private TableColumn<Reader, String> colReaderId;

    @FXML
    private TableColumn<Reader, String> colReaderName;

    @FXML
    private TableColumn<ReaderCard, Date> colStartDate;

    @FXML
    private DatePicker dpkBirthDay;

    @FXML
    private DatePicker dpkExpirationDate;

    @FXML
    private DatePicker dpkStartDate;

    @FXML
    private RadioButton rdnFemale;

    @FXML
    private ToggleGroup rdnGender;

    @FXML
    private RadioButton rdnMale;

    @FXML
    private TableView<Reader> tbvReader;

    @FXML
    private TableView<ReaderCard> tbvReaderCard;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextField txtDepartment;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtObject;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private TextField txtReaderCardId;

    @FXML
    private TextField txtReaderCardSearch;

    @FXML
    private TextField txtReaderId;

    @FXML
    private TextField txtReaderName;

    @FXML
    private TextField txtReaderSearch;

    @FXML
    private TextField txtReaderName2;

    @FXML
    void searchReaderHandler(ActionEvent event) throws SQLException {
        this.clearReaderDetails();
        this.loadReaderTableData(this.txtReaderSearch.getText());
    }

    @FXML
    void addReaderHandler(ActionEvent event) {
        if (this.isReaderEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Reader reader = new Reader();
            reader.setReaderId(this.txtReaderId.getText());
            reader.setReaderName(this.txtReaderName.getText());
            reader.setGender(this.rdnMale.isSelected() ? "Nam" : "Nữ");
            reader.setBirthDay(Date.from(this.dpkBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            reader.setAddress(this.txtAddress.getText());
            reader.setEmail(this.txtEmail.getText());
            reader.setPhoneNumber(this.txtPhoneNumber.getText());
            reader.setObject(this.txtObject.getText());
            reader.setDepartment(this.txtDepartment.getText());
            try {
                ReaderManagementServices.addReader(reader);
                this.clearReaderDetails();
                this.loadReaderTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm độc giả thất bại", "Mã độc giả đã tồn tại!");
            }
        }
    }

    @FXML
    void deleteReaderHandler(ActionEvent event) {
        if (this.isReaderEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            try {
                ReaderManagementServices.deleteReader(this.txtReaderId.getText());
                this.clearReaderDetails();
                this.loadReaderTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa độc giả thất bại", "Mã độc giả đang được liên kết!");
            }
        }
    }

    @FXML
    void editReaderHandler(ActionEvent event) {
        if (this.isReaderEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Reader selectedReader = this.tbvReader.getSelectionModel().getSelectedItem();
            Reader reader = new Reader();
            reader.setReaderId(this.txtReaderId.getText());
            reader.setReaderName(this.txtReaderName.getText());
            reader.setGender(this.rdnMale.isSelected() ? "Nam" : "Nữ");
            reader.setBirthDay(Date.from(this.dpkBirthDay.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            reader.setAddress(this.txtAddress.getText());
            reader.setEmail(this.txtEmail.getText());
            reader.setPhoneNumber(this.txtPhoneNumber.getText());
            reader.setObject(this.txtObject.getText());
            reader.setDepartment(this.txtDepartment.getText());
            try {
                ReaderManagementServices.editReader(selectedReader.getReaderId(), reader);
                this.clearReaderDetails();
                this.loadReaderTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderDetails();
                if (e.getErrorCode() == 1062)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa độc giả thất bại", "Mã độc giả mới đã tồn tại!");
                else if (e.getErrorCode() == 1451)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa độc giả thất bại", "Mã độc giả đang được liên kết!");
            }
        }
    }

    @FXML
    void backReaderHandler(ActionEvent event) throws IOException {
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
    void clickReaderHandler(MouseEvent event) {
        Reader selectedReader = this.tbvReader.getSelectionModel().getSelectedItem();
        if (selectedReader == null) return;
        this.txtReaderId.setText(selectedReader.getReaderId());
        this.txtReaderName.setText(selectedReader.getReaderName());
        if (selectedReader.getGender().equals(this.rdnMale.getText()))
            this.rdnMale.setSelected(true);
        else this.rdnFemale.setSelected(true);
        this.dpkBirthDay.setValue(LocalDate.parse(Utils.dateFormat.format(selectedReader.getBirthDay()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.txtAddress.setText(selectedReader.getAddress());
        this.txtEmail.setText(selectedReader.getEmail());
        this.txtPhoneNumber.setText(selectedReader.getPhoneNumber());
        this.txtObject.setText(selectedReader.getObject());
        this.txtDepartment.setText(selectedReader.getDepartment());
    }

    @FXML
    void searchReaderCardHandler(ActionEvent event) throws SQLException {
        this.clearReaderCardDetails();
        this.loadReaderCardTableData(this.txtReaderCardSearch.getText());
    }

    @FXML
    void addReaderCardHandler(ActionEvent event) {
        if (this.isReaderCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            ReaderCard readerCard = new ReaderCard();
            readerCard.setReaderCardId(this.txtReaderCardId.getText());
            readerCard.setReader(this.cbxReaderId.getSelectionModel().getSelectedItem());
            readerCard.setStartDate(Date.from(this.dpkStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            readerCard.setExpirationDate(Date.from(this.dpkExpirationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            try {
                ReaderManagementServices.addReaderCard(readerCard);
                this.clearReaderCardDetails();
                this.loadReaderCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm thẻ độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderCardDetails();
                if (e.getMessage().contains("PRIMARY"))
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ độc giả thất bại", "Mã thẻ độc giả đã tồn tại!");
                else
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ độc giả thất bại", "Mã độc giả đã tồn tại!");
            } catch (DateTimeException e) {
                e.printStackTrace();
                this.clearReaderCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ độc giả thất bại", "Ngày không hợp lệ!");
            }
        }
    }

    @FXML
    void deleteReaderCardHandler(ActionEvent event) {
        if (this.isReaderCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa thẻ độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            try {
                ReaderManagementServices.deleteReaderCard(this.txtReaderCardId.getText());
                this.clearReaderCardDetails();
                this.loadReaderCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thẻ độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa thẻ độc giả thất bại", "Mã thẻ độc giả đang được liên kết!");
            }
        }
    }

    @FXML
    void editReaderCardHandler(ActionEvent event) {
        if (this.isReaderCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ độc giả thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            ReaderCard selectedReaderCard = this.tbvReaderCard.getSelectionModel().getSelectedItem();
            ReaderCard readerCard = new ReaderCard();
            readerCard.setReaderCardId(this.txtReaderCardId.getText());
            readerCard.setReader(this.cbxReaderId.getSelectionModel().getSelectedItem());
            readerCard.setStartDate(Date.from(this.dpkStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            readerCard.setExpirationDate(Date.from(this.dpkExpirationDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            try {
                ReaderManagementServices.editReaderCard(selectedReaderCard.getReaderCardId(), readerCard);
                this.clearReaderCardDetails();
                this.loadReaderCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa thẻ độc giả thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearReaderCardDetails();
                if (e.getMessage().contains("PRIMARY"))
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ độc giả thất bại", "Mã thẻ độc giả mới đã tồn tại!");
                else
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ độc giả thất bại", "Mã độc giả mới đã tồn tại!");
            } catch (DateTimeException e) {
                e.printStackTrace();
                this.clearReaderCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ độc giả thất bại", "Ngày không hợp lệ!");
            }
        }
    }

    @FXML
    void backReaderCardHandler(ActionEvent event) throws IOException {
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
    void clickReaderCardHandler(MouseEvent event) {
        ReaderCard selectedReaderCard = this.tbvReaderCard.getSelectionModel().getSelectedItem();
        if (selectedReaderCard == null) return;
        this.txtReaderCardId.setText(selectedReaderCard.getReaderCardId());
        this.cbxReaderId.getSelectionModel().select(this.cbxReaderId.getItems().stream().filter(reader -> reader.getReaderId().equals(selectedReaderCard.getReader().getReaderId())).findFirst().orElse(null));
        this.dpkStartDate.setValue(LocalDate.parse(Utils.dateFormat.format(selectedReaderCard.getStartDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.dpkExpirationDate.setValue(LocalDate.parse(Utils.dateFormat.format(selectedReaderCard.getExpirationDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    @FXML
    void showReaderNameHandler(ActionEvent event) {
        this.txtReaderName2.setText(this.cbxReaderId.getSelectionModel().getSelectedItem().getReaderName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadReaderTableData(null);
            this.loadReaderCardTableData(null);
            this.cbxReaderId.setItems(FXCollections.observableArrayList(ReaderManagementServices.getReaderListByKeyword(null)));
            this.cbxReaderId.getSelectionModel().selectFirst();
            this.txtReaderName2.setText(this.cbxReaderId.getSelectionModel().getSelectedItem().getReaderName());
            this.tpReader.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
                try {
                    this.txtReaderSearch.clear();
                    this.txtReaderCardSearch.clear();
                    this.clearReaderDetails();
                    this.clearReaderCardDetails();
                    this.loadReaderTableData(null);
                    this.loadReaderCardTableData(null);
                    this.cbxReaderId.setItems(FXCollections.observableArrayList(ReaderManagementServices.getReaderListByKeyword(null)));
                    this.cbxReaderId.getSelectionModel().selectFirst();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadReaderTableData(String keyword) throws SQLException {
        this.colReaderId.setCellValueFactory(new PropertyValueFactory<>("ReaderId"));
        this.colReaderName.setCellValueFactory(new PropertyValueFactory<>("ReaderName"));
        this.colGender.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        this.colBirthDay.setCellValueFactory(new PropertyValueFactory<>("BirthDay"));
        this.colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        this.colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        this.colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        this.colObject.setCellValueFactory(new PropertyValueFactory<>("Object"));
        this.colDepartment.setCellValueFactory(new PropertyValueFactory<>("Department"));
        this.tbvReader.setItems(FXCollections.observableArrayList(ReaderManagementServices.getReaderListByKeyword(keyword)));
    }

    public void clearReaderDetails() {
        this.txtReaderId.clear();
        this.txtReaderName.clear();
        this.rdnMale.setSelected(true);
        this.dpkBirthDay.setValue(null);
        this.txtAddress.clear();
        this.txtEmail.clear();
        this.txtPhoneNumber.clear();
        this.txtObject.clear();
        this.txtDepartment.clear();
    }

    public boolean isReaderEmpty() {
        return this.txtReaderId.getText().equals("") || this.txtReaderName.getText().equals("") || this.dpkBirthDay.getValue() == null
                || this.txtAddress.getText().equals("") || this.txtEmail.getText().equals("") || this.txtPhoneNumber.getText().equals("")
                || this.txtObject.getText().equals("") || this.txtDepartment.getText().equals("");
    }

    public void loadReaderCardTableData(String keyword) throws SQLException {
        this.colReaderCardId.setCellValueFactory(new PropertyValueFactory<>("ReaderCardId"));
        this.colReaderId2.setCellValueFactory(new PropertyValueFactory<>("Reader"));
        this.colReaderName2.setCellValueFactory(r -> new SimpleStringProperty(r.getValue().getReader().getReaderName()));
        this.colStartDate.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        this.colExpirationDate.setCellValueFactory(new PropertyValueFactory<>("ExpirationDate"));
        this.tbvReaderCard.setItems(FXCollections.observableArrayList(ReaderManagementServices.getReaderCardListByKeyword(keyword)));
    }

    public void clearReaderCardDetails() {
        this.txtReaderCardId.clear();
        this.cbxReaderId.getSelectionModel().selectFirst();
        this.dpkStartDate.setValue(null);
        this.dpkExpirationDate.setValue(null);
    }

    public boolean isReaderCardEmpty() {
        return this.txtReaderCardId.getText().equals("") || this.dpkStartDate.getValue() == null || this.dpkExpirationDate.getValue() == null;
    }
}
