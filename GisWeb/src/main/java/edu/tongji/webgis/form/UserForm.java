package edu.tongji.webgis.form;

import edu.tongji.webgis.model.User;

/**
 * Created by panyan on 16/1/26.
 */
public class UserForm {
    private String username;

    private String password;

    private User.Role role;

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

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}
