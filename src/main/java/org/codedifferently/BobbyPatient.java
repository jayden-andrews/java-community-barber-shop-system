package org.codedifferently;

import java.util.Random;

// Defines the BobbyPatient class that stores customer information and generates unique IDs.
public class BobbyPatient {

    // Stores the patient’s full name.
    private String name;

    // Stores the patient’s phone number.
    private String phoneNumber;

    // Stores the unique patient ID that remains constant after creation.
    private final String patientId;

    /* Constructor to instantiates a new BobbyPatient object using the provided name and phone number.
       Generates and assigns a unique patient ID during object creation. */
    public BobbyPatient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.patientId = generatePatientId(new Random());
    }

    // Returns the patient’s name.
    public String getName() {
        return name;
    }

    // Returns the customer’s phone number.
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Returns the customer’s unique ID.
    public String getPatientId() {
        return patientId;
    }

    // Generates a unique patient ID using the first three letters of the name and a random five-digit number.
    public String generatePatientId(Random random) {
        return name.substring(0, 3).toUpperCase() + random.nextInt(10000, 100000);
    }

    // Returns a formatted string representation of the customer’s information.
    @Override
    public String toString() {
        return "\n(Name: " + name + ", Phone: " + phoneNumber + ", PatientId: " + patientId + ")";
    }
}