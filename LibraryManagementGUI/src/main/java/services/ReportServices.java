package services;

import configs.JDBCUtils;
import pojo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ReportServices {
    public static List<BookReport> getBookReportListByYear(String year) throws SQLException {
        int index = 0;
        List<BookReport> bookReportList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT t.TitleId, TitleName,";
            sqlQuery += " COUNT(IF(BorrowedDate LIKE concat('%',?,'%') OR (IsReturned = 0 AND current_date() LIKE concat('%',?,'%')), 1, null)) AS 'BorrowedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%',?,'%') AND Fine != 100000, 1, null)) AS 'ReturnedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%',?,'%') AND Fine = 100000, 1, null)) AS 'LostBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%',?,'%') AND Fine > 0 AND Fine != 100000, 1, null)) AS 'OverdueBookNumber'";
            sqlQuery += " FROM title t, book b, borrowed_card_detail bcd, borrowed_card bc";
            sqlQuery += " WHERE t.TitleId = b.TitleId AND b.BookId = bcd.BookId AND bcd.BorrowedCardId = bc.BorrowedCardId";
            sqlQuery += " GROUP BY t.TitleId, TitleName ORDER BY t.TitleId;";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, year);
            ps.setString(2, year);
            ps.setString(3, year);
            ps.setString(4, year);
            ps.setString(5, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookReport bookReport = new BookReport();
                bookReport.setOrder(++index);
                Title title = new Title();
                title.setTitleId(rs.getString("t.TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                bookReport.setTitle(title);
                bookReport.setBorrowedBookNumber(rs.getInt("BorrowedBookNumber"));
                bookReport.setReturnedBookNumber(rs.getInt("ReturnedBookNumber"));
                bookReport.setLostBookNumber(rs.getInt("LostBookNumber"));
                bookReport.setOverdueBookNumber(rs.getInt("OverdueBookNumber"));
                bookReportList.add(bookReport);
            }
        }
        return bookReportList;
    }

    public static List<BookReport> getBookReportListByQuarter(String quarter, String year) throws SQLException {
        String filterList;
        switch (quarter) {
            case "Một":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "01", year, "02", year, "03");
                break;
            case "Hai":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "04", year, "05", year, "06");
                break;
            case "Ba":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "07", year, "08", year, "09");
                break;
            case "Bốn":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "10", year, "11", year, "12");
                break;
            default:
                filterList = null;
        }
        int index = 0;
        List<BookReport> bookReportList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT t.TitleId, TitleName,";
            sqlQuery += " COUNT(IF(BorrowedDate REGEXP ? OR (IsReturned = 0 AND current_date() REGEXP ?), 1, null)) AS 'BorrowedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine != 100000, 1, null)) AS 'ReturnedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine = 100000, 1, null)) AS 'LostBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine > 0 AND Fine != 100000, 1, null)) AS 'OverdueBookNumber'";
            sqlQuery += " FROM title t, book b, borrowed_card_detail bcd, borrowed_card bc";
            sqlQuery += " WHERE t.TitleId = b.TitleId AND b.BookId = bcd.BookId AND bcd.BorrowedCardId = bc.BorrowedCardId";
            sqlQuery += " GROUP BY t.TitleId, TitleName ORDER BY t.TitleId;";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, filterList);
            ps.setString(2, filterList);
            ps.setString(3, filterList);
            ps.setString(4, filterList);
            ps.setString(5, filterList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BookReport bookReport = new BookReport();
                bookReport.setOrder(++index);
                Title title = new Title();
                title.setTitleId(rs.getString("t.TitleId"));
                title.setTitleName(rs.getString("TitleName"));
                bookReport.setTitle(title);
                bookReport.setBorrowedBookNumber(rs.getInt("BorrowedBookNumber"));
                bookReport.setReturnedBookNumber(rs.getInt("ReturnedBookNumber"));
                bookReport.setLostBookNumber(rs.getInt("LostBookNumber"));
                bookReport.setOverdueBookNumber(rs.getInt("OverdueBookNumber"));
                bookReportList.add(bookReport);
            }
        }
        return bookReportList;
    }

    public static List<ReaderReport> getReaderReportListByYear(String year) throws SQLException {
        int index = 0;
        List<ReaderReport> readerReportList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT r.ReaderId, ReaderName,";
            sqlQuery += " COUNT(IF(BorrowedDate LIKE concat('%', ?, '%') OR (IsReturned = 0 AND current_date() LIKE concat('%', ?, '%')), 1, null)) AS 'BorrowedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%', ?, '%') AND Fine != 100000, 1, null)) AS 'ReturnedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%', ?, '%') AND Fine = 100000, 1, null)) AS 'LostBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%', ?, '%') AND Fine > 0 AND Fine != 100000, 1, null)) AS 'OverdueBookNumber',";
            sqlQuery += " SUM(IF(IsReturned = 1 AND ReturnedDate LIKE concat('%', ?, '%') AND Fine > 0, Fine, 0)) AS 'Fine'";
            sqlQuery += " FROM reader r, reader_card rc, borrowed_card bc, borrowed_card_detail bcd, book b";
            sqlQuery += " WHERE r.ReaderId = rc.ReaderId AND rc.ReaderCardId = bc.ReaderCardId AND bc.BorrowedCardId = bcd.BorrowedCardId AND bcd.BookId = b.BookId";
            sqlQuery += " GROUP BY r.ReaderId, ReaderName ORDER BY r.ReaderId;";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, year);
            ps.setString(2, year);
            ps.setString(3, year);
            ps.setString(4, year);
            ps.setString(5, year);
            ps.setString(6, year);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReaderReport readerReport = new ReaderReport();
                readerReport.setOrder(++index);
                Reader reader = new Reader();
                reader.setReaderId(rs.getString("r.ReaderId"));
                reader.setReaderName(rs.getString("ReaderName"));
                readerReport.setReader(reader);
                readerReport.setBorrowedBookNumber(rs.getInt("BorrowedBookNumber"));
                readerReport.setReturnedBookNumber(rs.getInt("ReturnedBookNumber"));
                readerReport.setLostBookNumber(rs.getInt("LostBookNumber"));
                readerReport.setOverdueBookNumber(rs.getInt("OverdueBookNumber"));
                readerReport.setFine(rs.getDouble("Fine"));
                readerReportList.add(readerReport);
            }
        }
        return readerReportList;
    }

    public static List<ReaderReport> getReaderReportListByQuarter(String quarter, String year) throws SQLException {
        String filterList;
        switch (quarter) {
            case "Một":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "01", year, "02", year, "03");
                break;
            case "Hai":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "04", year, "05", year, "06");
                break;
            case "Ba":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "07", year, "08", year, "09");
                break;
            case "Bốn":
                filterList = String.format("%s-%s|%s-%s|%s-%s", year, "10", year, "11", year, "12");
                break;
            default:
                filterList = null;
        }
        int index = 0;
        List<ReaderReport> readerReportList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT r.ReaderId, ReaderName,";
            sqlQuery += " COUNT(IF(BorrowedDate REGEXP ? OR (IsReturned = 0 AND current_date() REGEXP ?), 1, null)) AS 'BorrowedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine != 100000, 1, null)) AS 'ReturnedBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine = 100000, 1, null)) AS 'LostBookNumber',";
            sqlQuery += " COUNT(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine > 0 AND Fine != 100000, 1, null)) AS 'OverdueBookNumber',";
            sqlQuery += " SUM(IF(IsReturned = 1 AND ReturnedDate REGEXP ? AND Fine > 0, Fine, 0)) AS 'Fine'";
            sqlQuery += " FROM reader r, reader_card rc, borrowed_card bc, borrowed_card_detail bcd, book b";
            sqlQuery += " WHERE r.ReaderId = rc.ReaderId AND rc.ReaderCardId = bc.ReaderCardId AND bc.BorrowedCardId = bcd.BorrowedCardId AND bcd.BookId = b.BookId";
            sqlQuery += " GROUP BY r.ReaderId, ReaderName ORDER BY r.ReaderId;";
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            ps.setString(1, filterList);
            ps.setString(2, filterList);
            ps.setString(3, filterList);
            ps.setString(4, filterList);
            ps.setString(5, filterList);
            ps.setString(6, filterList);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ReaderReport readerReport = new ReaderReport();
                readerReport.setOrder(++index);
                Reader reader = new Reader();
                reader.setReaderId(rs.getString("r.ReaderId"));
                reader.setReaderName(rs.getString("ReaderName"));
                readerReport.setReader(reader);
                readerReport.setBorrowedBookNumber(rs.getInt("BorrowedBookNumber"));
                readerReport.setReturnedBookNumber(rs.getInt("ReturnedBookNumber"));
                readerReport.setLostBookNumber(rs.getInt("LostBookNumber"));
                readerReport.setOverdueBookNumber(rs.getInt("OverdueBookNumber"));
                readerReport.setFine(rs.getDouble("Fine"));
                readerReportList.add(readerReport);
            }
        }
        return readerReportList;
    }
}