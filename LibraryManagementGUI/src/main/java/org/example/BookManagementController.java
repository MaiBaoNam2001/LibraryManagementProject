package org.example;

import configs.Utils;
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
import pojo.Title;
import services.BookManagementServices;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BookManagementController implements Initializable {

    @FXML
    private ComboBox<Title> cbxBookName;

    @FXML
    private TableColumn<Title, String> colAuthor;

    @FXML
    private TableColumn<Book, String> colBookId;

    @FXML
    private TableColumn<Book, String> colBookName;

    @FXML
    private TableColumn<Title, String> colCategory;

    @FXML
    private TableColumn<Book, String> colDescription;

    @FXML
    private TableColumn<Book, Date> colEntryDate;

    @FXML
    private TableColumn<Book, Integer> colPosition;

    @FXML
    private TableColumn<Title, String> colPublisher;

    @FXML
    private TableColumn<Book, Integer> colPublishingYear;

    @FXML
    private TableColumn<Title, Integer> colQuantity;

    @FXML
    private TableColumn<Book, String> colStatus;

    @FXML
    private TableColumn<Title, String> colTitleId;

    @FXML
    private TableColumn<Title, String> colTitleName;

    @FXML
    private DatePicker dpkEntryDate;

    @FXML
    private TableView<Book> tbvBook;

    @FXML
    private TableView<Title> tbvTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtBookSearch;

    @FXML
    private TextField txtCategory;

    @FXML
    private TextArea txtDescription;

    @FXML
    private TextField txtPublisher;

    @FXML
    private TextField txtPublishingYear;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtTitleId;

    @FXML
    private TextArea txtTitleName;

    @FXML
    private TextField txtTitleSearch;

    @FXML
    void searchTitleHandler(ActionEvent event) throws SQLException {
        this.clearTitleDetails();
        this.loadTitleTableData(this.txtTitleSearch.getText());
    }

    @FXML
    void addTitleHandler(ActionEvent event) {
        if (this.isTitleEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm đầu sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Title title = new Title();
            title.setTitleId(this.txtTitleId.getText());
            title.setTitleName(this.txtTitleName.getText());
            title.setAuthor(this.txtAuthor.getText());
            title.setPublisher(this.txtPublisher.getText());
            title.setCategory(this.txtCategory.getText());
            try {
                BookManagementServices.addTitle(title);
                this.clearTitleDetails();
                this.loadTitleTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm đầu sách thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearTitleDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm đầu sách thất bại", "Mã đầu sách đã tồn tại!");
            }
        }
    }

    @FXML
    void deleteTitleHandler(ActionEvent event) {
        if (this.isTitleEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa đầu sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            try {
                BookManagementServices.deleteTitle(this.txtTitleId.getText());
                this.clearTitleDetails();
                this.loadTitleTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa đầu sách thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearTitleDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa đầu sách thất bại", "Mã đầu sách đang được liên kết!");
            }
        }
    }

    @FXML
    void editTitleHandler(ActionEvent event) {
        if (this.isTitleEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa đầu sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Title selectedTitle = this.tbvTitle.getSelectionModel().getSelectedItem();
            Title title = new Title();
            title.setTitleId(this.txtTitleId.getText());
            title.setTitleName(this.txtTitleName.getText());
            title.setAuthor(this.txtAuthor.getText());
            title.setPublisher(this.txtPublisher.getText());
            title.setCategory(this.txtCategory.getText());
            try {
                BookManagementServices.editTitle(selectedTitle.getTitleId(), title);
                BookManagementServices.updateBookName(selectedTitle.getTitleId(), title.getTitleName());
                this.clearTitleDetails();
                this.loadTitleTableData(null);
                Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa đầu sách thành công", "");
            } catch (SQLException e) {
                e.printStackTrace();
                this.clearTitleDetails();
                if (e.getErrorCode() == 1062)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa đầu sách thất bại", "Mã đầu sách mới đã tồn tại!");
                else if (e.getErrorCode() == 1451)
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa đầu sách thất bại", "Mã đầu sách đang được liên kết!");
            }
        }
    }

    @FXML
    void backTitleHandler(ActionEvent event) throws IOException {
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
    void clickTitleHandler(MouseEvent event) {
        Title selectedTitle = this.tbvTitle.getSelectionModel().getSelectedItem();
        if (selectedTitle == null) return;
        this.txtTitleId.setText(selectedTitle.getTitleId());
        this.txtTitleName.setText(selectedTitle.getTitleName());
        this.txtAuthor.setText(selectedTitle.getAuthor());
        this.txtPublisher.setText(selectedTitle.getPublisher());
        this.txtCategory.setText(selectedTitle.getCategory());
    }

    @FXML
    void searchBookHandler(ActionEvent event) throws SQLException {
        this.clearBookDetails();
        this.loadBookTableData(this.txtBookSearch.getText());
    }

    @FXML
    void addBookHandler(ActionEvent event) {
        if (this.isBookEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            if (Integer.parseInt(this.txtQuantity.getText()) > 0) {
                try {
                    Book book = new Book();
                    book.setBookName(this.cbxBookName.getSelectionModel().getSelectedItem().toString());
                    book.setDescription(this.txtDescription.getText());
                    book.setPublishingYear(Integer.parseInt(this.txtPublishingYear.getText()));
                    book.setEntryDate(Date.from(this.dpkEntryDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    book.setTitle(this.cbxBookName.getSelectionModel().getSelectedItem());
                    for (int i = 0; i < Integer.parseInt(this.txtQuantity.getText()); i++) {
                        List<Book> bookList = BookManagementServices.getBookListByKeyword(null);
                        if (bookList.size() == 0) {
                            book.setBookId("B00001");
                            book.setPosition(1);
                        } else {
                            Book lastBook = bookList.get(bookList.size() - 1);
                            int idIndex = Integer.parseInt(lastBook.getBookId().substring(1));
                            book.setBookId(String.format("B%05d", ++idIndex));
                            int positionIndex = lastBook.getPosition();
                            book.setPosition(++positionIndex);
                        }
                        BookManagementServices.addBook(book);
                    }
                    this.clearBookDetails();
                    this.loadBookTableData(null);
                    this.updateTitleQuantity();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Thêm sách thành công", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.clearBookDetails();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm sách thất bại", "Mã sách mới đã tồn tại!");
                }
            } else {
                this.clearBookDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Thêm sách thất bại", "Số lượng sách không hợp lệ!");
            }
        }
    }

    @FXML
    void deleteBookHandler(ActionEvent event) {
        if (this.isBookEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            List<Book> bookList = this.tbvBook.getItems().stream().filter(book -> book.getTitle().getTitleId().equals(this.cbxBookName.getSelectionModel().getSelectedItem().getTitleId())).collect(Collectors.toList());
            if (Integer.parseInt(this.txtQuantity.getText()) > 0 && Integer.parseInt(this.txtQuantity.getText()) <= bookList.size()) {
                try {
                    for (int i = 0; i < Integer.parseInt(this.txtQuantity.getText()); i++) {
                        BookManagementServices.deleteBook(bookList.get(bookList.size() - 1).getBookId());
                        bookList.remove(bookList.size() - 1);
                    }
                    this.clearBookDetails();
                    this.loadBookTableData(null);
                    this.updateTitleQuantity();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Xóa sách thành công", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.clearBookDetails();
                    Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa sách thất bại", "Mã sách đang được liên kết!");
                }
            } else {
                this.clearBookDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Xóa sách thất bại", "Số lượng sách không hợp lệ!");
            }
        }
    }

    @FXML
    void editBookHandler(ActionEvent event) {
        if (this.isBookEmpty())
            Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa sách thất bại", "Vui lòng nhập đầy đủ thông tin!");
        else {
            Book selectedBook = this.tbvBook.getSelectionModel().getSelectedItem();
            List<Book> bookList = this.tbvBook.getItems().stream().filter(book -> book.getTitle().getTitleId().equals(selectedBook.getTitle().getTitleId())).collect(Collectors.toList());
            if (Integer.parseInt(this.txtQuantity.getText()) > 0 && Integer.parseInt(this.txtQuantity.getText()) <= bookList.size()) {
                try {
                    Book book = new Book();
                    book.setBookName(this.cbxBookName.getSelectionModel().getSelectedItem().toString());
                    book.setDescription(this.txtDescription.getText());
                    book.setPublishingYear(Integer.parseInt(this.txtPublishingYear.getText()));
                    book.setEntryDate(Date.from(this.dpkEntryDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    book.setTitle(this.cbxBookName.getSelectionModel().getSelectedItem());
                    for (int i = 0; i < Integer.parseInt(this.txtQuantity.getText()); i++) {
                        BookManagementServices.editBook(bookList.get(bookList.size() - 1).getBookId(), book);
                        bookList.remove(bookList.size() - 1);
                    }
                    this.clearBookDetails();
                    this.loadBookTableData(null);
                    this.updateTitleQuantity();
                    Utils.showAlert(Alert.AlertType.INFORMATION, "Thông báo", "Sửa sách thành công", "");
                } catch (SQLException e) {
                    e.printStackTrace();
                    this.clearBookDetails();
                    if (e.getErrorCode() == 1062)
                        Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa sách thất bại", "Mã sách mới đã tồn tại!");
                    else if (e.getErrorCode() == 1451)
                        Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa sách thất bại", "Mã sách đang được liên kết!");
                }
            } else {
                this.clearBookDetails();
                Utils.showAlert(Alert.AlertType.ERROR, "Thông báo", "Sửa sách thất bại", "Số lượng sách không hợp lệ!");
            }
        }
    }

    @FXML
    void backBookHandler(ActionEvent event) throws IOException {
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
    void clickBookHandler(MouseEvent event) {
        Book selectedBook = this.tbvBook.getSelectionModel().getSelectedItem();
        if (selectedBook == null) return;
        this.cbxBookName.getSelectionModel().select(this.cbxBookName.getItems().stream().filter(title -> title.getTitleId().equals(selectedBook.getTitle().getTitleId())).findFirst().orElse(null));
        this.txtDescription.setText(selectedBook.getDescription());
        this.txtPublishingYear.setText(String.valueOf(selectedBook.getPublishingYear()));
        this.dpkEntryDate.setValue(LocalDate.parse(Utils.dateFormat.format(selectedBook.getEntryDate()), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.txtQuantity.setText(String.valueOf(this.tbvBook.getItems().stream().filter(book -> book.getTitle().getTitleId().equals(selectedBook.getTitle().getTitleId())).count()));
    }

    @FXML
    void changeTagHandler(MouseEvent event) throws SQLException {
        this.txtTitleSearch.clear();
        this.txtBookSearch.clear();
        this.clearTitleDetails();
        this.clearBookDetails();
        this.loadBookTableData(null);
        this.cbxBookName.setItems(FXCollections.observableArrayList(BookManagementServices.getTitleListByKeyword(null)));
        this.cbxBookName.getSelectionModel().selectFirst();
        this.loadTitleTableData(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.loadTitleTableData(null);
            this.loadBookTableData(null);
            this.cbxBookName.setItems(FXCollections.observableArrayList(BookManagementServices.getTitleListByKeyword(null)));
            this.cbxBookName.getSelectionModel().selectFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTitleTableData(String keyword) throws SQLException {
        this.colTitleId.setCellValueFactory(new PropertyValueFactory<>("TitleId"));
        this.colTitleName.setCellValueFactory(new PropertyValueFactory<>("TitleName"));
        this.colAuthor.setCellValueFactory(new PropertyValueFactory<>("Author"));
        this.colPublisher.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        this.colCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        this.colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        this.tbvTitle.setItems(FXCollections.observableArrayList(BookManagementServices.getTitleListByKeyword(keyword)));
    }

    public void clearTitleDetails() {
        this.txtTitleId.clear();
        this.txtTitleName.clear();
        this.txtAuthor.clear();
        this.txtPublisher.clear();
        this.txtCategory.clear();
    }

    public boolean isTitleEmpty() {
        return this.txtTitleId.getText().equals("") || this.txtTitleName.equals("") || this.txtAuthor.equals("")
                || this.txtPublisher.getText().equals("") || this.txtCategory.equals("");
    }

    public void loadBookTableData(String keyword) throws SQLException {
        this.colBookId.setCellValueFactory(new PropertyValueFactory<>("BookId"));
        this.colBookName.setCellValueFactory(new PropertyValueFactory<>("BookName"));
        this.colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        this.colPublishingYear.setCellValueFactory(new PropertyValueFactory<>("PublishingYear"));
        this.colEntryDate.setCellValueFactory(new PropertyValueFactory<>("EntryDate"));
        this.colPosition.setCellValueFactory(new PropertyValueFactory<>("Position"));
        this.colStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        this.tbvBook.setItems(FXCollections.observableArrayList(BookManagementServices.getBookListByKeyword(keyword)));
    }

    public void clearBookDetails() {
        this.cbxBookName.getSelectionModel().selectFirst();
        this.txtDescription.clear();
        this.txtPublishingYear.clear();
        this.dpkEntryDate.setValue(null);
        this.txtQuantity.clear();
    }

    public boolean isBookEmpty() {
        return this.txtDescription.getText().equals("") || this.txtPublishingYear.getText().equals("")
                || this.dpkEntryDate.getValue() == null || this.txtQuantity.getText().equals("");
    }

    public void updateTitleQuantity() throws SQLException {
        for (Title title : this.tbvTitle.getItems()) {
            int quantity = (int) this.tbvBook.getItems().stream().filter(book -> book.getTitle().getTitleId().equals(title.getTitleId())).count();
            BookManagementServices.updateTitleQuantity(title.getTitleId(), quantity);
        }
    }
}

