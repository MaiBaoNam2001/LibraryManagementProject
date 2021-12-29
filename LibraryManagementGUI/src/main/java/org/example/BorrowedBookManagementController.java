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
import pojo.Book;
import pojo.BorrowedCard;
import pojo.BorrowedCardDetails;
import pojo.ReaderCard;
import services.BookManagementServices;
import services.BorrowedBookManagementServices;
import services.ReaderManagementServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SimpleTimeZone;

public class BorrowedBookManagementController implements Initializable {
    @FXML
    private ComboBox<ReaderCard> cbxReaderCardId;

    @FXML
    private TableColumn<BorrowedCardDetails, String> colStatus;

    @FXML
    private TableColumn<BorrowedCardDetails, String> colBookId;

    @FXML
    private TableColumn<?, ?> colBookId2;

    @FXML
    private TableColumn<?, ?> colBookId3;

    @FXML
    private TableColumn<BorrowedCardDetails, String> colBookName;

    @FXML
    private TableColumn<?, ?> colBookName2;

    @FXML
    private TableColumn<?, ?> colBookName3;

    @FXML
    private TableColumn<BorrowedCard, String> colBorrowedCardId;

    @FXML
    private TableColumn<?, ?> colBorrowedCardId2;

    @FXML
    private TableColumn<?, ?> colBorrowedCardId3;

    @FXML
    private TableColumn<BorrowedCard, Date> colBorrowedDate;

    @FXML
    private TableColumn<?, ?> colBorrowedDate3;

    @FXML
    private TableColumn<?, ?> colBorrowedDate4;

    @FXML
    private TableColumn<BorrowedCardDetails, Boolean> colReturned;

    @FXML
    private TableColumn<BorrowedCardDetails, Date> colReturnedDate;

    @FXML
    private TableColumn<BorrowedCard, String> colReaderCardId;

    @FXML
    private TableColumn<BorrowedCard, String> colReaderName;

    @FXML
    private TableColumn<?, ?> colReturnedDate2;

    @FXML
    private TableColumn<?, ?> colStatus2;

    @FXML
    private DatePicker dpkBorrowedDate;

    @FXML
    private TableView<BorrowedCard> tbvBorrowedCard;

    @FXML
    private TableView<BorrowedCardDetails> tbvBorrowedCardDetails;

    @FXML
    private TableView<?> tbvBorrowedCardDetails2;

    @FXML
    private TableView<?> tbvReturnedBook;

    @FXML
    private TabPane tpBorrowedBook;

    @FXML
    private TextField txtBookId;

    @FXML
    private TextField txtBookId2;

    @FXML
    private TextField txtBorrowedCardId;

    @FXML
    private TextField txtBorrowedCardSearch;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtFine;

    @FXML
    private TextField txtReaderCardId;

    @FXML
    private TextField txtReaderName;

    @FXML
    private TextField txtStatus;

    private String bookIdTemp;

    @FXML
    void searchBorrowedCardHandler(ActionEvent event) throws SQLException {
        this.clearBorrowedCardDetails();
        this.loadBorrowedCardTableData(this.txtBorrowedCardSearch.getText());
    }

    @FXML
    void addBorrowedCardHandler(ActionEvent event) {
        if (this.isBorrowedCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ mượn thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            BorrowedCard borrowedCard = new BorrowedCard();
            borrowedCard.setBorrowedCardId(this.txtBorrowedCardId.getText());
            borrowedCard.setReaderCard(this.cbxReaderCardId.getSelectionModel().getSelectedItem());
            borrowedCard.setEmployee(LoginController.user.getEmployee());
            borrowedCard.setBorrowedDate(Date.from(this.dpkBorrowedDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            try {
                BorrowedBookManagementServices.addBorrowedCard(borrowedCard);
                this.clearBorrowedCardDetails();
                this.loadBorrowedCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm thẻ mượn thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ mượn thất bại", "Mã thẻ mượn đã tồn tại!");
            } catch (DateTimeException e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ mượn thất bại", "Thẻ độc giả chưa có hiệu lực hoặc đã quá hạn!");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm thẻ mượn thất bại", "Độc giả chưa trả hết sách mượn!");
            }
        }
    }

    @FXML
    void deleteBorrowedCardHandler(ActionEvent event) {
        if (this.isBorrowedCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa thẻ mượn thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            try {
                BorrowedBookManagementServices.deleteBorrowedCard(this.txtBorrowedCardId.getText());
                this.clearBorrowedCardDetails();
                this.loadBorrowedCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa thẻ mượn thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa thẻ mượn thất bại", "Mã thẻ mượn đang được liên kết!");
            }
        }
    }

    @FXML
    void editBorrowedCardHandler(ActionEvent event) {
        if (this.isBorrowedCardEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ mượn thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            BorrowedCard selectedBorrowedCard = this.tbvBorrowedCard.getSelectionModel().getSelectedItem();
            BorrowedCard borrowedCard = new BorrowedCard();
            borrowedCard.setBorrowedCardId(this.txtBorrowedCardId.getText());
            borrowedCard.setReaderCard(this.cbxReaderCardId.getSelectionModel().getSelectedItem());
            borrowedCard.setEmployee(LoginController.user.getEmployee());
            borrowedCard.setBorrowedDate(Date.from(this.dpkBorrowedDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            try {
                BorrowedBookManagementServices.editBorrowedCard(selectedBorrowedCard.getBorrowedCardId(), borrowedCard);
                this.clearBorrowedCardDetails();
                this.loadBorrowedCardTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa thẻ mượn thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                if (e.getErrorCode() == 1062)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ mượn thất bại", "Mã thẻ mượn mới đã tồn tại!");
                else if (e.getErrorCode() == 1451)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ mượn thất bại", "Mã thẻ mượn đang được liên kết!");
            } catch (DateTimeException e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ mượn thất bại", "Thẻ độc giả mới chưa có hiệu lực hoặc đã quá hạn!");
            } catch (UnknownError e) {
                e.printStackTrace();
                this.clearBorrowedCardDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa thẻ mượn thất bại", "Độc giả mới chưa trả hết sách mượn!");
            }
        }
    }

    @FXML
    void clickBorrowedCardHandler(MouseEvent event) throws SQLException {
        BorrowedCard selectedBorrowedCard = this.tbvBorrowedCard.getSelectionModel().getSelectedItem();
        if (selectedBorrowedCard == null) return;
        this.txtBorrowedCardId.setText(selectedBorrowedCard.getBorrowedCardId());
        this.cbxReaderCardId.getSelectionModel().select(this.cbxReaderCardId.getItems().stream().filter(readerCard -> readerCard.getReaderCardId().equals(selectedBorrowedCard.getReaderCard().getReaderCardId())).findFirst().orElse(null));
        this.dpkBorrowedDate.setValue(LocalDate.parse(Utils.dateFormat.format(selectedBorrowedCard.getBorrowedDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.loadBorrowedCardDetailsTableData(selectedBorrowedCard.getBorrowedCardId());
    }

    @FXML
    void checkBookHandler(ActionEvent event) {
        if (this.txtBookId.getText().equals("")) {
            this.txtBookId.clear();
            this.txtStatus.clear();
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Kiểm tra sách thất bại", "Vui lòng nhập mã sách!");
        } else {

            try {
                Book book = BorrowedBookManagementServices.getBookByBookId(this.txtBookId.getText());
                if (book == null) {
                    this.txtBookId.clear();
                    this.txtStatus.clear();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Kiểm tra sách thất bại", "Không tìm thấy mã sách!");
                } else {
                    this.txtStatus.setText(book.getStatus());
                    this.bookIdTemp = book.getBookId();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void borrowBookHandler(ActionEvent event) {
        if (this.tbvBorrowedCard.getSelectionModel().getSelectedItem() == null) {
            this.clearBorrowedCardDetails();
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Mượn sách thất bại", "Vui lòng chọn mã thẻ mượn!");
        } else {
            if (this.txtBookId.getText().equals(this.bookIdTemp) && this.txtStatus.getText().equals("Sẵn sàng")) {
                BorrowedCard borrowedCard = new BorrowedCard();
                borrowedCard.setBorrowedCardId(this.txtBorrowedCardId.getText());
                borrowedCard.setReaderCard(this.cbxReaderCardId.getSelectionModel().getSelectedItem());
                borrowedCard.setEmployee(LoginController.user.getEmployee());
                borrowedCard.setBorrowedDate(Date.from(this.dpkBorrowedDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                Book book = new Book();
                book.setBookId(this.txtBookId.getText());
                book.setStatus(this.txtStatus.getText());
                BorrowedCardDetails borrowedCardDetails = new BorrowedCardDetails();
                borrowedCardDetails.setBorrowedCard(borrowedCard);
                borrowedCardDetails.setBook(book);
                borrowedCardDetails.setReturned(false);
                borrowedCardDetails.setReturnedDate(null);
                try {
                    BorrowedBookManagementServices.borrowBook(borrowedCardDetails);
                    BorrowedBookManagementServices.updateBookStatus(this.txtBookId.getText(), "Đang mượn");
                    this.txtBookId.clear();
                    this.txtStatus.clear();
                    this.loadBorrowedCardDetailsTableData(this.tbvBorrowedCard.getSelectionModel().getSelectedItem().getBorrowedCardId());
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Mượn sách thành công", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.txtBookId.clear();
                    this.txtStatus.clear();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Mượn sách thất bại", "Mã sách đã tồn tại!");
                } catch (UnknownError e) {
                    e.printStackTrace();
                    this.txtBookId.clear();
                    this.txtStatus.clear();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Mượn sách thất bại", "Chỉ được mượn tối đa 5 quyển sách!");
                }
            } else if (!this.txtBookId.getText().equals(this.bookIdTemp)) {
                this.txtBookId.clear();
                this.txtStatus.clear();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Mượn sách thất bại", "Mã sách không hợp lệ!");
            } else if (!this.txtStatus.getText().equals("Sẵn sàng")) {
                this.txtBookId.clear();
                this.txtStatus.clear();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Mượn sách thất bại", "Sách không ở trạng thái sẵn sàng!");
            }
        }

    }

    @FXML
    void backBorrowedCardHandler(ActionEvent event) throws IOException {
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
    void checkReaderCardHandler(ActionEvent event) {

    }

    @FXML
    void returnBookHandler(ActionEvent event) {

    }

    @FXML
    void lostBookHandler(ActionEvent event) {

    }

    @FXML
    void backReturnedBookHandler(ActionEvent event) throws IOException {
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
    void clickBorrowedCardDetailsHandler(MouseEvent event) {

    }

    @FXML
    void showReaderNameHandler(ActionEvent event) {
        this.txtReaderName.setText(this.cbxReaderCardId.getSelectionModel().getSelectedItem().getReader().getReaderName());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadBorrowedCardTableData(null);
            this.cbxReaderCardId.setItems(FXCollections.observableArrayList(ReaderManagementServices.getReaderCardListByKeyword(null)));
            this.cbxReaderCardId.getSelectionModel().selectFirst();
            this.txtReaderName.setText(this.cbxReaderCardId.getSelectionModel().getSelectedItem().getReader().getReaderName());
            this.txtEmployeeId.setText(LoginController.user.getEmployee().getEmployeeId());
            this.txtEmployeeName.setText(LoginController.user.getEmployee().getEmployeeName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadBorrowedCardTableData(String keyword) throws SQLException {
        this.colBorrowedCardId.setCellValueFactory(new PropertyValueFactory<>("BorrowedCardId"));
        this.colReaderCardId.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReaderCard().getReaderCardId()));
        this.colReaderName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getReaderCard().getReader().getReaderName()));
        this.colBorrowedDate.setCellValueFactory(new PropertyValueFactory<>("BorrowedDate"));
        this.tbvBorrowedCard.setItems(FXCollections.observableArrayList(BorrowedBookManagementServices.getBorrowedCardListByKeyword(keyword)));
    }

    public void loadBorrowedCardDetailsTableData(String borrowedCardId) throws SQLException {
        this.colBookId.setCellValueFactory(new PropertyValueFactory<>("Book"));
        this.colBookName.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBook().getBookName()));
        this.colStatus.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getBook().getStatus()));
        this.colReturned.setCellValueFactory(new PropertyValueFactory<>("Returned"));
        this.colReturnedDate.setCellValueFactory(new PropertyValueFactory<>("ReturnedDate"));
        this.tbvBorrowedCardDetails.setItems(FXCollections.observableArrayList(BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId(borrowedCardId)));
    }

    public void clearBorrowedCardDetails() {
        this.txtBorrowedCardId.clear();
        this.cbxReaderCardId.getSelectionModel().selectFirst();
        this.dpkBorrowedDate.setValue(null);
        this.txtBookId.clear();
        this.txtStatus.clear();
        this.tbvBorrowedCardDetails.setItems(null);
    }

    public boolean isBorrowedCardEmpty() {
        return this.txtBorrowedCardId.getText().equals("") || this.dpkBorrowedDate.getValue() == null;
    }
}
