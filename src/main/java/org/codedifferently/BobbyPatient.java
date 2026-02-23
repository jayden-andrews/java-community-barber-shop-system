package org.codedifferently;

import java.util.Random;

// Defines the BobbyPatient class that stores customer information and generates unique IDs.
public class BobbyPatient {

    // Stores the customer’s full name.
    private String name;

    // Stores the customer’s phone number.
    private String phoneNumber;

    // Stores the unique patient ID that remains constant after creation.
    private final String customerId;

    /* Constructor to instantiates a new BobbyPatient object using the provided name and phone number.
       Generates and assigns a unique customer ID during object creation. */
    public BobbyPatient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.customerId = generateCustomerId(new Random());
    }

    // Returns the customer’s name.
    public String getName() {
        return name;
    }

    // Returns the customer’s phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Returns the customer’s unique ID.
    public String getCustomerId() {
        return customerId;
    }

    // Generates a unique customer ID using the first three letters of the name and a random five-digit number.
    public String generateCustomerId(Random random) {
        String prefix;
        if (name.length() >= 3) {
            prefix = name.substring(0, 3).toUpperCase();
        } else {
            prefix = name.toUpperCase();
        }
        return prefix + random.nextInt(10000, 100000);
    }

    // Returns a formatted string representation of the customer’s information.
    @Override
    public String toString() {
        return "\n(Name: " + name + ", Phone: " + phoneNumber + ", PatientId: " + customerId + ")";
    }
}