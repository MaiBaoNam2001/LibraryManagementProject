package pojo;

import java.util.Date;

public class ReaderCard {
    private String readerCardId;
    private Date startDate;
    private Date expirationDate;
    private Reader reader;

    public ReaderCard(String readerCardId, Date startDate, Date expirationDate, Reader reader) {
        this.readerCardId = readerCardId;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.reader = reader;
    }

    public ReaderCard() {
    }

    public String getReaderCardId() {
        return readerCardId;
    }

    public void setReaderCardId(String readerCardId) {
        this.readerCardId = readerCardId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    @Override
    public String toString() {
        return this.readerCardId;
    }
}
