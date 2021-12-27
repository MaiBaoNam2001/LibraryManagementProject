package services;

import configs.JDBCUtils;
import pojo.Reader;
import pojo.ReaderCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

public class ReaderManagementServices {
    public static List<Reader> getReaderListByKeyword(String keyword) throws SQLException {
        List<Reader> readerList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT * FROM reader";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " WHERE ReaderId LIKE concat('%', ?, '%')";
                sqlQuery += " OR ReaderName LIKE concat('%', ?, '%')";
                sqlQuery += " OR Gender LIKE concat('%', ?, '%')";
                sqlQuery += " OR BirthDay LIKE concat('%', ?, '%')";
                sqlQuery += " OR Address LIKE concat('%', ?, '%')";
                sqlQuery += " OR Email LIKE concat('%', ?, '%')";
                sqlQuery += " OR PhoneNumber LIKE concat('%', ?, '%')";
                sqlQuery += " OR Object LIKE concat('%', ?, '%')";
                sqlQuery += " OR Department LIKE concat('%', ?, '%')";
            }
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
                ps.setString(6, keyword);
                ps.setString(7, keyword);
                ps.setString(8, keyword);
                ps.setString(9, keyword);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setReaderId(rs.getString("ReaderId"));
                reader.setReaderName(rs.getString("ReaderName"));
                reader.setGender(rs.getString("Gender"));
                reader.setBirthDay(rs.getDate("BirthDay"));
                reader.setAddress(rs.getString("Address"));
                reader.setEmail(rs.getString("Email"));
                reader.setPhoneNumber(rs.getString("PhoneNumber"));
                reader.setObject(rs.getString("Object"));
                reader.setDepartment(rs.getString("Department"));
                readerList.add(reader);
            }
        }
        return readerList;
    }

    public static void addReader(Reader reader) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reader VALUES(?,?,?,?,?,?,?,?,?)");
            ps.setString(1, reader.getReaderId());
            ps.setString(2, reader.getReaderName());
            ps.setString(3, reader.getGender());
            ps.setDate(4, new java.sql.Date(reader.getBirthDay().getTime()));
            ps.setString(5, reader.getAddress());
            ps.setString(6, reader.getEmail());
            ps.setString(7, reader.getPhoneNumber());
            ps.setString(8, reader.getObject());
            ps.setString(9, reader.getDepartment());
            ps.executeUpdate();
        }
    }

    public static void deleteReader(String readerId) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reader WHERE ReaderId = ?");
            ps.setString(1, readerId);
            ps.executeUpdate();
        }
    }

    public static void editReader(String readerId, Reader newReader) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE reader SET ReaderId = ?, ReaderName = ?, Gender = ?, BirthDay = ?, Address = ?, Email = ?, PhoneNumber = ?, Object = ?, Department = ? WHERE ReaderId = ?");
            ps.setString(1, newReader.getReaderId());
            ps.setString(2, newReader.getReaderName());
            ps.setString(3, newReader.getGender());
            ps.setDate(4, new java.sql.Date(newReader.getBirthDay().getTime()));
            ps.setString(5, newReader.getAddress());
            ps.setString(6, newReader.getEmail());
            ps.setString(7, newReader.getPhoneNumber());
            ps.setString(8, newReader.getObject());
            ps.setString(9, newReader.getDepartment());
            ps.setString(10, readerId);
            ps.executeUpdate();
        }
    }

    public static List<ReaderCard> getReaderCardListByKeyword(String keyword) throws SQLException {
        List<ReaderCard> readerCardList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT rc.*, r.* FROM reader_card rc, reader r WHERE rc.ReaderId = r.ReaderId";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " AND (ReaderCardId LIKE concat('%',?,'%')";
                sqlQuery += " OR StartDate LIKE concat('%',?,'%')";
                sqlQuery += " OR ExpirationDate LIKE concat('%',?,'%')";
                sqlQuery += " OR rc.ReaderId LIKE concat('%',?,'%')";
                sqlQuery += " OR ReaderName LIKE concat('%',?,'%'))";
            }
            PreparedStatement ps = connection.prepareStatement(sqlQuery);
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(1, keyword);
                ps.setString(2, keyword);
                ps.setString(3, keyword);
                ps.setString(4, keyword);
                ps.setString(5, keyword);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Reader reader = new Reader();
                reader.setReaderId(rs.getString("ReaderId"));
                reader.setReaderName(rs.getString("ReaderName"));
                reader.setGender(rs.getString("Gender"));
                reader.setBirthDay(rs.getDate("BirthDay"));
                reader.setAddress(rs.getString("Address"));
                reader.setEmail(rs.getString("Email"));
                reader.setPhoneNumber(rs.getString("PhoneNumber"));
                reader.setObject(rs.getString("Object"));
                reader.setDepartment(rs.getString("Department"));
                ReaderCard readerCard = new ReaderCard();
                readerCard.setReaderCardId(rs.getString("ReaderCardId"));
                readerCard.setStartDate(rs.getDate("StartDate"));
                readerCard.setExpirationDate(rs.getDate("ExpirationDate"));
                readerCard.setReader(reader);
                readerCardList.add(readerCard);
            }
        }
        return readerCardList;
    }

    public static void addReaderCard(ReaderCard readerCard) throws SQLException {
        if (readerCard.getStartDate().compareTo(readerCard.getExpirationDate()) < 0) {
            try (Connection connection = JDBCUtils.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO reader_card VALUES (?,?,?,?)");
                ps.setString(1, readerCard.getReaderCardId());
                ps.setDate(2, new java.sql.Date(readerCard.getStartDate().getTime()));
                ps.setDate(3, new java.sql.Date(readerCard.getExpirationDate().getTime()));
                ps.setString(4, readerCard.getReader().getReaderId());
                ps.executeUpdate();
            }
        } else throw new DateTimeException("Invalid Date");
    }

    public static void deleteReaderCard(String readerCardId) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM reader_card WHERE ReaderCardId = ?");
            ps.setString(1, readerCardId);
            ps.executeUpdate();
        }
    }

    public static void editReaderCard(String readerCardId, ReaderCard newReaderCard) throws SQLException {
        if (newReaderCard.getStartDate().compareTo(newReaderCard.getExpirationDate()) < 0) {
            try (Connection connection = JDBCUtils.getConnection()) {
                PreparedStatement ps = connection.prepareStatement("UPDATE reader_card SET ReaderCardId = ?, StartDate = ?, ExpirationDate = ?, ReaderId = ? WHERE ReaderCardId = ?");
                ps.setString(1, newReaderCard.getReaderCardId());
                ps.setDate(2, new java.sql.Date(newReaderCard.getStartDate().getTime()));
                ps.setDate(3, new java.sql.Date(newReaderCard.getExpirationDate().getTime()));
                ps.setString(4, newReaderCard.getReader().getReaderId());
                ps.setString(5, readerCardId);
                ps.executeUpdate();
            }
        } else throw new DateTimeException("Invalid Date");
    }
}
