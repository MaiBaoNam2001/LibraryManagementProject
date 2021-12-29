package pojo;

import java.util.Date;

public class Book {
    private String bookId;
    private String bookName;
    private String description;
    private int publishingYear;
    private Date entryDate;
    private int position;
    private String status;
    private Title title;

    public Book(String bookId, String bookName, String description, int publishingYear, Date entryDate, int position, String status, Title title) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.description = description;
        this.publishingYear = publishingYear;
        this.entryDate = entryDate;
        this.position = position;
        this.status = status;
        this.title = title;
    }

    public Book() {
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return this.bookId;
    }
}
