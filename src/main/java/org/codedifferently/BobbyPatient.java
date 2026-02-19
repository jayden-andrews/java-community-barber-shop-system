package org.codedifferently;
import java.util.ArrayList;
import java.util.Random;

public class BobbyPatient {
    private String name;
    private String phoneNumber;
    private final String patientId;
    private String haircut;

    // Constructor
    public BobbyPatient(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.patientId = generatePatientId(new Random());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPatientId() {
        return patientId;
    }

    public String generatePatientId(Random random) {
        return name.substring(0, 3).toUpperCase() + random.nextInt(10000, 100000);
    }


}
