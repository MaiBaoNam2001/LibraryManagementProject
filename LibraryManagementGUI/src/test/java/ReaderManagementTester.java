import org.junit.jupiter.api.*;
import pojo.Reader;
import pojo.ReaderCard;
import services.ReaderManagementServices;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReaderManagementTester {
    private static Reader validReader1;
    private static Reader invalidReader;
    private static Reader validReader2;
    private static ReaderCard validReaderCard1;
    private static ReaderCard validReaderCard2;
    private static List<ReaderCard> invalidReaderCardList;

    @BeforeAll
    public static void init() throws ParseException {
        validReader1 = new Reader("R00008", "Lê Thị Thùy Dương", "Nữ", new SimpleDateFormat("yyyy-MM-dd").parse("2000-05-20"), "Nha Trang", "duong.lt@gmail.com", "0124687542", "Sinh viên", "Khoa Ngôn ngữ Anh");
        validReader2 = new Reader("R00010", "Cao Thái Tú", "Nam", new SimpleDateFormat("yyyy-MM-dd").parse("2000-06-20"), "Đà Nẵng", "tu.ct@gmail.com", "0124687554", "Sinh viên", "Khoa Ngôn ngữ Anh");
        invalidReader = new Reader("R00001", "Nguyễn Tuấn Tú", "Nam", new SimpleDateFormat("yyyy-MM-dd").parse("1985-04-10"), "Thành phố Hồ Chí Minh", "tu.nt@gmail.com", "0127198758", "Giảng viên", "Khoa Ngôn ngữ Anh");
        validReaderCard1 = new ReaderCard("RC00010", new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-08"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-08"), validReader2);
        validReaderCard2 = new ReaderCard("RC00011", new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-10"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-10"), validReader2);
        invalidReaderCardList = new ArrayList<>();
        invalidReaderCardList.add(new ReaderCard("RC00011", new SimpleDateFormat("yyyy-MM-dd").parse("2022-10-08"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-08"), validReader2));
        invalidReaderCardList.add(new ReaderCard("RC00012", new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-08"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-08"), validReader2));
        invalidReaderCardList.add(new ReaderCard("RC00001", new SimpleDateFormat("yyyy-MM-dd").parse("2022-02-08"), new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-08"), validReader2));
    }

    @Test
    @Order(1)
    @Tag("Reader")
    public void testGetReaderListByReaderId() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword("R00002");
        assertNotNull(readerList);
        assertEquals(1, readerList.size());
        assertEquals("R00002", readerList.get(0).getReaderId());
        assertEquals("Trần Công Lập", readerList.get(0).getReaderName());
        assertEquals("lap.tc@gmail.com", readerList.get(0).getEmail());
        assertEquals("0124443589", readerList.get(0).getPhoneNumber());
        assertEquals("Khoa Quản trị kinh doanh", readerList.get(0).getDepartment());
    }

    @Test
    @Order(2)
    @Tag("Reader")
    public void testGetReaderListByReaderName() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword("Nguyễn Thị Thùy Dung");
        assertNotNull(readerList);
        assertEquals(1, readerList.size());
        assertEquals("R00004", readerList.get(0).getReaderId());
        assertEquals("Nguyễn Thị Thùy Dung", readerList.get(0).getReaderName());
        assertEquals("dung.nt@gmail.com", readerList.get(0).getEmail());
        assertEquals("0129325846", readerList.get(0).getPhoneNumber());
        assertEquals("Khoa Ngôn ngữ Anh", readerList.get(0).getDepartment());
    }

    @Test
    @Order(3)
    @Tag("Reader")
    public void testGetReaderListByObject() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword("Giảng viên");
        assertNotNull(readerList);
        assertEquals(3, readerList.size());
        assertEquals("R00003", readerList.get(0).getReaderId());
        assertEquals("Đinh Thái Tường", readerList.get(0).getReaderName());
        assertEquals("R00006", readerList.get(2).getReaderId());
        assertEquals("Cao Thị Lan Phương", readerList.get(2).getReaderName());
        List<String> addressList = readerList.stream().map(reader -> reader.getAddress()).collect(Collectors.toList());
        Collections.reverse(addressList);
        assertArrayEquals(new String[]{"Thành phố Hồ Chí Minh", "Hà Nội", "Nghệ An"}, addressList.toArray());
        assertEquals(2, readerList.stream().filter(reader -> reader.getDepartment().equals("Khoa Công nghệ thông tin")).count());
    }

    @Test
    @Order(4)
    @Tag("Reader")
    public void testGetReaderListByDepartment() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword("Khoa Công nghệ thông tin");
        assertNotNull(readerList);
        assertEquals(3, readerList.size());
        assertEquals("R00001", readerList.get(0).getReaderId());
        assertEquals("Nguyễn Công Bình", readerList.get(0).getReaderName());
        assertEquals("R00003", readerList.get(1).getReaderId());
        assertEquals("Đinh Thái Tường", readerList.get(1).getReaderName());
        assertArrayEquals(new String[]{"Đồng Nai", "Nghệ An", "Thành phố Hồ Chí Minh"}, readerList.stream().map(reader -> reader.getAddress()).toArray());
        assertEquals(2, readerList.stream().filter(reader -> reader.getObject().equals("Giảng viên")).count());
    }

    @Test
    @Order(5)
    @Tag("Reader")
    public void testGetReaderByNull() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(null);
        assertNotNull(readerList);
        assertEquals(8, readerList.size());
        assertEquals(5, readerList.stream().filter(reader -> reader.getGender().equals("Nam")).count());
        assertTrue(readerList.stream().allMatch(reader -> reader.getEmail().contains("@gmail.com")));
        String[] departments = new String[]{"Khoa Công nghệ thông tin", "Khoa Quản trị kinh doanh", "Khoa Ngôn ngữ Anh", "Khoa Tài chính ngân hàng"};
        assertArrayEquals(departments, readerList.stream().map(reader -> reader.getDepartment()).distinct().toArray());
    }

    @Test
    @Order(6)
    @Tag("Reader")
    public void testGetReaderByInvalidKeyword() throws SQLException {
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword("Lâm Đồng");
        assertEquals(0, readerList.size());
    }

    @Test
    @Order(7)
    @Tag("Reader")
    public void testAddValidReader() throws SQLException {
        ReaderManagementServices.addReader(validReader1);
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(validReader1.getReaderId());
        assertEquals(1, readerList.size());
        assertEquals(validReader1.getReaderId(), readerList.get(0).getReaderId());
        assertEquals(validReader1.getReaderName(), readerList.get(0).getReaderName());
        assertEquals(validReader1.getEmail(), readerList.get(0).getEmail());
        assertEquals(validReader1.getPhoneNumber(), readerList.get(0).getPhoneNumber());
        assertEquals(validReader1.getDepartment(), readerList.get(0).getDepartment());
    }

    @Test
    @Order(8)
    @Tag("Reader")
    public void testAddInvalidReader() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.addReader(invalidReader);
        });
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(invalidReader.getReaderName());
        assertEquals(0, readerList.size());
    }

    @Test
    @Order(9)
    @Tag("Reader")
    public void testDeleteValidReader() throws SQLException {
        ReaderManagementServices.deleteReader(validReader1.getReaderId());
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(validReader1.getReaderId());
        assertEquals(0, readerList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            Reader reader = readerList.get(0);
        });
    }

    @Test
    @Order(10)
    @Tag("Reader")
    public void testDeleteInvalidReader() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.deleteReader(invalidReader.getReaderId());
        });
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(invalidReader.getReaderId());
        assertEquals(1, readerList.size());
    }

    @Test
    @Order(11)
    @Tag("Reader")
    public void testEditValidReader() throws SQLException {
        ReaderManagementServices.editReader("R00009", validReader2);
        List<Reader> readerList = ReaderManagementServices.getReaderListByKeyword(validReader2.getReaderId());
        assertEquals(1, readerList.size());
        assertEquals(validReader2.getReaderId(), readerList.get(0).getReaderId());
        assertEquals(validReader2.getReaderName(), readerList.get(0).getReaderName());
        assertEquals(validReader2.getEmail(), readerList.get(0).getEmail());
        assertEquals(validReader2.getPhoneNumber(), readerList.get(0).getPhoneNumber());
        assertEquals(validReader2.getDepartment(), readerList.get(0).getDepartment());
    }

    @Test
    @Order(12)
    @Tag("Reader")
    public void testEditInvalidReader() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.editReader("R00001", validReader1);
        });
        List<Reader> readerList1 = ReaderManagementServices.getReaderListByKeyword("R00001");
        assertEquals(1, readerList1.size());
        assertEquals("R00001", readerList1.get(0).getReaderId());
        assertEquals("Nguyễn Công Bình", readerList1.get(0).getReaderName());
        assertEquals("binh.nc@gmail.com", readerList1.get(0).getEmail());
        assertEquals("0128887531", readerList1.get(0).getPhoneNumber());
        assertEquals("Khoa Công nghệ thông tin", readerList1.get(0).getDepartment());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.editReader(validReader2.getReaderId(), readerList1.get(0));
        });
        List<Reader> readerList2 = ReaderManagementServices.getReaderListByKeyword(validReader2.getReaderId());
        assertEquals(1, readerList2.size());
        assertEquals(validReader2.getReaderId(), readerList2.get(0).getReaderId());
        assertEquals(validReader2.getReaderName(), readerList2.get(0).getReaderName());
        assertEquals(validReader2.getEmail(), readerList2.get(0).getEmail());
        assertEquals(validReader2.getPhoneNumber(), readerList2.get(0).getPhoneNumber());
        assertEquals(validReader2.getDepartment(), readerList2.get(0).getDepartment());
    }

    @Test
    @Order(13)
    @Tag("ReaderCard")
    public void testGetReaderCardByReaderCardId() throws SQLException {
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword("RC00002");
        assertNotNull(readerCardList);
        assertEquals(1, readerCardList.size());
        assertEquals("RC00002", readerCardList.get(0).getReaderCardId());
        assertEquals("2021-12-10", readerCardList.get(0).getStartDate().toString());
        assertEquals("2022-01-10", readerCardList.get(0).getExpirationDate().toString());
        assertEquals("R00002", readerCardList.get(0).getReader().getReaderId());
    }

    @Test
    @Order(14)
    @Tag("ReaderCard")
    public void testGetReaderCardByReaderId() throws SQLException {
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword("R00007");
        assertNotNull(readerCardList);
        assertEquals(1, readerCardList.size());
        assertEquals("RC00007", readerCardList.get(0).getReaderCardId());
        assertEquals("2021-12-28", readerCardList.get(0).getStartDate().toString());
        assertEquals("2022-01-28", readerCardList.get(0).getExpirationDate().toString());
        assertEquals("R00007", readerCardList.get(0).getReader().getReaderId());
    }

    @Test
    @Order(15)
    @Tag("ReaderCard")
    public void testGetReaderCardByNull() throws SQLException {
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword(null);
        assertNotNull(readerCardList);
        assertEquals(7, readerCardList.size());
        assertEquals("RC00001", readerCardList.get(0).getReaderCardId());
        assertEquals("R00001", readerCardList.get(0).getReader().getReaderId());
        assertEquals("RC00007", readerCardList.get(6).getReaderCardId());
        assertEquals("R00007", readerCardList.get(6).getReader().getReaderId());
    }

    @Test
    @Order(16)
    @Tag("ReaderCard")
    public void testGetReaderCardByInvalidKeyword() throws SQLException {
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword("RC00020");
        assertNotNull(readerCardList);
        assertEquals(0, readerCardList.size());
    }

    @Test
    @Order(17)
    @Tag("ReaderCard")
    public void testAddValidReaderCard() throws SQLException {
        ReaderManagementServices.addReaderCard(validReaderCard1);
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword(validReaderCard1.getReaderCardId());
        assertEquals(1, readerCardList.size());
        assertEquals(validReaderCard1.getReaderCardId(), readerCardList.get(0).getReaderCardId());
        assertEquals(validReaderCard1.getStartDate(), readerCardList.get(0).getStartDate());
        assertEquals(validReaderCard1.getExpirationDate(), readerCardList.get(0).getExpirationDate());
        assertEquals(validReaderCard1.getReader().getReaderId(), readerCardList.get(0).getReader().getReaderId());
    }

    @Test
    @Order(18)
    @Tag("ReaderCard")
    public void testAddInvalidReaderCard() throws SQLException {
        assertThrows(DateTimeException.class, () -> {
            ReaderManagementServices.addReaderCard(invalidReaderCardList.get(0));
        });
        List<ReaderCard> readerCardList1 = ReaderManagementServices.getReaderCardListByKeyword(invalidReaderCardList.get(0).getReaderCardId());
        assertEquals(0, readerCardList1.size());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.addReaderCard(invalidReaderCardList.get(1));
        });
        List<ReaderCard> readerCardList2 = ReaderManagementServices.getReaderCardListByKeyword(invalidReaderCardList.get(1).getReaderCardId());
        assertEquals(0, readerCardList2.size());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.addReaderCard(invalidReaderCardList.get(2));
        });
        List<ReaderCard> readerCardList3 = ReaderManagementServices.getReaderCardListByKeyword(invalidReaderCardList.get(2).getReaderCardId());
        assertEquals(1, readerCardList3.size());
    }

    @Test
    @Order(19)
    @Tag("ReaderCard")
    public void testDeleteValidReaderCard() throws SQLException {
        ReaderManagementServices.deleteReaderCard(validReaderCard1.getReaderCardId());
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword(validReaderCard1.getReaderCardId());
        assertEquals(0, readerCardList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> {
            ReaderCard readerCard = readerCardList.get(0);
        });
    }

    @Test
    @Order(20)
    @Tag("ReaderCard")
    public void testDeleteInvalidReaderCard() throws SQLException {
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.deleteReaderCard("RC00002");
        });
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword("RC00002");
        assertEquals(1, readerCardList.size());
    }

    @Test
    @Order(21)
    @Tag("ReaderCard")
    public void testEditValidReaderCard() throws SQLException {
        ReaderManagementServices.addReaderCard(validReaderCard1);
        ReaderManagementServices.editReaderCard(validReaderCard1.getReaderCardId(), validReaderCard2);
        List<ReaderCard> readerCardList = ReaderManagementServices.getReaderCardListByKeyword(validReaderCard2.getReaderCardId());
        assertEquals(1, readerCardList.size());
        assertEquals(validReaderCard2.getReaderCardId(), readerCardList.get(0).getReaderCardId());
        assertEquals(validReaderCard2.getStartDate(), readerCardList.get(0).getStartDate());
        assertEquals(validReaderCard2.getExpirationDate(), readerCardList.get(0).getExpirationDate());
        assertEquals(validReaderCard2.getReader().getReaderId(), readerCardList.get(0).getReader().getReaderId());
    }

    @Test
    @Order(22)
    @Tag("ReaderCard")
    public void testEditInvalidReaderCard() throws SQLException {
        assertThrows(DateTimeException.class, () -> {
            ReaderManagementServices.editReaderCard(validReaderCard2.getReaderCardId(), invalidReaderCardList.get(0));
        });
        List<ReaderCard> readerCardList1 = ReaderManagementServices.getReaderCardListByKeyword(validReaderCard2.getReaderCardId());
        assertEquals(1, readerCardList1.size());
        assertEquals(validReaderCard2.getReaderCardId(), readerCardList1.get(0).getReaderCardId());
        assertEquals(validReaderCard2.getReader().getReaderId(), readerCardList1.get(0).getReader().getReaderId());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.editReaderCard("RC00001", validReaderCard1);
        });
        List<ReaderCard> readerCardList2 = ReaderManagementServices.getReaderCardListByKeyword("RC00001");
        assertEquals(1, readerCardList2.size());
        assertEquals("RC00001", readerCardList2.get(0).getReaderCardId());
        assertEquals("R00001", readerCardList2.get(0).getReader().getReaderId());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.editReaderCard(validReaderCard2.getReaderCardId(), invalidReaderCardList.get(2));
        });
        List<ReaderCard> readerCardList3 = ReaderManagementServices.getReaderCardListByKeyword(validReaderCard2.getReaderCardId());
        assertEquals(1, readerCardList3.size());
        assertEquals(validReaderCard2.getReaderCardId(), readerCardList3.get(0).getReaderCardId());
        assertEquals(validReaderCard2.getReader().getReaderId(), readerCardList3.get(0).getReader().getReaderId());
        assertThrows(SQLIntegrityConstraintViolationException.class, () -> {
            ReaderManagementServices.editReaderCard("RC00007", invalidReaderCardList.get(1));
        });
        List<ReaderCard> readerCardList4 = ReaderManagementServices.getReaderCardListByKeyword("RC00007");
        assertEquals(1, readerCardList4.size());
        assertEquals("RC00007", readerCardList4.get(0).getReaderCardId());
        assertEquals("R00007", readerCardList4.get(0).getReader().getReaderId());
    }
}

