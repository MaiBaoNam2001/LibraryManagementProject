import org.junit.jupiter.api.*;
import pojo.BookReport;
import pojo.ReaderReport;
import services.ReportServices;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReportTester {
    @Test
    @Order(1)
    @Tag("BookReport")
    public void testGetBookReportListByValidYear() throws SQLException {
        List<BookReport> bookReportList = ReportServices.getBookReportListByYear("2022");
        assertEquals(8, bookReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(11, bookReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(4, bookReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(7, bookReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
    }

    @Test
    @Order(2)
    @Tag("BookReport")
    public void testGetBookReportListByInvalidYear() throws SQLException {
        List<BookReport> bookReportList = ReportServices.getBookReportListByYear(null);
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
    }

    @Test
    @Order(3)
    @Tag("BookReport")
    public void testGetBookReportListByValidQuarter() throws SQLException {
        List<BookReport> bookReportList = ReportServices.getBookReportListByQuarter("Bốn", "2021");
        assertEquals(16, bookReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(1, bookReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
    }

    @Test
    @Order(4)
    @Tag("BookReport")
    public void testGetBookReportListByInvalidQuarter() throws SQLException {
        List<BookReport> bookReportList = ReportServices.getBookReportListByQuarter("Năm", "2021");
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, bookReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
    }

    @Test
    @Order(5)
    @Tag("ReaderReport")
    public void testGetReaderReportListByValidYear() throws SQLException {
        List<ReaderReport> readerReportList = ReportServices.getReaderReportListByYear("2022");
        assertEquals(8, readerReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(11, readerReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(4, readerReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(7, readerReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
        assertEquals(485000, readerReportList.stream().mapToDouble(value -> value.getFine()).sum());
    }

    @Test
    @Order(6)
    @Tag("ReaderReport")
    public void testGetReaderReportListByInvalidYear() throws SQLException {
        List<ReaderReport> readerReportList = ReportServices.getReaderReportListByYear("abc");
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToDouble(value -> value.getFine()).sum());
    }

    @Test
    @Order(7)
    @Tag("ReaderReport")
    public void testGetReaderReportListByValidQuarter() throws SQLException {
        List<ReaderReport> readerReportList = ReportServices.getReaderReportListByQuarter("Bốn", "2021");
        assertEquals(16, readerReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(1, readerReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToDouble(value -> value.getFine()).sum());
    }

    @Test
    @Order(8)
    @Tag("ReaderReport")
    public void testGetReaderReportListByInvalidQuarter() throws SQLException {
        List<ReaderReport> readerReportList = ReportServices.getReaderReportListByQuarter("Sáu", "2021");
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getBorrowedBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getReturnedBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getLostBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToInt(value -> value.getOverdueBookNumber()).sum());
        assertEquals(0, readerReportList.stream().mapToDouble(value -> value.getFine()).sum());
    }
}
