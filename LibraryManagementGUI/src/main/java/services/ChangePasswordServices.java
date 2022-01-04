package services;

import configs.JDBCUtils;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChangePasswordServices {
    public static void changePassword(User user, String newPassword) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE user SET Password = ? WHERE EmployeeId = ? AND Password = ?");
            ps.setString(1, DigestUtils.md5Hex(newPassword));
            ps.setString(2, user.getEmployee().getEmployeeId());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
        }
    }
}
