package services;

import configs.JDBCUtils;
import pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookManagementServices {
    public static List<BorrowedCard> getBorrowedCardListByKeyword(String keyword) throws SQLException {
        List<BorrowedCard> borrowedCardList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT bc.*, rc.*, e.*, r.* FROM borrowed_card bc, reader_card rc, employee e, reader r WHERE bc.ReaderCardId = rc.ReaderCardId AND bc.EmployeeId = e.EmployeeId AND rc.ReaderId = r.ReaderId";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " AND (BorrowedCardId LIKE concat('%',?,'%')";
                sqlQuery += " OR bc.ReaderCardId LIKE concat('%',?,'%')";
                sqlQuery += " OR ReaderName LIKE concat('%',?,'%')";
                sqlQuery += " OR bc.EmployeeId LIKE concat('%',?,'%')";
                sqlQuery += " OR EmployeeName LIKE concat('%',?,'%')";
                sqlQuery += " OR BorrowedDate LIKE concat('%',?,'%'))";
            }
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
                ps.setString(6, keyword);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowedCard borrowedCard = new BorrowedCard();
                borrowedCard.setBorrowedCardId(rs.getString("BorrowedCardId"));
                Reader reader = new Reader();
                reader.setReaderId(rs.getString("ReaderId"));
                reader.setReaderName(rs.getString("ReaderName"));
                reader.setGender(rs.getString("Gender"));
                reader.setBirthDay(rs.getDate("r.BirthDay"));
                reader.setAddress(rs.getString("r.Address"));
                reader.setEmail(rs.getString("r.Email"));
                reader.setPhoneNumber(rs.getString("r.PhoneNumber"));
                reader.setObject(rs.getString("Object"));
                reader.setDepartment(rs.getString("Department"));
                ReaderCard readerCard = new ReaderCard();
                readerCard.setReaderCardId(rs.getString("ReaderCardId"));
                readerCard.setStartDate(rs.getDate("StartDate"));
                readerCard.setExpirationDate(rs.getDate("ExpirationDate"));
                readerCard.setReader(reader);
                borrowedCard.setReaderCard(readerCard);
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("EmployeeId"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                employee.setBirthDay(rs.getDate("e.BirthDay"));
                employee.setAddress(rs.getString("e.Address"));
                employee.setEmail(rs.getString("e.Email"));
                employee.setPhoneNumber(rs.getString("e.PhoneNumber"));
                borrowedCard.setEmployee(employee);
                borrowedCard.setBorrowedDate(rs.getDate("BorrowedDate"));
                borrowedCardList.add(borrowedCard);
            }
        }
        return borrowedCardList;
    }

    public static List<BorrowedCardDetails> getBorrowedCardDetailsListByBorrowedCardId(String borrowedCardId) throws SQLException {
        List<BorrowedCardDetails> borrowedCardDetailsList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT bcd.*, b.*, t.* FROM borrowed_card_detail bcd, book b, title t WHERE bcd.BookId = b.BookId AND b.TitleId = t.TitleId AND BorrowedCardId = ?");
            ps.setString(1, borrowedCardId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BorrowedCardDetails borrowedCardDetails = new BorrowedCardDetails();
                BorrowedCard borrowedCard = new BorrowedCard();
                borrowedCard.setBorrowedCardId(rs.getString("BorrowedCardId"));
                borrowedCardDetails.setBorrowedCard(borrowedCard);
                Book book = new Book();
                book.setBookId(rs.getString("b.BookId"));
                book.setBookName(rs.getString("BookName"));
                book.setDescription(rs.getString("Description"));
                book.setPublishingYear(rs.getInt("PublishingYear"));
                book.setEntryDate(rs.getDate("EntryDate"));
                book.setPosition(rs.getInt("Position"));
                book.setStatus(rs.getString("Status"));
                Title title = new Title();
                title.setTitleId(rs.getString("TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                title.setAuthor(rs.getString("Author"));
                title.setPublisher(rs.getString("Publisher"));
                title.setCategory(rs.getString("Category"));
                title.setQuantity(rs.getInt("Quantity"));
                book.setTitle(title);
                borrowedCardDetails.setBook(book);
                borrowedCardDetails.setReturned(rs.getBoolean("IsReturned"));
                borrowedCardDetails.setReturnedDate(rs.getDate("ReturnedDate"));
                borrowedCardDetailsList.add(borrowedCardDetails);
            }
        }
        return borrowedCardDetailsList;
    }

    public static int getBorrowedBooksNumberByReaderCardId(String readerCardId) throws SQLException {
        int borrowedBooksNumber = -1;
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT count(BookId) as BorrowedBooksNumber FROM reader_card rc, borrowed_card bc, borrowed_card_detail bcd WHERE rc.ReaderCardId = bc.ReaderCardId AND bc.BorrowedCardId = bcd.BorrowedCardId AND IsReturned = 0 AND rc.ReaderCardId = ?");
            ps.setString(1, readerCardId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                borrowedBooksNumber = rs.getInt("BorrowedBooksNumber");
            }
        }
        return borrowedBooksNumber;
    }

    public static void addBorrowedCard(BorrowedCard borrowedCard) throws SQLException {
        if (borrowedCard.getReaderCard().getStartDate().compareTo(borrowedCard.getBorrowedDate()) > 0 || borrowedCard.getBorrowedDate().compareTo(borrowedCard.getReaderCard().getExpirationDate()) > 0)
            throw new DateTimeException("Invalid Date");
        if (getBorrowedBooksNumberByReaderCardId(borrowedCard.getReaderCard().getReaderCardId()) != 0)
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO borrowed_card VALUES (?,?,?,?)");
            ps.setString(1, borrowedCard.getBorrowedCardId());
            ps.setString(2, borrowedCard.getReaderCard().getReaderCardId());
            ps.setString(3, borrowedCard.getEmployee().getEmployeeId());
            ps.setDate(4, new java.sql.Date(borrowedCard.getBorrowedDate().getTime()));
            ps.executeUpdate();
        }
    }

    public static void deleteBorrowedCard(String borrowedCardId) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM borrowed_card WHERE BorrowedCardId = ?");
            ps.setString(1, borrowedCardId);
            ps.executeUpdate();
        }
    }

    public static void editBorrowedCard(String borrowedCardId, BorrowedCard newBorrowedCard) throws SQLException {
        if (newBorrowedCard.getReaderCard().getStartDate().compareTo(newBorrowedCard.getBorrowedDate()) > 0 || newBorrowedCard.getBorrowedDate().compareTo(newBorrowedCard.getReaderCard().getExpirationDate()) > 0)
            throw new DateTimeException("Invalid Date");
        if (getBorrowedBooksNumberByReaderCardId(newBorrowedCard.getReaderCard().getReaderCardId()) != 0)
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE borrowed_card SET BorrowedCardId = ?, ReaderCardId = ?, EmployeeId = ?, BorrowedDate = ? WHERE BorrowedCardId = ?");
            ps.setString(1, newBorrowedCard.getBorrowedCardId());
            ps.setString(2, newBorrowedCard.getReaderCard().getReaderCardId());
            ps.setString(3, newBorrowedCard.getEmployee().getEmployeeId());
            ps.setDate(4, new java.sql.Date(newBorrowedCard.getBorrowedDate().getTime()));
            ps.setString(5, borrowedCardId);
            ps.executeUpdate();
        }
    }

    public static Book getBookByBookId(String bookId) throws SQLException {
        Book book = null;
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT t.*, b.* FROM title t, book b WHERE t.TitleId = b.TitleId AND BookId = ?");
            ps.setString(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                book = new Book();
                book.setBookId(rs.getString("BookId"));
                book.setBookName(rs.getString("BookName"));
                book.setDescription(rs.getString("Description"));
                book.setPublishingYear(rs.getInt("PublishingYear"));
                book.setEntryDate(rs.getDate("EntryDate"));
                book.setPosition(rs.getInt("Position"));
                book.setStatus(rs.getString("Status"));
                Title title = new Title();
                title.setTitleId(rs.getString("TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                title.setAuthor(rs.getString("Author"));
                title.setPublisher(rs.getString("Publisher"));
                title.setCategory(rs.getString("Category"));
                title.setQuantity(rs.getInt("Quantity"));
                book.setTitle(title);
            }
        }
        return book;
    }

    public static void borrowBook(BorrowedCardDetails borrowedCardDetails) throws SQLException {
        if (getBorrowedBooksNumberByReaderCardId(borrowedCardDetails.getBorrowedCard().getReaderCard().getReaderCardId()) >= 5)
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO borrowed_card_detail VALUES (?,?,?,?)");
            ps.setString(1, borrowedCardDetails.getBorrowedCard().getBorrowedCardId());
            ps.setString(2, borrowedCardDetails.getBook().getBookId());
            ps.setBoolean(3, borrowedCardDetails.isReturned());
            ps.setDate(4, null);
            ps.executeUpdate();
        }
    }

    public static void updateBookStatus(String bookId, String newStatus) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE book SET Status = ? WHERE BookId = ?");
            ps.setString(1, newStatus);
            ps.setString(2, bookId);
            ps.executeUpdate();
        }
    }
}
