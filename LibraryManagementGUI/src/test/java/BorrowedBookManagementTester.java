import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import pojo.*;
import services.BookManagementServices;
import services.BorrowedBookManagementServices;
import services.EmployeeManagementServices;
import services.ReaderManagementServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BorrowedBookManagementTester {
    private static BorrowedCard validBorrowedCard1;
    private static BorrowedCard validBorrowedCard2;
    private static List<BorrowedCard> invalidBorrowedCardList;
    private static List<BorrowedCardDetails> validBorrowedCardDetailsList;
    private static BorrowedCardDetails validBorrowedCardDetails;

    @BeforeAll
    public static void init() throws SQLException, ParseException {
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword(null);
        List<Employee> employeeList = EmployeeManagementServices.getEmployeeListByKeyword("E00003");
        validBorrowedCard1 = new BorrowedCard("BC00005", readerCardList.get(6), employeeList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-09"));
        validBorrowedCard2 = new BorrowedCard("BC00006", readerCardList.get(5), employeeList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"));
        invalidBorrowedCardList = new ArrayList<>();
        invalidBorrowedCardList.add(new BorrowedCard("BC00006", readerCardList.get(3), employeeList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-09")));
        invalidBorrowedCardList.add(new BorrowedCard("BC00006", readerCardList.get(2), employeeList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-09")));
        invalidBorrowedCardList.add(new BorrowedCard("BC00001", readerCardList.get(6), employeeList.get(0), new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-15")));
        List<Book> bookList = BookManagementServices.getBookListByKeyword(null);
        validBorrowedCardDetailsList = new ArrayList<>();
        validBorrowedCardDetailsList.add(new BorrowedCardDetails(validBorrowedCard2, bookList.get(0), false, null, 0.00));
        validBorrowedCardDetailsList.add(new BorrowedCardDetails(validBorrowedCard2, bookList.get(1), false, null, 0.00));
        validBorrowedCardDetailsList.add(new BorrowedCardDetails(validBorrowedCard2, bookList.get(2), false, null, 0.00));
        validBorrowedCardDetailsList.add(new BorrowedCardDetails(validBorrowedCard2, bookList.get(3), false, null, 0.00));
        validBorrowedCardDetailsList.add(new BorrowedCardDetails(validBorrowedCard2, bookList.get(4), false, null, 0.00));
        validBorrowedCardDetails = new BorrowedCardDetails(validBorrowedCard2, bookList.get(5), false, null, 0.00);
    }

    @Test
    @Order(1)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardListByBorrowedCardId() throws SQLException {
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("BC00004");
        assertNotNull(borrowedCardList);
        assertEquals(1, borrowedCardList.size());
        assertEquals("BC00004", borrowedCardList.get(0).getBorrowedCardId());
        assertEquals("RC00004", borrowedCardList.get(0).getReaderCard().getReaderCardId());
        assertEquals("Nguyễn Thị Thùy Dung", borrowedCardList.get(0).getReaderCard().getReader().getReaderName());
        assertEquals("E00001", borrowedCardList.get(0).getEmployee().getEmployeeId());
        assertEquals("Nguyễn Đình Dũng", borrowedCardList.get(0).getEmployee().getEmployeeName());
    }

    @Test
    @Order(2)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardListByReaderCardId() throws SQLException {
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("RC00002");
        assertNotNull(borrowedCardList);
        assertEquals(1, borrowedCardList.size());
        assertEquals("BC00002", borrowedCardList.get(0).getBorrowedCardId());
        assertEquals("RC00002", borrowedCardList.get(0).getReaderCard().getReaderCardId());
        assertEquals("Trần Công Lập", borrowedCardList.get(0).getReaderCard().getReader().getReaderName());
        assertEquals("E00001", borrowedCardList.get(0).getEmployee().getEmployeeId());
        assertEquals("Nguyễn Đình Dũng", borrowedCardList.get(0).getEmployee().getEmployeeName());
    }

    @Test
    @Order(3)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardListByEmployeeId() throws SQLException {
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("E00001");
        assertNotNull(borrowedCardList);
        assertEquals(4, borrowedCardList.size());
        assertEquals("BC00001", borrowedCardList.get(0).getBorrowedCardId());
        assertEquals("RC00001", borrowedCardList.get(0).getReaderCard().getReaderCardId());
        assertEquals("E00001", borrowedCardList.get(0).getEmployee().getEmployeeId());
        assertEquals("BC00004", borrowedCardList.get(3).getBorrowedCardId());
        assertEquals("RC00004", borrowedCardList.get(3).getReaderCard().getReaderCardId());
        assertEquals("E00001", borrowedCardList.get(3).getEmployee().getEmployeeId());
        assertTrue(borrowedCardList.stream().allMatch(borrowedCard -> borrowedCard.getEmployee().getEmployeeId().equals("E00001")));
        assertTrue(borrowedCardList.stream().allMatch(borrowedCard -> {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-27").compareTo(borrowedCard.getBorrowedDate()) <= 0 && borrowedCard.getBorrowedDate().compareTo(new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-31")) <= 0;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return false;
        }));
    }

    @Test
    @Order(4)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardListByNull() throws SQLException {
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword(null);
        assertNotNull(borrowedCardList);
        assertEquals(4, borrowedCardList.size());
        assertTrue(borrowedCardList.stream().allMatch(borrowedCard -> borrowedCard.getEmployee().getEmployeeId().equals("E00001")));
        assertArrayEquals(new String[]{"BC00001", "BC00002", "BC00003", "BC00004"}, borrowedCardList.stream().map(borrowedCard -> borrowedCard.getBorrowedCardId()).toArray());
        assertArrayEquals(new String[]{"RC00001", "RC00002", "RC00003", "RC00004"}, borrowedCardList.stream().map(borrowedCard -> borrowedCard.getReaderCard().getReaderCardId()).toArray());
    }

    @Test
    @Order(5)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardListByInvalidKeyword() throws SQLException {
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("BC00010");
        assertEquals(0, borrowedCardList.size());
    }

    @Test
    @Order(6)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardDetailListByValidBorrowedCardId() throws SQLException {
        List<BorrowedCardDetails> borrowedCardDetailsList = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId("BC00003");
        assertNotNull(borrowedCardDetailsList);
        assertEquals(5, borrowedCardDetailsList.size());
        assertEquals("B00012", borrowedCardDetailsList.get(1).getBook().getBookId());
        assertEquals("B00023", borrowedCardDetailsList.get(4).getBook().getBookId());
        assertEquals(3, borrowedCardDetailsList.stream().filter(borrowedCardDetails -> borrowedCardDetails.isReturned()).count());
        assertNull(borrowedCardDetailsList.get(4).getReturnedDate());
        assertEquals(300000, borrowedCardDetailsList.stream().mapToDouble(value -> value.getFine()).sum());
    }

    @Test
    @Order(7)
    @Tag("BorrowedCard")
    public void testGetBorrowedCardDetailListByInvalidBorrowedCardId() throws SQLException {
        List<BorrowedCardDetails> borrowedCardDetailsList = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId("BC00005");
        assertNotNull(borrowedCardDetailsList);
        assertEquals(0, borrowedCardDetailsList.size());
    }

    @Test
    @Order(8)
    @Tag("BorrowedCard")
    public void testGetBorrowedBooksNumberByValidReaderCardId() throws SQLException {
        assertEquals(2, BorrowedBookManagementServices.getBorrowedBooksNumberByReaderCardId("RC00003"));
        assertEquals(1, BorrowedBookManagementServices.getBorrowedBooksNumberByReaderCardId("RC00004"));
        assertEquals(0, BorrowedBookManagementServices.getBorrowedBooksNumberByReaderCardId("RC00001"));
    }

    @Test
    @Order(9)
    @Tag("BorrowedCard")
    public void testGetBorrowedBooksNumberByInvalidReaderCardId() throws SQLException {
        assertEquals(0, BorrowedBookManagementServices.getBorrowedBooksNumberByReaderCardId("RC00010"));
        assertEquals(0, BorrowedBookManagementServices.getBorrowedBooksNumberByReaderCardId(null));
    }

    @Test
    @Order(10)
    @Tag("BorrowedCard")
    public void testAddValidBorrowedCard() throws SQLException {
        BorrowedBookManagementServices.addBorrowedCard(validBorrowedCard1);
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword(validBorrowedCard1.getBorrowedCardId());
        assertEquals(1, borrowedCardList.size());
        assertEquals(validBorrowedCard1.getBorrowedCardId(), borrowedCardList.get(0).getBorrowedCardId());
        assertEquals(validBorrowedCard1.getReaderCard().getReaderCardId(), borrowedCardList.get(0).getReaderCard().getReaderCardId());
        assertEquals(validBorrowedCard1.getEmployee().getEmployeeId(), borrowedCardList.get(0).getEmployee().getEmployeeId());
        assertEquals(validBorrowedCard1.getBorrowedDate(), borrowedCardList.get(0).getBorrowedDate());
    }

    @Test
    @Order(11)
    @Tag("BorrowedCard")
    public void testAddInvalidBorrowedCard() throws SQLException {
        assertThrows(DateTimeException.class, () -> {
            BorrowedBookManagementServices.addBorrowedCard(invalidBorrowedCardList.get(0));
        });
        assertThrows(UnknownError.class, () -> {
            BorrowedBookManagementServices.addBorrowedCard(invalidBorrowedCardList.get(1));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BorrowedBookManagementServices.addBorrowedCard(invalidBorrowedCardList.get(2));
        });
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword(null);
        assertEquals(5, borrowedCardList.size());
    }

    @Test
    @Order(12)
    @Tag("BorrowedCard")
    public void testDeleteValidBorrowedCard() throws SQLException {
        BorrowedBookManagementServices.deleteBorrowedCard(validBorrowedCard1.getBorrowedCardId());
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword(validBorrowedCard1.getBorrowedCardId());
        assertEquals(0, borrowedCardList.size());
    }

    @Test
    @Order(13)
    @Tag("BorrowedCard")
    public void testDeleteInvalidBorrowedCard() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BorrowedBookManagementServices.deleteBorrowedCard("BC00001");
        });
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("BC00001");
        assertEquals(1, borrowedCardList.size());
    }

    @Test
    @Order(14)
    @Tag("BorrowedCard")
    public void testEditValidBorrowedCard() throws SQLException {
        BorrowedBookManagementServices.addBorrowedCard(validBorrowedCard1);
        BorrowedBookManagementServices.editBorrowedCard(validBorrowedCard1.getBorrowedCardId(), validBorrowedCard2);
        List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword(validBorrowedCard2.getBorrowedCardId());
        assertEquals(1, borrowedCardList.size());
        assertEquals(validBorrowedCard2.getBorrowedCardId(), borrowedCardList.get(0).getBorrowedCardId());
        assertEquals(validBorrowedCard2.getReaderCard().getReaderCardId(), borrowedCardList.get(0).getReaderCard().getReaderCardId());
        assertEquals(validBorrowedCard2.getEmployee().getEmployeeId(), borrowedCardList.get(0).getEmployee().getEmployeeId());
        assertEquals(validBorrowedCard2.getBorrowedDate(), borrowedCardList.get(0).getBorrowedDate());
    }

    @Test
    @Order(15)
    @Tag("BorrowedCard")
    public void testEditInvalidBorrowedCard() throws SQLException {
        assertThrows(DateTimeException.class, () -> {
            BorrowedBookManagementServices.editBorrowedCard(validBorrowedCard2.getBorrowedCardId(), invalidBorrowedCardList.get(0));
        });
        assertThrows(UnknownError.class, () -> {
            BorrowedBookManagementServices.editBorrowedCard(validBorrowedCard2.getBorrowedCardId(), invalidBorrowedCardList.get(1));
        });
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BorrowedBookManagementServices.editBorrowedCard(validBorrowedCard2.getBorrowedCardId(), invalidBorrowedCardList.get(2));
        });
        List<BorrowedCard> borrowedCardList1 = BorrowedBookManagementServices.getBorrowedCardListByKeyword(validBorrowedCard2.getBorrowedCardId());
        assertEquals(1, borrowedCardList1.size());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            BorrowedBookManagementServices.editBorrowedCard("BC00002", validBorrowedCard1);
        });
        List<BorrowedCard> borrowedCardList2 = BorrowedBookManagementServices.getBorrowedCardListByKeyword("BC00002");
        assertEquals(1, borrowedCardList2.size());
    }

    @Test
    @Order(16)
    @Tag("Book")
    public void testGetBookByValidBookId() throws SQLException {
        Book book = BorrowedBookManagementServices.getBookByBookId("B00001");
        assertNotNull(book);
        assertEquals("B00001", book.getBookId());
        assertEquals("Shin - Cậu bé bút chì", book.getBookName());
        assertEquals("Sẵn sàng", book.getStatus());
        assertEquals("T00008", book.getTitle().getTitleId());
        assertEquals("Yoshito Usui", book.getTitle().getAuthor());
        assertEquals("Nhà xuất bản Kim Đồng", book.getTitle().getPublisher());
        assertEquals("Thiếu nhi", book.getTitle().getCategory());
        assertEquals(3, book.getTitle().getQuantity());
    }

    @Test
    @Order(17)
    @Tag("Book")
    public void testGetBookByInvalidBookId() throws SQLException {
        Book book = BorrowedBookManagementServices.getBookByBookId("B00050");
        assertNull(book);
    }

    @Test
    @Order(18)
    @Tag("BorrowedCardDetails")
    public void testBorrowValidBook() throws SQLException {
        for (int i = 0; i < validBorrowedCardDetailsList.size(); i++) {
            try {
                BorrowedBookManagementServices.borrowBook(validBorrowedCardDetailsList.get(i));
                List<BorrowedCardDetails> borrowedCardDetailsList = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId(validBorrowedCardDetailsList.get(0).getBorrowedCard().getBorrowedCardId());
                assertEquals(i + 1, borrowedCardDetailsList.size());
                assertEquals(validBorrowedCardDetailsList.get(i).getBorrowedCard().getBorrowedCardId(), borrowedCardDetailsList.get(i).getBorrowedCard().getBorrowedCardId());
                assertEquals(validBorrowedCardDetailsList.get(i).getBook().getBookId(), borrowedCardDetailsList.get(i).getBook().getBookId());
                assertFalse(borrowedCardDetailsList.get(i).isReturned());
                assertNull(borrowedCardDetailsList.get(i).getReturnedDate());
                assertEquals(0, borrowedCardDetailsList.get(i).getFine());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<BorrowedCardDetails> borrowedCardDetailsList2 = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId(validBorrowedCard2.getBorrowedCardId());
        assertEquals(5, borrowedCardDetailsList2.size());
    }

    @Test
    @Order(19)
    @Tag("BorrowedCardDetails")
    public void testBorrowInvalidBook() throws SQLException {
        assertThrows(UnknownError.class, () -> {
            BorrowedBookManagementServices.borrowBook(validBorrowedCardDetails);
        });
        List<BorrowedCardDetails> borrowedCardDetailsList = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId(validBorrowedCardDetails.getBorrowedCard().getBorrowedCardId());
        assertEquals(5, borrowedCardDetailsList.size());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            List<BorrowedCardDetails> borrowedCardDetailsList2 = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId("BC00004");
            List<BorrowedCard> borrowedCardList = BorrowedBookManagementServices.getBorrowedCardListByKeyword("BC00004");
            borrowedCardDetailsList2.get(0).setBorrowedCard(borrowedCardList.get(0));
            BorrowedBookManagementServices.borrowBook(borrowedCardDetailsList2.get(0));
        });
        List<BorrowedCardDetails> borrowedCardDetailsList3 = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId("BC00004");
        assertEquals(1, borrowedCardDetailsList3.size());
    }

    @Test
    @Order(20)
    @Tag("Book")
    public void testUpdateBookStatus() throws SQLException {
        validBorrowedCardDetailsList.forEach(borrowedCardDetails -> {
            try {
                BorrowedBookManagementServices.updateBookStatus(borrowedCardDetails.getBook().getBookId(), "Đang mượn");
                List<Book> bookList = BookManagementServices.getBookListByKeyword(borrowedCardDetails.getBook().getBookId());
                assertEquals(1, bookList.size());
                assertEquals("Đang mượn", bookList.get(0).getStatus());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        List<Book> bookList2 = BookManagementServices.getBookListByKeyword(null);
        assertEquals(8, bookList2.stream().filter(book -> book.getStatus().equals("Đang mượn")).count());
    }

    @Test
    @Order(21)
    @Tag("BorrowedCard")
    public void testIsValidBorrowedCardExist() throws SQLException {
        assertTrue(BorrowedBookManagementServices.isBorrowedCardExist("BC00001"));
        assertTrue(BorrowedBookManagementServices.isBorrowedCardExist("BC00004"));
    }

    @Test
    @Order(22)
    @Tag("BorrowedCard")
    public void testIsInvalidBorrowedCardExist() throws SQLException {
        assertFalse(BorrowedBookManagementServices.isBorrowedCardExist("BC00050"));
        assertFalse(BorrowedBookManagementServices.isBorrowedCardExist(null));
    }

    @Test
    @Order(23)
    @Tag("BorrowedCardDetails")
    public void testReturnValidBook() throws ParseException, SQLException {
        validBorrowedCardDetailsList.get(0).setReturned(true);
        validBorrowedCardDetailsList.get(0).setReturnedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-20"));
        validBorrowedCardDetailsList.get(0).setFine(0.00);
        validBorrowedCardDetailsList.get(1).setReturned(true);
        validBorrowedCardDetailsList.get(1).setReturnedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-01"));
        validBorrowedCardDetailsList.get(1).setFine(5000.00);
        validBorrowedCardDetailsList.get(2).setReturned(true);
        validBorrowedCardDetailsList.get(2).setReturnedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-30"));
        validBorrowedCardDetailsList.get(2).setFine(100000.00);
        BorrowedBookManagementServices.returnBook(validBorrowedCardDetailsList.get(0), ReturnType.RETURNED_BOOK);
        BorrowedBookManagementServices.updateBookStatus(validBorrowedCardDetailsList.get(0).getBook().getBookId(), "Sẵn sàng");
        BorrowedBookManagementServices.returnBook(validBorrowedCardDetailsList.get(1), ReturnType.RETURNED_BOOK);
        BorrowedBookManagementServices.updateBookStatus(validBorrowedCardDetailsList.get(1).getBook().getBookId(), "Sẵn sàng");
        BorrowedBookManagementServices.returnBook(validBorrowedCardDetailsList.get(2), ReturnType.LOST_BOOK);
        BorrowedBookManagementServices.updateBookStatus(validBorrowedCardDetailsList.get(2).getBook().getBookId(), "Mất sách");
        List<BorrowedCardDetails> borrowedCardDetailsList = BorrowedBookManagementServices.getBorrowedCardDetailsListByBorrowedCardId(validBorrowedCard2.getBorrowedCardId());
        assertEquals(5, borrowedCardDetailsList.size());
        for (int i = 0; i < validBorrowedCardDetailsList.size() - 2; i++) {
            assertEquals(validBorrowedCardDetailsList.get(i).getBorrowedCard().getBorrowedCardId(), borrowedCardDetailsList.get(i).getBorrowedCard().getBorrowedCardId());
            assertEquals(validBorrowedCardDetailsList.get(i).getBook().getBookId(), borrowedCardDetailsList.get(i).getBook().getBookId());
            assertTrue(borrowedCardDetailsList.get(i).isReturned());
            assertNotNull(borrowedCardDetailsList.get(i).getReturnedDate());
            assertEquals(validBorrowedCardDetailsList.get(i).getFine(), borrowedCardDetailsList.get(i).getFine());
        }
    }

    @Test
    @Order(24)
    @Tag("BorrowedCardDetails")
    public void testReturnInvalidBook() throws ParseException {
        validBorrowedCardDetailsList.get(3).setReturned(true);
        validBorrowedCardDetailsList.get(3).setReturnedDate(new SimpleDateFormat("yyyy-MM-dd").parse("2021-12-31"));
        assertThrows(DateTimeException.class, () -> {
            BorrowedBookManagementServices.returnBook(validBorrowedCardDetailsList.get(3), ReturnType.RETURNED_BOOK);
        });
    }

    @Test
    @Order(25)
    @Tag("BorrowedCardDetails")
    public void testGetValidTotalFines() throws ParseException, SQLException {
        assertEquals(40000, BorrowedBookManagementServices.getTotalFines("BC00001", new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-31")));
        assertEquals(10000, BorrowedBookManagementServices.getTotalFines("BC00002", new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-27")));
    }

    @Test
    @Order(26)
    @Tag("BorrowedCardDetails")
    public void testGetInvalidTotalFines() throws ParseException, SQLException {
        assertEquals(0, BorrowedBookManagementServices.getTotalFines("BC00050", new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-31")));
        assertEquals(0, BorrowedBookManagementServices.getTotalFines(null, new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-31")));
    }
}
