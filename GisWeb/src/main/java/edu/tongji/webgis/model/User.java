package edu.tongji.webgis.model;

/**
 * Created by panyan on 15/11/26.
 */
public class User {

    public enum Role {
        ADMIN, NORMAL_USER, SPECIAL_USER;

        /**
         * add default rolePrefix 'ROLE_' to fit comparison in hasRole()
         * {@link org.springframework.security.access.expression.SecurityExpressionRoot#hasRole(String) }
         *
         * @return
         */
        public String toRoleString() {
            return "ROLE_" + super.toString();
        }
    }

    private String username;

    private String password;

    private boolean active;

    private Role role;

    private String phone;

    private String realName;

    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
