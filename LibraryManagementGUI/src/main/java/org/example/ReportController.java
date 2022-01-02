package org.example;

import configs.Utils;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import pojo.BookReport;
import pojo.ReaderReport;
import services.ReportServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    @FXML
    private ComboBox<String> cbxQuarter;

    @FXML
    private ComboBox<String> cbxQuarter2;

    @FXML
    private ComboBox<String> cbxReport;

    @FXML
    private ComboBox<String> cbxReport2;

    @FXML
    private TableColumn<BookReport, Integer> colBorrowedBookNumber;

    @FXML
    private TableColumn<ReaderReport, Integer> colBorrowedBookNumber2;

    @FXML
    private TableColumn<ReaderReport, String> colFine;

    @FXML
    private TableColumn<BookReport, Integer> colLostBookNumber;

    @FXML
    private TableColumn<ReaderReport, Integer> colLostBookNumber2;

    @FXML
    private TableColumn<BookReport, Integer> colOrder;

    @FXML
    private TableColumn<ReaderReport, Integer> colOrder2;

    @FXML
    private TableColumn<BookReport, Integer> colOverdueBookNumber;

    @FXML
    private TableColumn<ReaderReport, Integer> colOverdueBookNumber2;

    @FXML
    private TableColumn<ReaderReport, String> colReaderId;

    @FXML
    private TableColumn<ReaderReport, String> colReaderName;

    @FXML
    private TableColumn<BookReport, Integer> colReturnedBookNumber;

    @FXML
    private TableColumn<ReaderReport, Integer> colReturnedBookNumber2;

    @FXML
    private TableColumn<BookReport, String> colTitleId;

    @FXML
    private TableColumn<BookReport, String> colTitleName;

    @FXML
    private TableView<BookReport> tbvBookReport;

    @FXML
    private TableView<ReaderReport> tbvReaderReport;

    @FXML
    private TabPane tpReport;

    @FXML
    private TextField txtBorrowedBookTotal;

    @FXML
    private TextField txtBorrowedBookTotal2;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeId2;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeName2;

    @FXML
    private TextField txtLostBookTotal;

    @FXML
    private TextField txtLostBookTotal2;

    @FXML
    private TextField txtOverdueBookTotal;

    @FXML
    private TextField txtOverdueBookTotal2;

    @FXML
    private TextField txtReturnedBookTotal;

    @FXML
    private TextField txtReturnedBookTotal2;

    @FXML
    private TextField txtTotalFines;

    @FXML
    private TextField txtYear;

    @FXML
    private TextField txtYear2;

    @FXML
    private Label lblQuarter;
    @FXML
    private Label lblQuarter2;

    @FXML
    void showBookReportHandler(ActionEvent event) throws SQLException {
        if (this.cbxReport.getSelectionModel().getSelectedItem().equals("Năm")) {
            if (this.txtYear.getText().equals(""))
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xem báo cáo thất bại", "Vui lòng nhập năm!");
            else {
                this.loadBookReportTableData(this.txtYear.getText());
                this.showBookReportTotal();
            }
        } else {
            if (this.txtYear.getText().equals(""))
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xem báo cáo thất bại", "Vui lòng nhập năm!");
            else {
                this.loadBookReportTableData(this.cbxQuarter.getSelectionModel().getSelectedItem(), this.txtYear.getText());
                this.showBookReportTotal();
            }
        }
    }

    @FXML
    void printBookReportHandler(ActionEvent event) {

    }

    @FXML
    void backBookReportHandler(ActionEvent event) throws IOException {
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
    void showReaderReportHandler(ActionEvent event) throws SQLException {
        if (this.cbxReport2.getSelectionModel().getSelectedItem().equals("Năm")) {
            if (this.txtYear2.getText().equals(""))
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xem báo cáo thất bại", "Vui lòng nhập năm!");
            else {
                this.loadReaderReportTableData(this.txtYear2.getText());
                this.showReaderReportTotal();
            }
        } else {
            if (this.txtYear2.getText().equals(""))
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xem báo cáo thất bại", "Vui lòng nhập năm!");
            else {
                this.loadReaderReportTableData(this.cbxQuarter2.getSelectionModel().getSelectedItem(), this.txtYear2.getText());
                this.showReaderReportTotal();
            }
        }
    }

    @FXML
    void printReaderReportHandler(ActionEvent event) {

    }

    @FXML
    void backReaderReportHandler(ActionEvent event) throws IOException {
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
        this.txtEmployeeId.setText(LoginController.user.getEmployee().getEmployeeId());
        this.txtEmployeeName.setText(LoginController.user.getEmployee().getEmployeeName());
        this.cbxReport.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"Năm", "Quý"})));
        this.cbxReport.getSelectionModel().selectFirst();
        this.cbxQuarter.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"Một", "Hai", "Ba", "Bốn"})));
        this.cbxQuarter.getSelectionModel().selectFirst();
        this.lblQuarter.setOpacity(0);
        this.cbxQuarter.setOpacity(0);
        this.cbxReport.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (this.cbxReport.getSelectionModel().getSelectedItem().equals("Năm")) {
                this.txtYear.clear();
                this.lblQuarter.setOpacity(0);
                this.cbxQuarter.setOpacity(0);
                this.clearBookReportDetails();
            } else {
                this.txtYear.clear();
                this.lblQuarter.setOpacity(1);
                this.cbxQuarter.setOpacity(1);
                this.cbxQuarter.getSelectionModel().selectFirst();
                this.clearBookReportDetails();
            }
        });
        this.txtEmployeeId2.setText(LoginController.user.getEmployee().getEmployeeId());
        this.txtEmployeeName2.setText(LoginController.user.getEmployee().getEmployeeName());
        this.cbxReport2.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"Năm", "Quý"})));
        this.cbxReport2.getSelectionModel().selectFirst();
        this.cbxQuarter2.setItems(FXCollections.observableArrayList(Arrays.asList(new String[]{"Một", "Hai", "Ba", "Bốn"})));
        this.cbxQuarter2.getSelectionModel().selectFirst();
        this.lblQuarter2.setOpacity(0);
        this.cbxQuarter2.setOpacity(0);
        this.cbxReport2.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            if (this.cbxReport2.getSelectionModel().getSelectedItem().equals("Năm")) {
                this.txtYear2.clear();
                this.lblQuarter2.setOpacity(0);
                this.cbxQuarter2.setOpacity(0);
                this.clearReaderReportDetails();
            } else {
                this.txtYear2.clear();
                this.lblQuarter2.setOpacity(1);
                this.cbxQuarter2.setOpacity(1);
                this.cbxQuarter2.getSelectionModel().selectFirst();
                this.clearReaderReportDetails();
            }
        });
        this.tpReport.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
            this.cbxReport.getSelectionModel().selectFirst();
            this.txtYear.clear();
            this.clearBookReportDetails();
            this.cbxReport2.getSelectionModel().selectFirst();
            this.txtYear2.clear();
            this.clearReaderReportDetails();
        });
    }

    public void loadBookReportTableData(String year) throws SQLException {
        this.colOrder.setCellValueFactory(new PropertyValueFactory<>("Order"));
        this.colTitleId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTitle().getTitleId()));
        this.colTitleName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTitle().getTitleName()));
        this.colBorrowedBookNumber.setCellValueFactory(new PropertyValueFactory<>("BorrowedBookNumber"));
        this.colReturnedBookNumber.setCellValueFactory(new PropertyValueFactory<>("ReturnedBookNumber"));
        this.colLostBookNumber.setCellValueFactory(new PropertyValueFactory<>("LostBookNumber"));
        this.colOverdueBookNumber.setCellValueFactory(new PropertyValueFactory<>("OverdueBookNumber"));
        this.tbvBookReport.setItems(FXCollections.observableArrayList(ReportServices.getBookReportListByYear(year)));
    }

    public void loadBookReportTableData(String quarter, String year) throws SQLException {
        this.colOrder.setCellValueFactory(new PropertyValueFactory<>("Order"));
        this.colTitleId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTitle().getTitleId()));
        this.colTitleName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getTitle().getTitleName()));
        this.colBorrowedBookNumber.setCellValueFactory(new PropertyValueFactory<>("BorrowedBookNumber"));
        this.colReturnedBookNumber.setCellValueFactory(new PropertyValueFactory<>("ReturnedBookNumber"));
        this.colLostBookNumber.setCellValueFactory(new PropertyValueFactory<>("LostBookNumber"));
        this.colOverdueBookNumber.setCellValueFactory(new PropertyValueFactory<>("OverdueBookNumber"));
        this.tbvBookReport.setItems(FXCollections.observableArrayList(ReportServices.getBookReportListByQuarter(quarter, year)));
    }

    public void clearBookReportDetails() {
        this.txtBorrowedBookTotal.clear();
        this.txtReturnedBookTotal.clear();
        this.txtLostBookTotal.clear();
        this.txtOverdueBookTotal.clear();
        this.tbvBookReport.setItems(null);
    }

    public void showBookReportTotal() {
        this.txtBorrowedBookTotal.setText(String.valueOf(this.tbvBookReport.getItems().stream().mapToInt(value -> value.getBorrowedBookNumber()).sum()));
        this.txtReturnedBookTotal.setText(String.valueOf(this.tbvBookReport.getItems().stream().mapToInt(value -> value.getReturnedBookNumber()).sum()));
        this.txtLostBookTotal.setText(String.valueOf(this.tbvBookReport.getItems().stream().mapToInt(value -> value.getLostBookNumber()).sum()));
        this.txtOverdueBookTotal.setText(String.valueOf(this.tbvBookReport.getItems().stream().mapToInt(value -> value.getOverdueBookNumber()).sum()));
    }

    public void loadReaderReportTableData(String year) throws SQLException {
        this.colOrder2.setCellValueFactory(new PropertyValueFactory<>("Order"));
        this.colReaderId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReader().getReaderId()));
        this.colReaderName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReader().getReaderName()));
        this.colBorrowedBookNumber2.setCellValueFactory(new PropertyValueFactory<>("BorrowedBookNumber"));
        this.colReturnedBookNumber2.setCellValueFactory(new PropertyValueFactory<>("ReturnedBookNumber"));
        this.colLostBookNumber2.setCellValueFactory(new PropertyValueFactory<>("LostBookNumber"));
        this.colOverdueBookNumber2.setCellValueFactory(new PropertyValueFactory<>("OverdueBookNumber"));
        this.colFine.setCellValueFactory(e -> new SimpleStringProperty(Utils.currencyFormat(e.getValue().getFine(), new Locale("vi", "VN"))));
        this.tbvReaderReport.setItems(FXCollections.observableArrayList(ReportServices.getReaderReportListByYear(year)));
    }

    public void loadReaderReportTableData(String quarter, String year) throws SQLException {
        this.colOrder2.setCellValueFactory(new PropertyValueFactory<>("Order"));
        this.colReaderId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReader().getReaderId()));
        this.colReaderName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReader().getReaderName()));
        this.colBorrowedBookNumber2.setCellValueFactory(new PropertyValueFactory<>("BorrowedBookNumber"));
        this.colReturnedBookNumber2.setCellValueFactory(new PropertyValueFactory<>("ReturnedBookNumber"));
        this.colLostBookNumber2.setCellValueFactory(new PropertyValueFactory<>("LostBookNumber"));
        this.colOverdueBookNumber2.setCellValueFactory(new PropertyValueFactory<>("OverdueBookNumber"));
        this.colFine.setCellValueFactory(e -> new SimpleStringProperty(Utils.currencyFormat(e.getValue().getFine(), new Locale("vi", "VN"))));
        this.tbvReaderReport.setItems(FXCollections.observableArrayList(ReportServices.getReaderReportListByQuarter(quarter, year)));
    }

    public void clearReaderReportDetails() {
        this.txtBorrowedBookTotal2.clear();
        this.txtReturnedBookTotal2.clear();
        this.txtLostBookTotal2.clear();
        this.txtOverdueBookTotal2.clear();
        this.txtTotalFines.clear();
        this.tbvReaderReport.setItems(null);
    }

    public void showReaderReportTotal() {
        this.txtBorrowedBookTotal2.setText(String.valueOf(this.tbvReaderReport.getItems().stream().mapToInt(value -> value.getBorrowedBookNumber()).sum()));
        this.txtReturnedBookTotal2.setText(String.valueOf(this.tbvReaderReport.getItems().stream().mapToInt(value -> value.getReturnedBookNumber()).sum()));
        this.txtLostBookTotal2.setText(String.valueOf(this.tbvReaderReport.getItems().stream().mapToInt(value -> value.getLostBookNumber()).sum()));
        this.txtOverdueBookTotal2.setText(String.valueOf(this.tbvReaderReport.getItems().stream().mapToInt(value -> value.getOverdueBookNumber()).sum()));
        this.txtTotalFines.setText(Utils.currencyFormat(this.tbvReaderReport.getItems().stream().mapToDouble(value -> value.getFine()).sum(), new Locale("vi", "VN")));
    }
}
