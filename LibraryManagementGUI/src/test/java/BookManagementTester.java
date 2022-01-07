import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pojo.Book;
import pojo.Title;
import services.BookManagementServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookManagementTester {
    private static Title validTitle1;
    private static Title validTitle2;
    private static Title invalidTitle;

    @BeforeAll
    public static void init() {
        validTitle1 = new Title("T00010", "Dế mèn phiêu lưu ký", "Tô Hoài", "Nhà xuất bản báo Tân Dân", "Thiếu nhi", 0);
        validTitle2 = new Title("T00011", "Góc sân và khoảng trời", "Trần Đăng Khoa", "Nhà xuất bản Kim Đồng", "Tiểu thuyết", 0);
        invalidTitle = new Title("T00001", "Đoàn binh Tây Tiến", "Quang Dũng", "Nhà xuất bản Kim Đồng", "Hồi ký", 0);
    }

    // Search title by keyword
    @Test
    @Tag("Title")
    public void testGetTitleListByTitleId() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword("T00001");
        assertNotNull(titleList);
        assertEquals(1, titleList.size());
        assertEquals("Tôi thấy hoa vàng trên cỏ xanh", titleList.get(0).getTitleName());
        assertEquals("Tiểu thuyết", titleList.get(0).getCategory());
        assertEquals(6, titleList.get(0).getQuantity());
    }

    @Test
    @Tag("Title")
    public void testGetTitleListByTitleName() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword("Mắt biếc");
        assertNotNull(titleList);
        assertEquals(1, titleList.size());
        assertEquals("T00009", titleList.get(0).getTitleId());
        assertEquals("Nguyễn Nhật Ánh", titleList.get(0).getAuthor());
        assertEquals("Nhà xuất bản Trẻ", titleList.get(0).getPublisher());
    }

    @Test
    @Tag("Title")
    public void testGetTitleListByAuthorName() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword("Nguyễn Nhật Ánh");
        assertNotNull(titleList);
        assertEquals(2, titleList.size());
        assertNotEquals("Doraemon", titleList.get(0).getTitleName());
        assertNotEquals("Tôi thấy hoa vàng trên cỏ xanh", titleList.get(1).getTitleName());
    }

    @Test
    @Tag("Title")
    public void testGetTitleByCategory() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword("Thiếu nhi");
        assertNotNull(titleList);
        assertEquals(3, titleList.size());
        assertEquals("Shin - Cậu bé bút chì", titleList.get(1).getTitleName());
        assertEquals("Tô Hoài", titleList.get(2).getAuthor());
    }

    @Test
    @Tag("Title")
    public void testGetTitleByNull() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(null);
        assertNotNull(titleList);
        assertEquals(7, titleList.size());
        assertEquals(23, titleList.stream().mapToInt(value -> value.getQuantity()).sum());
        assertEquals(3, titleList.stream().filter(title -> title.getCategory().equals("Thiếu nhi")).count());
    }

    @Test
    @Tag("Title")
    public void testGetTitleByInvalidKeyword() throws SQLException {
        List<Title> titleList = BookManagementServices.getTitleListByKeyword("Xuân Quỳnh");
        assertEquals(0, titleList.size());
    }

    // Add title
    @Test
    @Tag("Title")
    public void testAddValidTitle() throws SQLException {
        BookManagementServices.addTitle(validTitle1);
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(validTitle1.getTitleId());
        assertEquals(1, titleList.size());
        assertEquals(validTitle1.getTitleId(), titleList.get(0).getTitleId());
        assertEquals(validTitle1.getTitleName(), titleList.get(0).getTitleName());
        assertEquals(validTitle1.getQuantity(), titleList.get(0).getQuantity());
    }

    @Test
    @Tag("Title")
    public void testAddInvalidTitle() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.addTitle(invalidTitle);
        });
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(invalidTitle.getTitleName());
        assertEquals(0, titleList.size());
    }

    // Delete title
    @Test
    @Tag("Title")
    public void testDeleteValidTitle() throws SQLException {
        BookManagementServices.deleteTitle(validTitle1.getTitleId());
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(validTitle1.getTitleId());
        assertEquals(0, titleList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Title title = titleList.get(0);
        });
    }

    @Test
    @Tag("Title")
    public void testDeleteInvalidTitle() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.deleteTitle(invalidTitle.getTitleId());
        });
    }

    // Edit title
    @Test
    @Tag("Title")
    public void testEditValidTitle() throws SQLException {
        BookManagementServices.editTitle("T00005", validTitle1);
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(validTitle1.getTitleId());
        assertEquals(1, titleList.size());
        assertEquals(validTitle1.getTitleId(), titleList.get(0).getTitleId());
        assertEquals(validTitle1.getTitleName(), titleList.get(0).getTitleName());
        assertEquals(validTitle1.getQuantity(), titleList.get(0).getQuantity());
    }

    @Test
    @Tag("Title")
    public void testEditInvalidTitle() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.editTitle("T00001", validTitle2);
        });
        List<Title> titleList1 = BookManagementServices.getTitleListByKeyword("T00001");
        assertEquals(1, titleList1.size());
        assertEquals("T00001", titleList1.get(0).getTitleId());
        assertEquals("Tôi thấy hoa vàng trên cỏ xanh", titleList1.get(0).getTitleName());
        assertEquals("Nguyễn Nhật Ánh", titleList1.get(0).getAuthor());
        List<Title> titleList2 = BookManagementServices.getTitleListByKeyword("T00003");
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.editTitle("T00010", titleList2.get(0));
        });
        List<Title> titleList3 = BookManagementServices.getTitleListByKeyword("T00010");
        assertEquals(1, titleList3.size());
        assertEquals("T00010", titleList3.get(0).getTitleId());
        assertEquals("Dế mèn phiêu lưu ký", titleList3.get(0).getTitleName());
        assertEquals("Nhà xuất bản báo Tân Dân", titleList3.get(0).getPublisher());
    }
}

