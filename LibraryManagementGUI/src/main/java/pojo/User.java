package pojo;

public class User {
    private String userId;
    private String role;
    private String username;
    private String password;
    private Employee employee;

    public User(String userId, String role, String username, String password, Employee employee) {
        this.userId = userId;
        this.role = role;
        this.username = username;
        this.password = password;
        this.employee = employee;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
