package pojo;

public class ReaderReport {
    private int order;
    private Reader reader;
    private int borrowedBookNumber;
    private int returnedBookNumber;
    private int lostBookNumber;
    private int overdueBookNumber;
    private double fine;

    public ReaderReport(int order, Reader reader, int borrowedBookNumber, int returnedBookNumber, int lostBookNumber, int overdueBookNumber, double fine) {
        this.order = order;
        this.reader = reader;
        this.borrowedBookNumber = borrowedBookNumber;
        this.returnedBookNumber = returnedBookNumber;
        this.lostBookNumber = lostBookNumber;
        this.overdueBookNumber = overdueBookNumber;
        this.fine = fine;
    }

    public ReaderReport() {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
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

    public double getFine() {
        return fine;
    }

    public void setFine(double fine) {
        this.fine = fine;
    }
}
