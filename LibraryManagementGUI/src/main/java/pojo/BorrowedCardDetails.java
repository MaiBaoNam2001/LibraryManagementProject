package pojo;

import java.util.Date;

public class BorrowedCardDetails {
    private BorrowedCard borrowedCard;
    private Book book;
    private boolean returned;
    private Date returnedDate;
    private Double Fine;

    public BorrowedCardDetails(BorrowedCard borrowedCard, Book book, boolean returned, Date returnedDate, Double fine) {
        this.borrowedCard = borrowedCard;
        this.book = book;
        this.returned = returned;
        this.returnedDate = returnedDate;
        Fine = fine;
    }

    public BorrowedCardDetails() {
    }

    public BorrowedCard getBorrowedCard() {
        return borrowedCard;
    }

    public void setBorrowedCard(BorrowedCard borrowedCard) {
        this.borrowedCard = borrowedCard;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Double getFine() {
        return Fine;
    }

    public void setFine(Double fine) {
        Fine = fine;
    }
}
