package pojo;

public class BookReport {
    private int order;
    private Title title;
    private int borrowedBookNumber;
    private int returnedBookNumber;
    private int lostBookNumber;
    private int overdueBookNumber;

    public BookReport(int order, Title title, int borrowedBookNumber, int returnedBookNumber, int lostBookNumber, int overdueBookNumber) {
        this.order = order;
        this.title = title;
        this.borrowedBookNumber = borrowedBookNumber;
        this.returnedBookNumber = returnedBookNumber;
        this.lostBookNumber = lostBookNumber;
        this.overdueBookNumber = overdueBookNumber;
    }

    public BookReport() {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public int getBorrowedBookNumber() {
        return borrowedBookNumber;
    }

    public void setBorrowedBookNumber(int borrowedBookNumber) {
        this.borrowedBookNumber = borrowedBookNumber;
    }

    public int getReturnedBookNumber() {
        return returnedBookNumber;
    }

    public void setReturnedBookNumber(int returnedBookNumber) {
        this.returnedBookNumber = returnedBookNumber;
    }

    public int getLostBookNumber() {
        return lostBookNumber;
    }

    public void setLostBookNumber(int lostBookNumber) {
        this.lostBookNumber = lostBookNumber;
    }

    public int getOverdueBookNumber() {
        return overdueBookNumber;
    }

    public void setOverdueBookNumber(int overdueBookNumber) {
        this.overdueBookNumber = overdueBookNumber;
    }
}
