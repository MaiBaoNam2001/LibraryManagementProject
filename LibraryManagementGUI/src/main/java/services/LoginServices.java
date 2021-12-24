package services;

import configs.JDBCUtils;
import pojo.Employee;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServices {
    public static Employee getUserInformation(String username, String password) throws SQLException {
        Employee employee = null;
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT e.*, u.* FROM employee e, user u WHERE e.UserId = u.UserId and Username = ? and Password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                employee = new Employee();
                employee.setEmployeeId(rs.getString("EmployeeId"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                employee.setBirthDay(rs.getDate("BirthDay"));
                employee.setAddress(rs.getString("Address"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                User user = new User();
                user.setUserId(rs.getString("UserId"));
                user.setRole(rs.getString("Role"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                employee.setUser(user);
            }
        }
        return employee;
    }
}
