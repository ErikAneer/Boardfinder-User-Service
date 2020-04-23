package Boardfinder.Auth.Domain;

/**
 * A class to represent the user credentials
 * @author Erik
 */
public class UserCredentials {

    private String username, password;

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
}
