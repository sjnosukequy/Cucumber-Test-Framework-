package org.example.cucumber.src.models.object;

public class credential {
    public String email;
    public String password;

    public credential(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
