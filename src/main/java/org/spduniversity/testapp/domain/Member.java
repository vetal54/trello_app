package org.spduniversity.testapp.domain;

public class Member {
    private User user;
    private RoleEnum role;

    public Member(User user, RoleEnum role) {
        this.user = user;
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
