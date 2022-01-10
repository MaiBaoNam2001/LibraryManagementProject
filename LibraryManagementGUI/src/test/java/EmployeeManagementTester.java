import org.junit.jupiter.api.*;
import pojo.Employee;
import pojo.User;
import services.EmployeeManagementServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeManagementTester {
    private static Employee validEmployee1;
    private static Employee validEmployee2;
    private static Employee invalidEmployee1;
    private static Employee invalidEmployee2;
    private static User validUser;
    private static List<User> invalidUserList;

    @BeforeAll
    public static void init() throws ParseException, SQLException {
        validEmployee1 = new Employee("E00011", "Trần Mạnh Cường", new SimpleDateFormat("yyyy-MM-dd").parse("2000-05-05"), "Đà Nẵng", "cuong.tm@gmail.com", "0127846598");
        validEmployee2 = new Employee("E00012", "Chu Văn An", new SimpleDateFormat("yyyy-MM-dd").parse("2000-12-10"), "Nam Định", "an.cv@gmail.com", "0124789587");
        invalidEmployee1 = new Employee("E00001", "Ngô Tuấn Kiệt", new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-10"), "Nha Trang", "kiet.nt@gmail.com", "0123254681");
        invalidEmployee2 = new Employee("E00006", "Ngô Tuấn Kiệt", new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-10"), "Nha Trang", "kiet.nt@gmail.com", "0123254681");
        validUser = new User(null, null, "tranthinhuquynh", null, EmployeeManagementServices.getEmployeeListByKeyword("E00003").get(0));
        invalidUserList = new ArrayList<>();
        invalidUserList.add(new User(null, null, "nguyendinhdung2", null, EmployeeManagementServices.getEmployeeListByKeyword("E00001").get(0)));
        invalidUserList.add(new User(null, null, "nguyenthingoctram", null, EmployeeManagementServices.getEmployeeListByKeyword("E00004").get(0)));
        invalidUserList.add(new User(null, null, "lyminhhoang2", null, EmployeeManagementServices.getEmployeeListByKeyword("E00006").get(0)));
        invalidUserList.add(new User(null, null, "lyminhhoang", null, EmployeeManagementServices.getEmployeeListByKeyword("E00003").get(0)));
    }

    @Test
    @Order(1)
    @Tag("Employee")
    public void testGetEmployeeListByEmployeeId() throws SQLException {
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword("E00001");
        assertNotNull(employeeList);
        assertEquals(1, employeeList.size());
        assertEquals("E00001", employeeList.get(0).getEmployeeId());
        assertEquals("Nguyễn Đình Dũng", employeeList.get(0).getEmployeeName());
        assertEquals("dung.nd@gmail.com", employeeList.get(0).getEmail());
        assertEquals("0355987258", employeeList.get(0).getPhoneNumber());
    }

    @Test
    @Order(2)
    @Tag("Employee")
    public void testGetEmployeeListByEmployeeName() throws SQLException {
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword("Cao Thế Hùng");
        assertNotNull(employeeList);
        assertEquals(1, employeeList.size());
        assertEquals("E00002", employeeList.get(0).getEmployeeId());
        assertEquals("Cao Thế Hùng", employeeList.get(0).getEmployeeName());
        assertEquals("hung.ct@gmail.com", employeeList.get(0).getEmail());
        assertEquals("0128796541", employeeList.get(0).getPhoneNumber());
    }

    @Test
    @Order(3)
    @Tag("Employee")
    public void testGetEmployeeListByNull() throws SQLException {
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(null);
        assertNotNull(employeeList);
        assertEquals(10, employeeList.size());
        assertEquals("E00003", employeeList.get(2).getEmployeeId());
        assertEquals("Trần Thị Như Quỳnh", employeeList.get(2).getEmployeeName());
        assertEquals("E00009", employeeList.get(8).getEmployeeId());
        assertEquals("Lương Phát Đạt", employeeList.get(8).getEmployeeName());
        assertEquals(2, employeeList.stream().filter(employee -> employee.getAddress().equals("Đồng Tháp")).count());
        assertTrue(employeeList.stream().allMatch(employee -> employee.getEmail().contains("@gmail.com")));
        assertTrue(employeeList.stream().allMatch(employee -> employee.getPhoneNumber().length() == 10));
    }

    @Test
    @Order(4)
    @Tag("Employee")
    public void testGetEmployeeListByInvalidKeyword() throws SQLException {
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword("E00050");
        assertEquals(0, employeeList.size());
    }

    @Test
    @Order(5)
    @Tag("Employee")
    public void testAddValidEmployee() throws SQLException {
        EmployeeManagementServices.addEmployee(validEmployee1);
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(validEmployee1.getEmployeeId());
        assertEquals(1, employeeList.size());
        assertEquals(validEmployee1.getEmployeeId(), employeeList.get(0).getEmployeeId());
        assertEquals(validEmployee1.getEmployeeName(), employeeList.get(0).getEmployeeName());
        assertEquals(validEmployee1.getEmail(), employeeList.get(0).getEmail());
        assertEquals(validEmployee1.getPhoneNumber(), employeeList.get(0).getPhoneNumber());
    }

    @Test
    @Order(6)
    @Tag("Employee")
    public void testAddInvalidEmployee() throws SQLException {
        assertThrows(UnknownError.class, () -> {
            EmployeeManagementServices.addEmployee(invalidEmployee1);
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            EmployeeManagementServices.addEmployee(invalidEmployee2);
        });
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(null);
        assertEquals(11, employeeList.size());
    }

    @Test
    @Order(7)
    @Tag("Employee")
    public void testDeleteValidEmployee() throws SQLException {
        EmployeeManagementServices.deleteEmployee(validEmployee1.getEmployeeId());
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(validEmployee1.getEmployeeId());
        assertEquals(0, employeeList.size());
    }

    @Test
    @Order(8)
    @Tag("Employee")
    public void testDeleteInvalidEmployee() throws SQLException {
        assertThrows(UnknownError.class, () -> {
            EmployeeManagementServices.deleteEmployee(invalidEmployee1.getEmployeeId());
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            EmployeeManagementServices.deleteEmployee(invalidEmployee2.getEmployeeId());
        });
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(null);
        assertEquals(10, employeeList.size());
    }

    @Test
    @Order(9)
    @Tag("Employee")
    public void testEditValidEmployee() throws SQLException {
        EmployeeManagementServices.addEmployee(validEmployee1);
        EmployeeManagementServices.editEmployee(validEmployee1.getEmployeeId(), validEmployee2);
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(validEmployee2.getEmployeeId());
        assertEquals(1, employeeList.size());
        assertEquals(validEmployee2.getEmployeeId(), employeeList.get(0).getEmployeeId());
        assertEquals(validEmployee2.getEmployeeName(), employeeList.get(0).getEmployeeName());
        assertEquals(validEmployee2.getEmail(), employeeList.get(0).getEmail());
        assertEquals(validEmployee2.getPhoneNumber(), employeeList.get(0).getPhoneNumber());
    }

    @Test
    @Order(10)
    @Tag("User")
    public void testGetUserListByValidUsername() throws SQLException {
        List<User> userList = EmployeeManagementServices.getUserListByKeyword("lyminhhoang");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals("Người dùng", userList.get(0).getRole());
        assertEquals("lyminhhoang", userList.get(0).getUsername());
        assertEquals("8fa5365e5ff962567be6634572994a90", userList.get(0).getPassword());
        assertEquals("E00006", userList.get(0).getEmployee().getEmployeeId());
    }

    @Test
    @Order(11)
    @Tag("User")
    public void testGetUserListByRole() throws SQLException {
        List<User> userList = EmployeeManagementServices.getUserListByKeyword("Người dùng");
        assertNotNull(userList);
        assertEquals(4, userList.size());
        assertEquals("lyminhhoang", userList.get(0).getUsername());
        assertEquals("E00006", userList.get(0).getEmployee().getEmployeeId());
        assertEquals("trandinhquang", userList.get(3).getUsername());
        assertEquals("E00010", userList.get(3).getEmployee().getEmployeeId());
    }

    @Test
    @Order(12)
    @Tag("User")
    public void testGetUserListByEmployeeId() throws SQLException {
        List<User> userList = EmployeeManagementServices.getUserListByKeyword("E00002");
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals("Người dùng", userList.get(0).getRole());
        assertEquals("caothehung", userList.get(0).getUsername());
        assertEquals("25d55ad283aa400af464c76d713c07ad", userList.get(0).getPassword());
        assertEquals("E00002", userList.get(0).getEmployee().getEmployeeId());
    }

    @Test
    @Order(13)
    @Tag("User")
    public void testGetUserListByNull() throws SQLException {
        List<User> userList = EmployeeManagementServices.getUserListByKeyword(null);
        assertNotNull(userList);
        assertEquals(5, userList.size());
        assertEquals(1, userList.stream().filter(user -> user.getRole().equals("Admin")).count());
        assertEquals(4, userList.stream().filter(user -> user.getRole().equals("Người dùng")).count());
    }

    @Test
    @Order(14)
    @Tag("User")
    public void testGetUserListByInvalidKeyword() throws SQLException {
        List<User> userList = EmployeeManagementServices.getUserListByKeyword("E00050");
        assertEquals(0, userList.size());
    }

    @Test
    @Order(15)
    @Tag("User")
    public void testResetPassword() throws SQLException {
        List<User> userList1 = EmployeeManagementServices.getUserListByKeyword("E00001");
        EmployeeManagementServices.resetPassword(userList1.get(0), "nguyendinhdung2");
        List<User> userList2 = EmployeeManagementServices.getUserListByKeyword("E00001");
        assertNotEquals(userList1.get(0).getPassword(), userList2.get(0).getPassword());
    }

    @Test
    @Order(16)
    @Tag("User")
    public void testDeleteValidUser() throws SQLException {
        EmployeeManagementServices.deleteUser("E00007");
        List<User> userList = EmployeeManagementServices.getUserListByKeyword("E00007");
        assertEquals(0, userList.size());
    }

    @Test
    @Order(17)
    @Tag("User")
    public void testDeleteInvalidUser() throws SQLException {
        assertThrows(UnknownError.class, () -> {
            EmployeeManagementServices.deleteUser("E00001");
        });
        List<User> userList = EmployeeManagementServices.getUserListByKeyword(null);
        assertEquals(4, userList.size());
    }

    @Test
    @Order(18)
    @Tag("User")
    public void testEditValidUser() throws SQLException {
        EmployeeManagementServices.editUser("E00002", validUser);
        List<User> userList = EmployeeManagementServices.getUserListByKeyword(validUser.getEmployee().getEmployeeId());
        assertEquals(1, userList.size());
        assertEquals(validUser.getUsername(), userList.get(0).getUsername());
        assertEquals(validUser.getEmployee().getEmployeeId(), userList.get(0).getEmployee().getEmployeeId());
    }

    @Test
    @Order(19)
    @Tag("User")
    public void testEditInvalidUser() {
        assertThrows(UnknownError.class, () -> {
            EmployeeManagementServices.editUser("E00006", invalidUserList.get(0));
        });
        assertThrows(UnknownError.class, () -> {
            EmployeeManagementServices.editUser("E00001", invalidUserList.get(1));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            EmployeeManagementServices.editUser("E00003", invalidUserList.get(2));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            EmployeeManagementServices.editUser("E00003", invalidUserList.get(3));
        });
    }
}
