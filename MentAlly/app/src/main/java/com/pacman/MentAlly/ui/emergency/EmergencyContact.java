package com.pacman.MentAlly.ui.emergency;

public class EmergencyContact {

    private String name; //mandatory
    private long phoneNumber; //mandatory
    private String email; //mandatory
    private String contactId;
    private static int contactIdCounter;

    public EmergencyContact () {

    }

    public EmergencyContact (String name, long phoneNumber, String email) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.contactIdCounter++;
        this.contactId = Integer.toString(this.contactIdCounter);
    }


    public String getContactName() { return this.name; }

    public long getPhoneNumber() { return this.phoneNumber; }

    public String getEmail() { return this.email; }

    public String getContactId() { return this.contactId; }

    public void setContactName(String name) { this.name = name; }

    public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setEmail(String email) { this.email = email; }

    public void setContactId(String id) { this.contactId = id; }
}
