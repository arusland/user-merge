package io.arusland.intterra.entity;

import java.util.LinkedHashSet;
import java.util.Set;

public class RealUser {
    private final String name;
    private final Set<String> emails = new LinkedHashSet<>();

    public RealUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEmail(String email) {
        emails.add(email);
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void clearEmails() {
        emails.clear();
    }
}
