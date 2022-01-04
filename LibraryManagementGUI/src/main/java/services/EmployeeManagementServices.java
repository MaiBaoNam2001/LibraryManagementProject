package services;

import configs.JDBCUtils;
import org.apache.commons.codec.digest.DigestUtils;
import pojo.Employee;
import pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManagementServices {
    public static List<Employee> getEmployeeListByKeyword(String keyword) throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT * FROM employee";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " WHERE EmployeeId LIKE concat('%',?,'%')";
                sqlQuery += " OR EmployeeName LIKE concat('%',?,'%')";
                sqlQuery += " OR BirthDay LIKE concat('%',?,'%')";
                sqlQuery += " OR Address LIKE concat('%',?,'%')";
                sqlQuery += " OR Email LIKE concat('%',?,'%')";
                sqlQuery += " OR PhoneNumber LIKE concat('%',?,'%')";
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
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("EmployeeId"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                employee.setBirthDay(rs.getDate("BirthDay"));
                employee.setAddress(rs.getString("Address"));
                employee.setEmail(rs.getString("Email"));
                employee.setPhoneNumber(rs.getString("PhoneNumber"));
                employeeList.add(employee);
            }
        }
        return employeeList;
    }

    public static void addEmployee(Employee employee) throws SQLException {
        if (employee.getEmployeeId().equals("E00001"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO employee VALUES (?,?,?,?,?,?)");
            ps.setString(1, employee.getEmployeeId());
            ps.setString(2, employee.getEmployeeName());
            ps.setDate(3, new java.sql.Date(employee.getBirthDay().getTime()));
            ps.setString(4, employee.getAddress());
            ps.setString(5, employee.getEmail());
            ps.setString(6, employee.getPhoneNumber());
            ps.executeUpdate();
        }
    }

    public static void deleteEmployee(String employeeId) throws SQLException {
        if (employeeId.equals("E00001"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM employee WHERE EmployeeId = ?");
            ps.setString(1, employeeId);
            ps.executeUpdate();
        }
    }

    public static void editEmployee(String employeeId, Employee newEmployee) throws SQLException {
        if (employeeId.equals("E00001") || newEmployee.getEmployeeId().equals("E00001"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE employee SET EmployeeId = ?, EmployeeName = ?, BirthDay = ?, Address = ?, Email = ?, PhoneNumber = ? WHERE EmployeeId = ?");
            ps.setString(1, newEmployee.getEmployeeId());
            ps.setString(2, newEmployee.getEmployeeName());
            ps.setDate(3, new java.sql.Date(newEmployee.getBirthDay().getTime()));
            ps.setString(4, newEmployee.getAddress());
            ps.setString(5, newEmployee.getEmail());
            ps.setString(6, newEmployee.getPhoneNumber());
            ps.setString(7, employeeId);
            ps.executeUpdate();
        }
    }

    public static List<User> getUserListByKeyword(String keyword) throws SQLException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = JDBCUtils.getConnection()) {
            String sqlQuery = "SELECT e.*, u.* FROM employee e, user u WHERE e.EmployeeId = u.EmployeeId";
            if (keyword != null && !keyword.isEmpty()) {
                sqlQuery += " AND (e.EmployeeId LIKE concat('%',?,'%')";
                sqlQuery += " OR EmployeeName LIKE concat('%',?,'%')";
                sqlQuery += " OR Role LIKE concat('%',?,'%')";
                sqlQuery += " OR Username LIKE concat('%',?,'%')";
                sqlQuery += " OR Password LIKE concat('%',?,'%'))";
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
                Employee employee = new Employee();
                employee.setEmployeeId(rs.getString("e.EmployeeId"));
                employee.setEmployeeName(rs.getString("EmployeeName"));
                User user = new User();
                user.setEmployee(employee);
                user.setRole(rs.getString("Role"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                userList.add(user);
            }
        }
        return userList;
    }

    public static void resetPassword(User user, String newPassword) throws SQLException {
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE user SET Password = ? WHERE EmployeeId = ? AND Username = ?");
            ps.setString(1, DigestUtils.md5Hex(newPassword));
            ps.setString(2, user.getEmployee().getEmployeeId());
            ps.setString(3, user.getUsername());
            ps.executeUpdate();
        }
    }

    public static void deleteUser(String employeeId) throws SQLException {
        if (employeeId.equals("E00001"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE EmployeeId = ?");
            ps.setString(1, employeeId);
            ps.executeUpdate();
        }
    }

    public static void editUser(String employeeId, User newUser) throws SQLException {
        if (employeeId.equals("E00001") || newUser.getEmployee().getEmployeeId().equals("E00001"))
            throw new UnknownError();
        try (Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("UPDATE user SET EmployeeId = ?, Username = ? WHERE EmployeeId = ?");
            ps.setString(1, newUser.getEmployee().getEmployeeId());
            ps.setString(2, newUser.getUsername());
            ps.setString(3, employeeId);
            ps.executeUpdate();
        }
    }
}
