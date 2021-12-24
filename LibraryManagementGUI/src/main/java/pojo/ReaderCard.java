package pojo;

import java.util.Date;

public class ReaderCard {
    private String readerCardId;
    private Date startDate;
    private Date expirationDate;

    public ReaderCard(String readerCardId, Date startDate, Date expirationDate) {
        this.readerCardId = readerCardId;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
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
}
