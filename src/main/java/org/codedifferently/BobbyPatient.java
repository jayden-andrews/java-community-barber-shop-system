package org.codedifferently;
import java.util.Random;

public class BobbyPatient {
    private String name;
    private String phoneNumber;
    private final String patientId;

    // Constructor
    public BobbyPatient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.patientId = generatePatientId(new Random());
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public String generatePatientId(Random random) {
        return name.substring(0, 3).toUpperCase() + random.nextInt(10000, 100000);
    }

    @Override
    public String toString() {
        return "\n(Name: " + name + ", Phone: " + phoneNumber + ", PatientId: " + patientId + ")";
    }
}