package ru.rest.model;

import java.util.Objects;

public class User {
    protected int id;
    protected String firstName;
    protected String secondName;
    protected String email;
    protected String createdAt;

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public User setSecondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(secondName, user.secondName) &&
                Objects.equals(email, user.email) &&
                Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName, email, createdAt);
    }
}
