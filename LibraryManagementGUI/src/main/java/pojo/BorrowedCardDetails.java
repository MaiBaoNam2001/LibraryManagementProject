package pojo;

import java.util.Date;

public class BorrowedCardDetails {
    private BorrowedCard borrowedCard;
    private Book book;
    private boolean returned;
    private Date returnedDate;

    public BorrowedCardDetails(BorrowedCard borrowedCard, Book book, boolean returned, Date returnedDate) {
        this.borrowedCard = borrowedCard;
        this.book = book;
        this.returned = returned;
        this.returnedDate = returnedDate;
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
}
