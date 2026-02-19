package org.codedifferently;
import java.util.ArrayList;

public class AmaniAppointment {
    private ArrayList<AmaniAppointment> appointments = new ArrayList<AmaniAppointment>();
    private String name;
    private String haircut;

    // Constructor
    public AmaniAppointment(String name, String haircut) {
        this.name = name;
        this.haircut = haircut;
    }

    public void scheduleAppointment(BobbyPatient customer) {
        // Make an appointment using constructor
        // Add that appt to appointments variable
    }

    public void cancelAppointment(BobbyPatient customer) {
        // Check the appointments array
        // If found, remove it from the appointments array
    }

    public void checkIn(BobbyPatient customer) {
        // Check the appointments array and confirm the customer's appointment
    }

    public void viewSchedule() {
        System.out.println(appointments);
    }
}
