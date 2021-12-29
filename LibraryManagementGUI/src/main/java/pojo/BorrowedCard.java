package pojo;

import java.util.Date;

public class BorrowedCard {
    private String borrowedCardId;
    private ReaderCard readerCard;
    private Employee employee;
    private Date borrowedDate;

    public BorrowedCard(String borrowedCardId, ReaderCard readerCard, Employee employee, Date borrowedDate) {
        this.borrowedCardId = borrowedCardId;
        this.readerCard = readerCard;
        this.employee = employee;
        this.borrowedDate = borrowedDate;
    }

    public BorrowedCard() {
    }

    public String getBorrowedCardId() {
        return borrowedCardId;
    }

    public void setBorrowedCardId(String borrowedCardId) {
        this.borrowedCardId = borrowedCardId;
    }

    public ReaderCard getReaderCard() {
        return readerCard;
    }

    public void setReaderCard(ReaderCard readerCard) {
        this.readerCard = readerCard;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }
}
