package registration;

public class RegistrationForm {
    // create registration form for new user
    private String username;
    private String passWord;
    private String email;

    public RegistrationForm() {
    }

    public RegistrationForm(String username, String passWord, String email) {
        this.username = username;
        this.passWord = passWord;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
