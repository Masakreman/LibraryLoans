package com.libraryloans;

public class User {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    public User(String userId, String firstName, String lastName, String email) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override 
    public String toString() { // Printing Lists to Formatted String
        return "User ID = " + userId
                + System.lineSeparator()
                + "First Name: " + firstName
                + System.lineSeparator()
                + "Last Name: " + lastName
                + System.lineSeparator()
                + "Email: " + email
                + System.lineSeparator();

    }
    
    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
