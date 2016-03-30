package account;


public class Account {

    private String password;
    private String username;

    public Account() {
        this(DefaultAccount.USERNAME, DefaultAccount.PASSWORD);
    }

    public Account(String username, String password) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
