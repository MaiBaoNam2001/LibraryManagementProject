import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pojo.Book;
import pojo.Title;
import services.BookManagementServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookManagementTester {
    private static Title validTitle1;
    private static Title validTitle2;
    private static Title invalidTitle;
    private static List<Book> validBookList;
    private static Book invalidBook1;
    private static Book invalidBook2;
    private static Book validBook;
    private static Title validTitle3;

    @BeforeAll
    public static void init() throws ParseException, SQLException {
        validTitle1 = new Title("T00010", "Dế mèn phiêu lưu ký", "Tô Hoài", "Nhà xuất bản báo Tân Dân", "Thiếu nhi", 0);
        validTitle2 = new Title("T00011", "Góc sân và khoảng trời", "Trần Đăng Khoa", "Nhà xuất bản Kim Đồng", "Tiểu thuyết", 0);
        invalidTitle = new Title("T00001", "Đoàn binh Tây Tiến", "Quang Dũng", "Nhà xuất bản Kim Đồng", "Hồi ký", 0);

        validBookList = new ArrayList<>();
        validBookList.add(new Book("B00024", "Dế mèn phiêu lưu ký", "...", 1941, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-12"), 24, "Sẵn sàng", validTitle1));
        validBookList.add(new Book("B00025", "Dế mèn phiêu lưu ký", "...", 1941, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-12"), 25, "Sẵn sàng", validTitle1));
        validBookList.add(new Book("B00026", "Dế mèn phiêu lưu ký", "...", 1941, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-12"), 26, "Sẵn sàng", validTitle1));
        invalidBook1 = new Book("B00002", "Dế mèn phiêu lưu ký", "...", 1941, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-12"), 27, "Sẵn sàng", validTitle1);
        validTitle3 = BookManagementServices.getTitleListByKeyword("T00003").get(0);
        validBook = new Book("B00025", "Doraemon", "Doraemon là nhân vật chính hư cấu trong loạt Manga cùng tên của họa sĩ Fujiko F. Fujio. Trong truyện lấy bối cảnh ở thế kỷ 22, Doraemon là chú mèo robot của tương lai do xưởng Matsushiba — công xưởng chuyên sản xuất robot vốn dĩ nhằm mục đích chăm sóc trẻ nhỏ.", 1992, new SimpleDateFormat("yyyy-MM-dd").parse("2021-02-26"), 25, "Sẵn sàng", validTitle3);
        invalidBook2 = new Book("B00024", "Dế mèn phiêu lưu ký", "...", 1941, new SimpleDateFormat("yyyy-MM-dd").parse("2021-05-12"), 24, "Sẵn sàng", validTitle2);
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
        List<Title> titleList = BookManagementServices.getTitleListByKeyword(invalidTitle.getTitleId());
        assertEquals(1, titleList.size());
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

    // Update book name
    public void testUpdateBookName() {

    }

    //Search book by keyword
    @Test
    @Tag("Book")
    public void testGetBookByBookId() throws SQLException {
        List<Book> bookList = BookManagementServices.getBookListByKeyword("B00006");
        assertNotNull(bookList);
        assertEquals(1, bookList.size());
        assertEquals("Doraemon", bookList.get(0).getBookName());
        assertEquals(1992, bookList.get(0).getPublishingYear());
        assertEquals("2021-02-26", bookList.get(0).getEntryDate().toString());
        assertEquals("T00003", bookList.get(0).getTitle().getTitleId());
    }

    @Test
    @Tag("Book")
    public void testGetBookByBookName() throws SQLException {
        List<Book> bookList = BookManagementServices.getBookListByKeyword("Harry Potter");
        assertNotNull(bookList);
        assertEquals(10, bookList.size());
        assertEquals("B00017", bookList.get(3).getBookId());
        assertTrue(bookList.stream().allMatch(book -> book.getTitle().getTitleId().equals("T00007")));
        assertEquals(20, bookList.get(6).getPosition());
        assertEquals(3, bookList.stream().filter(book -> book.getStatus().equals("Đang mượn")).count());
        assertEquals(7, bookList.stream().filter(book -> book.getStatus().equals("Sẵn sàng")).count());
    }

    @Test
    @Tag("Book")
    public void testGetBookListByPublishingYear() throws SQLException {
        List<Book> bookList = BookManagementServices.getBookListByKeyword("2000");
        assertNotNull(bookList);
        assertEquals(3, bookList.size());
        assertArrayEquals(new String[]{"B00001", "B00002", "B00003"}, bookList.stream().map(book -> book.getBookId()).toArray());
        assertTrue(bookList.stream().allMatch(book -> book.getEntryDate().toString().equals("2010-12-10")));
        assertArrayEquals(new int[]{1, 2, 3}, bookList.stream().mapToInt(value -> value.getPosition()).toArray());
    }

    @Test
    @Tag("Book")
    public void testGetBookListByNull() throws SQLException {
        List<Book> bookList = BookManagementServices.getBookListByKeyword(null);
        assertNotNull(bookList);
        assertEquals(23, bookList.size());
        assertEquals(6, bookList.stream().filter(book -> book.getBookName().equals("Tôi thấy hoa vàng trên cỏ xanh")).count());
        assertEquals(3, bookList.stream().filter(book -> book.getStatus().equals("Mất sách")).count());
        assertArrayEquals(new String[]{"T00001", "T00003", "T00007", "T00008"}, bookList.stream().map(book -> book.getTitle().getTitleId()).distinct().sorted().toArray());
        assertEquals(1, bookList.stream().mapToInt(value -> value.getPosition()).min().getAsInt());
    }

    @Test
    @Tag("Book")
    public void testGetBookListByInvalidKeyword() throws SQLException {
        List<Book> bookList = BookManagementServices.getBookListByKeyword("Đoàn binh Tây Tiến");
        assertEquals(0, bookList.size());
    }

    //Update title quantity
//    public void testUpdateTitleQuantity(){
//
//    }
    //Add book
    @Test
    @Tag("Book")
    public void testAddValidBook() {
        validBookList.forEach(book -> {
            try {
                BookManagementServices.addBook(book);
                List<Book> bookList = BookManagementServices.getBookListByKeyword(book.getBookId());
                assertEquals(1, bookList.size());
                assertEquals(book.getBookId(), bookList.get(0).getBookId());
                assertEquals(book.getBookName(), bookList.get(0).getBookName());
                assertEquals(book.getPosition(), bookList.get(0).getPosition());
                assertEquals(book.getStatus(), bookList.get(0).getStatus());
                assertEquals(book.getTitle().getTitleId(), bookList.get(0).getTitle().getTitleId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @Tag("Book")
    public void testAddInvalidBook() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.addBook(invalidBook1);
        });
        List<Book> bookList = BookManagementServices.getBookListByKeyword(invalidBook1.getBookName());
        assertEquals(3, bookList.size());
    }

    @Test
    @Tag("Book")
    public void testDeleteValidBook() throws SQLException {
        BookManagementServices.deleteBook(validBookList.get(1).getBookId());
        List<Book> bookList = BookManagementServices.getBookListByKeyword(validBookList.get(1).getBookId());
        assertEquals(0, bookList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Book book = bookList.get(0);
        });
    }

    @Test
    @Tag("Book")
    public void testDeleteInvalidBook() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.deleteBook("B00003");
        });
        List<Book> bookList1 = BookManagementServices.getBookListByKeyword("B00003");
        assertEquals(1, bookList1.size());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.deleteBook("B00015");
        });
        List<Book> bookList2 = BookManagementServices.getBookListByKeyword("B00015");
        assertEquals(1, bookList2.size());
    }

    @Test
    @Tag("Book")
    public void testEditValidBook() throws SQLException {
        BookManagementServices.editBook(validBookList.get(0).getBookId(), validBook);
        List<Book> bookList = BookManagementServices.getBookListByKeyword(validBookList.get(0).getBookId());
        assertEquals(1, bookList.size());
        assertEquals(validBookList.get(0).getBookId(), bookList.get(0).getBookId());
        assertEquals(validBook.getBookName(), bookList.get(0).getBookName());
        assertEquals(validBookList.get(0).getPosition(), bookList.get(0).getPosition());
        assertEquals(validBook.getTitle().getTitleId(), bookList.get(0).getTitle().getTitleId());
    }

    @Test
    @Tag("Book")
    public void testEditInvalidBook() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BookManagementServices.editBook(validBookList.get(0).getBookId(), invalidBook2);
        });
    }

    // Update title quantity
    @Test
    @Tag("Book")
    public void testUpdateValidTitleQuantity() throws SQLException {
        BookManagementServices.updateTitleQuantity("T00003", 5);
        List<Title> titleList1 = BookManagementServices.getTitleListByKeyword("T00003");
        assertEquals(1, titleList1.size());
        assertEquals(5, titleList1.get(0).getQuantity());
        BookManagementServices.updateTitleQuantity("T00010", 1);
        List<Title> titleList2 = BookManagementServices.getTitleListByKeyword("T00010");
        assertEquals(1, titleList2.size());
        assertEquals(1, titleList2.get(0).getQuantity());
    }
}
