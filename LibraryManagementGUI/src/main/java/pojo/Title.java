package pojo;

import javafx.collections.ObservableArray;

public class Title {
    private String titleId;
    private String titleName;
    private String author;
    private String publisher;
    private String category;
    private int quantity;

    public Title(String titleId, String titleName, String author, String publisher, String category, int quantity) {
        this.titleId = titleId;
        this.titleName = titleName;
        this.author = author;
        this.publisher = publisher;
        this.category = category;
        this.quantity = quantity;
    }

    public Title() {
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getTitleName();
    }
}
