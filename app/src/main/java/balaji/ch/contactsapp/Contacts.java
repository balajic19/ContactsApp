package balaji.ch.contactsapp;

public class Contacts {


//    Initializing contact details
    private String ContactName;
    private String contactNumber;

    public Contacts(String contactName, String contactNumber) {
        this.ContactName = contactName;
        this.contactNumber = contactNumber;
    }

//    These are our getters of Contacts class
    public String getContactName() {
        return ContactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

}
