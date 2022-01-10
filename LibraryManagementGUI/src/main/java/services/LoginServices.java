package services;

import configs.JDBCUtils;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.Employee;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServices {
    public static User getUserInformation(String username, String password) throws SQLException {
        User user = null;
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT e.*, u.* FROM employee e, user u WHERE e.EmployeeId = u.EmployeeId and Username = ? and Password = ?");
            ps.setString(1, username);
            ps.setString(2, DigestUtils.md5Hex(password));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("EmployeeId"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                employee.setBirthDay(rs.getDate("BirthDay"));
                employee.setAddress(rs.getString("Address"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                user = new User();
                user.setUserId(rs.getString("UserId"));
                user.setRole(rs.getString("Role"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmployee(employee);
            }
        }
        return user;
    }
}
