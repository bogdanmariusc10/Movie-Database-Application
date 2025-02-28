package org.example;

public class Credentials
{
    private String email;
    private String password;

    public Credentials(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    // GETTERS
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //SETTERS
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
