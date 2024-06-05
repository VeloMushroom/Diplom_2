package pojo.user;

public class UserChange {

    private String name;
    private String email;

    public UserChange() {
    }

    public UserChange(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}