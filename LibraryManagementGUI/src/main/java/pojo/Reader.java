package pojo;

import java.util.Date;

public class Reader {
    private String readerId;
    private String readerName;
    private String gender;
    private Date birthDay;
    private String address;
    private String email;
    private String phoneNumber;
    private String object;
    private String department;

    public Reader(String readerId, String readerName, String gender, Date birthDay, String address, String email, String phoneNumber, String object, String department) {
        this.readerId = readerId;
        this.readerName = readerName;
        this.gender = gender;
        this.birthDay = birthDay;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.object = object;
        this.department = department;
    }

    public Reader() {
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return this.readerId;
    }
}
