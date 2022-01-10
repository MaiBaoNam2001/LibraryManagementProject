import org.junit.jupiter.api.*;
import pojo.Employee;
import pojo.User;
import services.EmployeeManagementServices;
import services.RegisterServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterTester {
    private static User validUser;
    private static List<User> invalidUserList;
    private static Employee invalidEmployee;

    @BeforeAll
    public static void init() throws SQLException, ParseException {
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword(null);
        validUser = new User(null, "Người dùng", "nguyenthituyetngan", "nguyenthituyetngan", employeeList.get(7));
        invalidEmployee = new Employee("E00050", "Ngô Thanh Tuấn", new SimpleDateFormat("yyyy-MM-dd").parse("1999-12-20"), "Gia Lai", "tuan.nt@gmail.com", "0128745349");
        invalidUserList = new ArrayList<>();
        invalidUserList.add(new User(null, "Admin", "nguyenthingoctram", "nguyenthingoctram", employeeList.get(3)));
        invalidUserList.add(new User(null, "Người dùng", "lyminhhoang", "nguyenthingoctram", employeeList.get(3)));
        invalidUserList.add(new User(null, "Người dùng", "nguyenthingoctram", "nguyenthingoctram", employeeList.get(5)));
        invalidUserList.add(new User(null, "Người dùng", "ngothanhtuan", "ngothanhtuan", invalidEmployee));
    }

    @Test
    @Order(1)
    @Tag("Register")
    public void testAddValidUser() throws SQLException {
        RegisterServices.addUser(validUser);
        List<User> userList = EmployeeManagementServices.getUserListByKeyword(validUser.getUsername());
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertEquals(validUser.getUsername(), userList.get(0).getUsername());
        assertNotEquals(validUser.getPassword(), userList.get(0).getPassword());
        assertEquals(validUser.getRole(), userList.get(0).getRole());
        assertEquals(validUser.getEmployee().getEmployeeId(), userList.get(0).getEmployee().getEmployeeId());
        assertEquals(validUser.getEmployee().getEmployeeName(), userList.get(0).getEmployee().getEmployeeName());
    }

    @Test
    @Order(2)
    @Tag("Register")
    public void testAddInvalidUser() throws SQLException {
        assertThrows(UnknownError.class, () -> {
            RegisterServices.addUser(invalidUserList.get(0));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            RegisterServices.addUser(invalidUserList.get(1));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            RegisterServices.addUser(invalidUserList.get(2));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            RegisterServices.addUser(invalidUserList.get(3));
        });
    }
}
