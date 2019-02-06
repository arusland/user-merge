package io.arusland.intterra.entity;

import java.util.Objects;
import java.util.Set;

public class User {
    private RealUser realUser;

    public User(String username) {
        realUser = new RealUser(username);
    }

    public void mergeAsUser(User user) {
        RealUser oldRealUser = realUser;
        realUser = user.realUser;

        oldRealUser.getEmails().forEach(realUser::addEmail);
        oldRealUser.clearEmails();
    }

    public void addEmail(String email) {
        realUser.addEmail(email);
    }

    public String getName() {
        return realUser.getName();
    }

    public Set<String> getEmails() {
        return realUser.getEmails();
    }

    @Override
    public String toString() {
        return "User{" +
                "name=" + getName() + " - " +
                getEmails() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;

        return Objects.equals(realUser, user.realUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(realUser);
    }

    public static User create(String userName) {
        return new User(userName);
    }
}
