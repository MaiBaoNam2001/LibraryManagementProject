import org.junit.jupiter.api.*;
import pojo.User;
import services.LoginServices;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTester {
    @Test
    @Order(1)
    @Tag("Login")
    public void testGetValidUserInformation() throws SQLException {
        User user = LoginServices.getUserInformation("trandinhquang", "trandinhquang");
        assertNotNull(user);
        assertEquals("52", user.getUserId());
        assertEquals("Người dùng", user.getRole());
        assertEquals("trandinhquang", user.getUsername());
        assertNotEquals("trandinhquang", user.getPassword());
        assertEquals("E00010", user.getEmployee().getEmployeeId());
        assertEquals("Trần Đình Quang", user.getEmployee().getEmployeeName());
        assertEquals("quang.td@gmail.com", user.getEmployee().getEmail());
        assertEquals("0285463219", user.getEmployee().getPhoneNumber());
    }

    @Test
    @Order(2)
    @Tag("Login")
    public void testGetInvalidUserInformation() throws SQLException {
        User user = LoginServices.getUserInformation("unknownuser", "unknownuser");
        assertNull(user);
    }
}
