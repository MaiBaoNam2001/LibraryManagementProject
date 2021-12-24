package pojo;

import java.util.Date;

public class Employee {
    private String employeeId;
    private String employeeName;
    private Date birthDay;
    private String address;
    private String email;
    private String phoneNumber;
    private User user;

    public Employee(String employeeId, String employeeName, Date birthDay, String address, String email, String phoneNumber, User user) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.birthDay = birthDay;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.user = user;
    }

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
