package com.pacman.MentAlly.ui.emergencyContacts;

public class Contact {

    private String name; //mandatory
    private int phoneNumber; //mandatory
    private String email; //optional
    private String contactId;
    private static int contactIdCounter;

    public Contact (String name, int phoneNumber, String email) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.contactIdCounter++;
        this.contactId = Integer.toString(this.contactIdCounter);
    }

    public Contact () {

    }


    public String getContactName() { return this.name; }

    public int getPhoneNumber() { return this.phoneNumber; }

    public String getEmail() { return this.email; }

    public String getContactId() { return this.contactId; }

    public void setContactName(String name) { this.name = name; }

    public void setPhoneNumber(int phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setEmail(String email) { this.email = email; }

    public void setContactId(String id) { this.contactId = id; }
}
