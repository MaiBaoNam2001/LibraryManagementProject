/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3360
 Source Schema         : library_management_v2_db

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 22/09/2022 20:48:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `BookId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `BookName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `PublishingYear` int(11) NULL DEFAULT 0,
  `EntryDate` date NULL DEFAULT NULL,
  `Position` int(11) NULL DEFAULT 0,
  `Status` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT 'Sẵn sàng',
  `TitleId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`BookId`) USING BTREE,
  INDEX `fk_book_title`(`TitleId`) USING BTREE,
  CONSTRAINT `fk_book_title` FOREIGN KEY (`TitleId`) REFERENCES `title` (`TitleId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('B00001', 'Shin - Cậu bé bút chì', 'Crayon Shin-chan là một bộ manga Nhật Bản được sáng tác và minh họa Usui Yoshito sáng tác và minh họa. Nội dung kể xoay quanh cậu bé Shin với những câu chuyện về cuộc sống hàng ngày cùng với bố mẹ, em gái, chú chó Bạch Tuyết, bạn bè, và những nhân vật khác.', 2000, '2010-12-10', 1, 'Đang mượn', 'T00008');
INSERT INTO `book` VALUES ('B00002', 'Shin - Cậu bé bút chì', 'Crayon Shin-chan là một bộ manga Nhật Bản được sáng tác và minh họa Usui Yoshito sáng tác và minh họa. Nội dung kể xoay quanh cậu bé Shin với những câu chuyện về cuộc sống hàng ngày cùng với bố mẹ, em gái, chú chó Bạch Tuyết, bạn bè, và những nhân vật khác.', 2000, '2010-12-10', 2, 'Đang mượn', 'T00008');
INSERT INTO `book` VALUES ('B00003', 'Shin - Cậu bé bút chì', 'Crayon Shin-chan là một bộ manga Nhật Bản được sáng tác và minh họa Usui Yoshito sáng tác và minh họa. Nội dung kể xoay quanh cậu bé Shin với những câu chuyện về cuộc sống hàng ngày cùng với bố mẹ, em gái, chú chó Bạch Tuyết, bạn bè, và những nhân vật khác.', 2000, '2010-12-10', 3, 'Sẵn sàng', 'T00008');
INSERT INTO `book` VALUES ('B00004', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 4, 'Sẵn sàng', 'T00001');
INSERT INTO `book` VALUES ('B00005', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 5, 'Sẵn sàng', 'T00001');
INSERT INTO `book` VALUES ('B00006', 'Doraemon', 'Doraemon là nhân vật chính hư cấu trong loạt Manga cùng tên của họa sĩ Fujiko F. Fujio. Trong truyện lấy bối cảnh ở thế kỷ 22, Doraemon là chú mèo robot của tương lai do xưởng Matsushiba — công xưởng chuyên sản xuất robot vốn dĩ nhằm mục đích chăm sóc trẻ nhỏ.', 1992, '2021-02-26', 6, 'Sẵn sàng', 'T00003');
INSERT INTO `book` VALUES ('B00007', 'Doraemon', 'Doraemon là nhân vật chính hư cấu trong loạt Manga cùng tên của họa sĩ Fujiko F. Fujio. Trong truyện lấy bối cảnh ở thế kỷ 22, Doraemon là chú mèo robot của tương lai do xưởng Matsushiba — công xưởng chuyên sản xuất robot vốn dĩ nhằm mục đích chăm sóc trẻ nhỏ.', 1992, '2021-02-26', 7, 'Sẵn sàng', 'T00003');
INSERT INTO `book` VALUES ('B00008', 'Doraemon', 'Doraemon là nhân vật chính hư cấu trong loạt Manga cùng tên của họa sĩ Fujiko F. Fujio. Trong truyện lấy bối cảnh ở thế kỷ 22, Doraemon là chú mèo robot của tương lai do xưởng Matsushiba — công xưởng chuyên sản xuất robot vốn dĩ nhằm mục đích chăm sóc trẻ nhỏ.', 1992, '2021-02-26', 8, 'Sẵn sàng', 'T00003');
INSERT INTO `book` VALUES ('B00009', 'Doraemon', 'Doraemon là nhân vật chính hư cấu trong loạt Manga cùng tên của họa sĩ Fujiko F. Fujio. Trong truyện lấy bối cảnh ở thế kỷ 22, Doraemon là chú mèo robot của tương lai do xưởng Matsushiba — công xưởng chuyên sản xuất robot vốn dĩ nhằm mục đích chăm sóc trẻ nhỏ.', 1992, '2021-02-26', 9, 'Sẵn sàng', 'T00003');
INSERT INTO `book` VALUES ('B00010', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 10, 'Sẵn sàng', 'T00001');
INSERT INTO `book` VALUES ('B00011', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 11, 'Mất sách', 'T00001');
INSERT INTO `book` VALUES ('B00012', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 12, 'Mất sách', 'T00001');
INSERT INTO `book` VALUES ('B00013', 'Tôi thấy hoa vàng trên cỏ xanh', 'Tôi thấy hoa vàng trên cỏ xanh là một tiểu thuyết dành cho thanh thiếu niên của nhà văn Nguyễn Nhật Ánh, xuất bản lần đầu tại Việt Nam vào ngày 9 tháng 12 năm 2010 bởi Nhà xuất bản Trẻ, với phần tranh minh họa do Đỗ Hoàng Tường thực hiện.', 2010, '2015-02-19', 13, 'Mất sách', 'T00001');
INSERT INTO `book` VALUES ('B00014', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 14, 'Đang mượn', 'T00007');
INSERT INTO `book` VALUES ('B00015', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 15, 'Đang mượn', 'T00007');
INSERT INTO `book` VALUES ('B00016', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 16, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00017', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 17, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00018', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 18, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00019', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 19, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00020', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 20, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00021', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 21, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00022', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 22, 'Sẵn sàng', 'T00007');
INSERT INTO `book` VALUES ('B00023', 'Harry Potter', 'Harry Potter là tên của series tiểu thuyết huyền bí gồm bảy phần của nữ nhà văn Anh Quốc J. K. Rowling. Bộ truyện viết về những cuộc phiêu lưu phù thủy của cậu bé Harry Potter cùng hai người bạn thân là Ronald Weasley và Hermione Granger, lấy bối cảnh tại Trường Phù thủy và Pháp sư Hogwarts nước Anh.', 1997, '2014-12-19', 23, 'Đang mượn', 'T00007');

-- ----------------------------
-- Table structure for borrowed_card
-- ----------------------------
DROP TABLE IF EXISTS `borrowed_card`;
CREATE TABLE `borrowed_card`  (
  `BorrowedCardId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ReaderCardId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `EmployeeId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `BorrowedDate` date NULL DEFAULT NULL,
  PRIMARY KEY (`BorrowedCardId`) USING BTREE,
  INDEX `fk_borrowed_card_employee_idx`(`EmployeeId`) USING BTREE,
  INDEX `fk_borrowed_card_reader_card_idx`(`ReaderCardId`) USING BTREE,
  CONSTRAINT `fk_borrowed_card_employee` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`EmployeeId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_borrowed_card_reader_card` FOREIGN KEY (`ReaderCardId`) REFERENCES `reader_card` (`ReaderCardId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowed_card
-- ----------------------------
INSERT INTO `borrowed_card` VALUES ('BC00001', 'RC00001', 'E00001', '2021-12-28');
INSERT INTO `borrowed_card` VALUES ('BC00002', 'RC00002', 'E00001', '2021-12-27');
INSERT INTO `borrowed_card` VALUES ('BC00003', 'RC00003', 'E00001', '2021-12-30');
INSERT INTO `borrowed_card` VALUES ('BC00004', 'RC00004', 'E00001', '2021-12-31');
INSERT INTO `borrowed_card` VALUES ('BC00005', 'RC00007', 'E00001', '2022-01-22');

-- ----------------------------
-- Table structure for borrowed_card_detail
-- ----------------------------
DROP TABLE IF EXISTS `borrowed_card_detail`;
CREATE TABLE `borrowed_card_detail`  (
  `BorrowedCardId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `BookId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `IsReturned` tinyint(4) NULL DEFAULT 1,
  `ReturnedDate` date NULL DEFAULT NULL,
  `Fine` decimal(10, 2) NULL DEFAULT 0.00,
  PRIMARY KEY (`BorrowedCardId`, `BookId`) USING BTREE,
  INDEX `fk_borrowed_card_detail_reader_idx`(`BookId`) USING BTREE,
  CONSTRAINT `fk_borrowed_card_detail_book` FOREIGN KEY (`BookId`) REFERENCES `book` (`BookId`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_borrowed_card_detail_borrowed_card` FOREIGN KEY (`BorrowedCardId`) REFERENCES `borrowed_card` (`BorrowedCardId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrowed_card_detail
-- ----------------------------
INSERT INTO `borrowed_card_detail` VALUES ('BC00001', 'B00001', 1, '2021-12-31', 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00001', 'B00003', 1, '2022-01-27', 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00001', 'B00004', 1, '2022-01-28', 5000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00001', 'B00006', 1, '2022-01-31', 20000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00001', 'B00009', 1, '2022-01-31', 20000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00002', 'B00002', 1, '2022-01-20', 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00002', 'B00005', 1, '2022-01-26', 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00002', 'B00007', 1, '2022-01-27', 5000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00002', 'B00008', 1, '2022-01-27', 5000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00002', 'B00010', 1, '2022-01-31', 25000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00003', 'B00011', 1, '2022-01-15', 100000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00003', 'B00012', 1, '2022-01-29', 100000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00003', 'B00013', 1, '2022-01-30', 100000.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00003', 'B00014', 0, NULL, 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00003', 'B00023', 0, NULL, 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00004', 'B00015', 0, NULL, 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00005', 'B00001', 0, NULL, 0.00);
INSERT INTO `borrowed_card_detail` VALUES ('BC00005', 'B00002', 0, NULL, 0.00);

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`  (
  `EmployeeId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `EmployeeName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `BirthDay` date NULL DEFAULT NULL,
  `Address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `PhoneNumber` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`EmployeeId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('E00001', 'Nguyễn Đình Dũng', '1998-01-10', 'Đồng Tháp', 'dung.nd@gmail.com', '0355987258');
INSERT INTO `employee` VALUES ('E00002', 'Cao Thế Hùng', '1999-10-24', 'Nam Định', 'hung.ct@gmail.com', '0128796541');
INSERT INTO `employee` VALUES ('E00003', 'Trần Thị Như Quỳnh', '1998-12-28', 'Đồng Nai', 'quynh.tt@gmail.com', '0127486355');
INSERT INTO `employee` VALUES ('E00004', 'Nguyễn Thị Ngọc Trâm', '1999-01-20', 'Cần Thơ', 'tram.nt@gmail.com', '0355912547');
INSERT INTO `employee` VALUES ('E00005', 'Phan Thị Minh Thu', '2000-02-15', 'Đồng Tháp', 'thu.pt@gmail.com', '0289657812');
INSERT INTO `employee` VALUES ('E00006', 'Lý Minh Hoàng', '1999-05-16', 'Bình Dương', 'hoang.lm@gmail.com', '0285432152');
INSERT INTO `employee` VALUES ('E00007', 'Ngô Thế Thành', '2000-04-27', 'Gia Lai', 'thanh.nt@gmail.com', '0287453687');
INSERT INTO `employee` VALUES ('E00008', 'Nguyễn Thị Tuyết Ngân', '2000-08-10', 'Thành phố Hồ Chí Minh', 'ngan.nt@gmail.com', '0125786445');
INSERT INTO `employee` VALUES ('E00009', 'Lương Phát Đạt', '1999-10-05', 'Gia Lai', 'dat.lp@gmail.com', '0127478745');
INSERT INTO `employee` VALUES ('E00010', 'Trần Đình Quang', '2000-11-18', 'Bình Thuận', 'quang.td@gmail.com', '0285463219');
INSERT INTO `employee` VALUES ('E00011', 'abc', '2000-11-18', 'Bình Thuận', 'abc@gmail.com', '0285463219');

-- ----------------------------
-- Table structure for reader
-- ----------------------------
DROP TABLE IF EXISTS `reader`;
CREATE TABLE `reader`  (
  `ReaderId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `ReaderName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Gender` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `BirthDay` date NULL DEFAULT NULL,
  `Address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `PhoneNumber` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Object` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Department` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ReaderId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reader
-- ----------------------------
INSERT INTO `reader` VALUES ('R00001', 'Nguyễn Công Bình', 'Nam', '2000-12-12', 'Đồng Nai', 'binh.nc@gmail.com', '0128887531', 'Sinh viên', 'Khoa Công nghệ thông tin');
INSERT INTO `reader` VALUES ('R00002', 'Trần Công Lập', 'Nam', '2000-12-28', 'Nam Định', 'lap.tc@gmail.com', '0124443589', 'Sinh viên', 'Khoa Quản trị kinh doanh');
INSERT INTO `reader` VALUES ('R00003', 'Đinh Thái Tường', 'Nam', '1985-05-10', 'Nghệ An', 'tuong.dt@gmail.com', '0124785523', 'Giảng viên', 'Khoa Công nghệ thông tin');
INSERT INTO `reader` VALUES ('R00004', 'Nguyễn Thị Thùy Dung', 'Nữ', '2001-05-06', 'Gia Lai', 'dung.nt@gmail.com', '0129325846', 'Sinh viên', 'Khoa Ngôn ngữ Anh');
INSERT INTO `reader` VALUES ('R00005', 'Trần Thị Thu Ngọc', 'Nữ', '1986-10-05', 'Hà Nội', 'ngoc.tt@gmail.com', '0123658921', 'Giảng viên', 'Khoa Tài chính ngân hàng');
INSERT INTO `reader` VALUES ('R00006', 'Cao Thị Lan Phương', 'Nữ', '1985-09-14', 'Thành phố Hồ Chí Minh', 'phuong.ct@gmail.com', '0127775450', 'Giảng viên', 'Khoa Công nghệ thông tin');
INSERT INTO `reader` VALUES ('R00007', 'Trần Thái Tường', 'Nam', '2001-07-13', 'Đắk Lắk', 'tuong.tt@gmail.com', '0125696331', 'Sinh viên', 'Khoa Ngôn ngữ Anh');
INSERT INTO `reader` VALUES ('R00009', 'Cao Vạn Đạt', 'Nam', '2000-12-27', 'Hà Nội', 'dat.cv@gmail.com', '0124789532', 'Sinh viên', 'Khoa Tài chính ngân hàng');

-- ----------------------------
-- Table structure for reader_card
-- ----------------------------
DROP TABLE IF EXISTS `reader_card`;
CREATE TABLE `reader_card`  (
  `ReaderCardId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `StartDate` date NULL DEFAULT NULL,
  `ExpirationDate` date NULL DEFAULT NULL,
  `ReaderId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ReaderCardId`) USING BTREE,
  UNIQUE INDEX `uq_reader_id`(`ReaderId`) USING BTREE,
  INDEX `fk_reader_card_reader_idx`(`ReaderId`) USING BTREE,
  CONSTRAINT `fk_reader_card_reader` FOREIGN KEY (`ReaderId`) REFERENCES `reader` (`ReaderId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of reader_card
-- ----------------------------
INSERT INTO `reader_card` VALUES ('RC00001', '2021-12-01', '2022-01-01', 'R00001');
INSERT INTO `reader_card` VALUES ('RC00002', '2021-12-10', '2022-01-10', 'R00002');
INSERT INTO `reader_card` VALUES ('RC00003', '2021-12-15', '2022-01-15', 'R00003');
INSERT INTO `reader_card` VALUES ('RC00004', '2021-12-05', '2022-01-05', 'R00004');
INSERT INTO `reader_card` VALUES ('RC00005', '2021-12-02', '2022-01-02', 'R00005');
INSERT INTO `reader_card` VALUES ('RC00006', '2021-12-06', '2022-01-06', 'R00006');
INSERT INTO `reader_card` VALUES ('RC00007', '2021-12-28', '2022-01-28', 'R00007');

-- ----------------------------
-- Table structure for title
-- ----------------------------
DROP TABLE IF EXISTS `title`;
CREATE TABLE `title`  (
  `TitleId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `TitleName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Author` varchar(1000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Publisher` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Category` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Quantity` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`TitleId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of title
-- ----------------------------
INSERT INTO `title` VALUES ('T00001', 'Tôi thấy hoa vàng trên cỏ xanh', 'Nguyễn Nhật Ánh', 'Nhà xuất bản Trẻ', 'Tiểu thuyết', 6);
INSERT INTO `title` VALUES ('T00003', 'Doraemon', 'Fujiko F.Fujio', 'Nhà xuất bản Kim Đồng', 'Thiếu nhi', 4);
INSERT INTO `title` VALUES ('T00006', 'Đắc nhân tâm', 'Dale Carnegie', 'Nhà xuất bản Dân trí', 'Tự giúp bản thân', 0);
INSERT INTO `title` VALUES ('T00007', 'Harry Potter', 'J.K.Rowling', 'Nhà xuất bản Trẻ', 'Tiểu thuyết', 10);
INSERT INTO `title` VALUES ('T00008', 'Shin - Cậu bé bút chì', 'Yoshito Usui', 'Nhà xuất bản Kim Đồng', 'Thiếu nhi', 3);
INSERT INTO `title` VALUES ('T00009', 'Mắt biếc', 'Nguyễn Nhật Ánh', 'Nhà xuất bản Trẻ', 'Tiểu thuyết', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `UserId` int(11) NOT NULL AUTO_INCREMENT,
  `Role` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Username` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `Password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `EmployeeId` varchar(10) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`UserId`) USING BTREE,
  UNIQUE INDEX `uq_username`(`Username`) USING BTREE,
  UNIQUE INDEX `uq_employee_id`(`EmployeeId`) USING BTREE,
  INDEX `fk_user_employee_idx`(`EmployeeId`) USING BTREE,
  CONSTRAINT `fk_user_employee` FOREIGN KEY (`EmployeeId`) REFERENCES `employee` (`EmployeeId`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Admin', 'nguyendinhdung', 'd459307c568dcfb5274e30e8d0007071', 'E00001');
INSERT INTO `user` VALUES (32, 'Người dùng', 'lyminhhoang', '8fa5365e5ff962567be6634572994a90', 'E00006');
INSERT INTO `user` VALUES (44, 'Người dùng', 'ngothethanh', '2b1201a3344629370aec01769b591df6', 'E00007');
INSERT INTO `user` VALUES (51, 'Người dùng', 'caothehung', '25d55ad283aa400af464c76d713c07ad', 'E00002');
INSERT INTO `user` VALUES (52, 'Người dùng', 'trandinhquang', 'bbdf3d8bb277d9d6ced4c5ea57e39548', 'E00010');

SET FOREIGN_KEY_CHECKS = 1;
