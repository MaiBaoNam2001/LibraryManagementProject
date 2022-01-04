package services;

import configs.JDBCUtils;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterServices {
    public static void addUser(User user) throws SQLException {
        if (user.getRole().equals("Admin"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user(Role, Username, Password, EmployeeId) VALUES (?,?,?,?)");
            ps.setString(1, user.getRole());
            ps.setString(2, user.getUsername());
            ps.setString(3, DigestUtils.md5Hex(user.getPassword()));
            ps.setString(4, user.getEmployee().getEmployeeId());
            ps.executeUpdate();
        }
    }
}
