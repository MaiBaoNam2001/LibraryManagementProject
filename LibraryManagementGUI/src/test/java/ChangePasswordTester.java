import org.junit.jupiter.api.*;
import pojo.User;
import services.ChangePasswordServices;
import services.EmployeeManagementServices;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ChangePasswordTester {
    private static User validUser;

    @BeforeAll
    public static void init() throws SQLException {
        validUser = EmployeeManagementServices.getUserListByKeyword("lyminhhoang").get(0);
    }

    @Test
    @Order(1)
    @Tag("ChangePassword")
    public void testChangePassword() throws SQLException {
        ChangePasswordServices.changePassword(validUser, "lyminhhoang2");
        List<User> userList = EmployeeManagementServices.getUserListByKeyword(validUser.getUsername());
        assertNotNull(userList);
        assertEquals(1, userList.size());
        assertNotEquals(validUser.getPassword(), userList.get(0).getPassword());
    }
}
