package services;

import configs.JDBCUtils;
import pojo.Book;
import pojo.Title;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManagementServices {
    public static List<Title> getTitleListByKeyword(String keyword) throws SQLException {
        List<Title> titleList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT * FROM title";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " WHERE TitleId LIKE concat('%',?,'%')";
                sqlQuery += " OR TitleName LIKE concat('%',?,'%')";
                sqlQuery += " OR Author LIKE concat('%',?,'%')";
                sqlQuery += " OR Publisher LIKE concat('%',?,'%')";
                sqlQuery += " OR Category LIKE concat('%',?,'%')";
                sqlQuery += " OR Quantity LIKE concat('%',?,'%')";
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
                Title title = new Title();
                title.setTitleId(rs.getString("TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                title.setAuthor(rs.getString("Author"));
                title.setPublisher(rs.getString("Publisher"));
                title.setCategory(rs.getString("Category"));
                title.setQuantity(rs.getInt("Quantity"));
                titleList.add(title);
            }
        }
        return titleList;
    }

    public static void addTitle(Title title) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO title(TitleId, TitleName, Author, Publisher, Category) VALUES(?,?,?,?,?)");
            ps.setString(1, title.getTitleId());
            ps.setString(2, title.getTitleName());
            ps.setString(3, title.getAuthor());
            ps.setString(4, title.getPublisher());
            ps.setString(5, title.getCategory());
            ps.executeUpdate();
        }
    }

    public static void deleteTitle(String titleId) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM title WHERE TitleId = ?");
            ps.setString(1, titleId);
            ps.executeUpdate();
        }
    }

    public static void editTitle(String titleId, Title newTitle) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE title SET TitleId = ?, TitleName = ?, Author = ?, Publisher = ?, Category = ? WHERE TitleId = ?");
            ps.setString(1, newTitle.getTitleId());
            ps.setString(2, newTitle.getTitleName());
            ps.setString(3, newTitle.getAuthor());
            ps.setString(4, newTitle.getPublisher());
            ps.setString(5, newTitle.getCategory());
            ps.setString(6, titleId);
            ps.executeUpdate();
        }
    }

    public static void updateBookName(String titleId, String titleName) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE book SET BookName = ? WHERE TitleId = ?");
            ps.setString(1, titleName);
            ps.setString(2, titleId);
            ps.executeUpdate();
        }
    }

    public static List<Book> getBookListByKeyword(String keyword) throws SQLException {
        List<Book> bookList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT t.*, b.* FROM title t, book b WHERE t.TitleId = b.TitleId";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " AND (BookId LIKE concat('%',?,'%')";
                sqlQuery += " OR BookName LIKE concat('%',?,'%')";
                sqlQuery += " OR Description LIKE concat('%',?,'%')";
                sqlQuery += " OR PublishingYear LIKE concat('%',?,'%')";
                sqlQuery += " OR EntryDate LIKE concat('%',?,'%')";
                sqlQuery += " OR Position LIKE concat('%',?,'%'))";
            }
            sqlQuery += " ORDER BY BookId ASC";
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
                Book book = new Book();
                book.setBookId(rs.getString("BookId"));
                book.setBookName(rs.getString("BookName"));
                book.setDescription(rs.getString("Description"));
                book.setPublishingYear(rs.getInt("PublishingYear"));
                book.setEntryDate(rs.getDate("EntryDate"));
                book.setPosition(rs.getInt("Position"));
                Title title = new Title();
                title.setTitleId(rs.getString("TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                title.setAuthor(rs.getString("Author"));
                title.setPublisher(rs.getString("Publisher"));
                title.setCategory(rs.getString("Category"));
                title.setQuantity(rs.getInt("Quantity"));
                book.setTitle(title);
                bookList.add(book);
            }
        }
        return bookList;
    }

    public static void updateTitleQuantity(String titleId, int quantity) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE title SET Quantity = ? WHERE TitleId = ?");
            ps.setInt(1, quantity);
            ps.setString(2, titleId);
            ps.executeUpdate();
        }
    }

    public static void addBook(Book book) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO book VALUES(?,?,?,?,?,?,?)");
            ps.setString(1, book.getBookId());
            ps.setString(2, book.getBookName());
            ps.setString(3, book.getDescription());
            ps.setInt(4, book.getPublishingYear());
            ps.setDate(5, new java.sql.Date(book.getEntryDate().getTime()));
            ps.setInt(6, book.getPosition());
            ps.setString(7, book.getTitle().getTitleId());
            ps.executeUpdate();
        }
    }

    public static void deleteBook(String bookId) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM book WHERE BookId = ?");
            ps.setString(1, bookId);
            ps.executeUpdate();
        }
    }

    public static void editBook(String bookId, Book newBook) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE book SET BookId = ?, BookName = ?, Description = ?, PublishingYear = ?, EntryDate = ?, Position = ?, TitleId = ? WHERE BookId = ?");
            ps.setString(1, newBook.getBookId());
            ps.setString(2, newBook.getBookName());
            ps.setString(3, newBook.getDescription());
            ps.setInt(4, newBook.getPublishingYear());
            ps.setDate(5, new java.sql.Date(newBook.getEntryDate().getTime()));
            ps.setInt(6, newBook.getPosition());
            ps.setString(7, newBook.getTitle().getTitleId());
            ps.setString(8, bookId);
            ps.executeUpdate();
        }
    }
}
