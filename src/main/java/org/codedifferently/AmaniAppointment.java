package org.codedifferently;

import java.util.ArrayList;
import java.util.Scanner;

// Defines the AmaniAppointmentMenu class that manages appointment scheduling and tracking.
public class AmaniAppointment {

    // Stores all available appointment time slots for the day.
    private static final String[] TIME_SLOTS = {
            "9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM",
            "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM",
            "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM",
            "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM",
            "5:00 PM"
    };

    // Stores all scheduled appointments in a shared list.
    private static final ArrayList<AmaniAppointment> appointments = new ArrayList<>();

    // Stores the customer’s name for the appointment.
    private String name;

    // Stores the haircut or service selected for the appointment.
    private String haircut;

    // Stores the selected time slot for the appointment.
    private String timeSlot;

    // Stores the unique customer ID associated with the appointment.
    private String customerId;

    // Tracks whether the customer has checked in for the appointment.
    private boolean checkedIn;

    // Constructor to instantiate a new AmaniAppointmentMenu object using customer and appointment details.
    public AmaniAppointment(BobbyPatient customer, String haircut, String timeSlot) {
        this.name = customer.getName();
        this.haircut = haircut;
        this.timeSlot = timeSlot;
        this.customerId = customer.getCustomerId();
        this.checkedIn = false;
    }

    // Displays the appointment menu and processes user selections until the user exits.
    public static ArrayList<AmaniAppointment> appointmentMenu(BobbyPatient customer, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Appointment Menu ===");
            System.out.println("1) Schedule Appointment");
            System.out.println("2) Cancel Appointment");
            System.out.println("3) Check In");
            System.out.println("4) View Schedule");
            System.out.println("0) Exit");
            System.out.print("Choose: ");

            int choice = readInt(scanner);

            // Creates a temporary appointment object to access instance methods.
            AmaniAppointment temp = new AmaniAppointment(customer, "N/A", "N/A");

            switch (choice) {
                case 1 -> temp.scheduleAppointment(customer, scanner);
                case 2 -> temp.cancelAppointment(customer);
                case 3 -> temp.checkIn(customer);
                case 4 -> temp.viewSchedule();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }

        // Returns the list of all scheduled appointments.
        return appointments;
    }

    // Schedules a new appointment if the selected slot and customer are valid.
    public void scheduleAppointment(BobbyPatient customer, Scanner scanner) {
        System.out.println("\n--- Schedule Appointment ---");

        int slotIndex = promptForSlot(scanner);
        String slot = TIME_SLOTS[slotIndex];

        // Checks whether the selected time slot is already booked.
        if (findAppointmentBySlot(slot) != null) {
            System.out.println("That time slot is already booked. Pick another.");
            return;
        }

        // Checks whether the customer already has an existing appointment.
        AmaniAppointment existing = findAppointmentByCustomer(customer.getCustomerId());
        // Tells the customer to reschedule if they already have an appointment.
        if (existing != null) {
            System.out.println("You already have an appointment at " + existing.timeSlot + ".");
            System.out.println("Cancel it first if you want to reschedule.");
            return;
        }

        // Prompts the user to enter the haircut that they want.
        System.out.println("Enter haircut/service (ex: Fade, Trim, Lineup): ");
        String haircutInput = scanner.nextLine().trim();

        // Validates that the haircut input is not empty.
        while (haircutInput.isEmpty()) {
            System.out.println("Service cannot be empty. Enter haircut/service: ");
            haircutInput = scanner.nextLine().trim();
        }

        // Adds the new appointment to the appointments list.
        appointments.add(new AmaniAppointment(customer, haircutInput, slot));

        System.out.println("Booked! " + customer.getName() + " at " + slot + " for " + haircutInput + ".");
    }

    // Cancels customer’s existing appointment if one exists.
    public void cancelAppointment(BobbyPatient customer) {
        System.out.println("\n--- Cancel Appointment ---");

        // Finds the appointment for the customer provided as an argument
        AmaniAppointment appt = findAppointmentByCustomer(customer.getCustomerId());

        // Checks whether an appointment exists for the customer.
        if (appt == null) {
            System.out.println("No appointment found for " + customer.getName() + ".");
            return;
        }

        // Removes the appointment from the appointments list.
        appointments.remove(appt);

        System.out.println("Canceled appointment at " + appt.timeSlot + " for " + customer.getName() + ".");
    }

    // Marks the customer as checked in for their appointment.
    public void checkIn(BobbyPatient customer) {
        System.out.println("\n--- Check In ---");

        AmaniAppointment appt = findAppointmentByCustomer(customer.getCustomerId());

        // Checks whether an appointment exists before checking in.
        if (appt == null) {
            System.out.println("No appointment found for " + customer.getName() + ".");
            return;
        }

        // Updates the appointment’s checked-in status to true.
        appt.setCheckedIn(true);

        System.out.println("Checked in: " + appt.name + " | " + appt.timeSlot + " | " + appt.haircut);
    }

    // Displays the full schedule and indicates whether each slot is available or booked.
    public void viewSchedule() {
        System.out.println("\n--- Full Schedule ---");

        for (String slot : TIME_SLOTS) {
            AmaniAppointment appt = findAppointmentBySlot(slot);

            // Displays availability if no appointment exists for the slot.
            if (appt == null) {
                System.out.println(slot + " -> Available");
            } else {
                // Changes output depending on whether the customer has checked in for their appointment.
                if (appt.checkedIn) {
                    System.out.println(slot + " -> BOOKED " + appt.name + " (" + appt.haircut + ") Checked In?: Yes");
                } else {
                    System.out.println(slot + " -> BOOKED " + appt.name + " (" + appt.haircut + ") Checked In?: No");
                }
            }
        }
    }

    // Prompts the user to select a valid time slot number.
    private int promptForSlot(Scanner scanner) {
        System.out.println("Available time slots:");

        for (int i = 0; i < TIME_SLOTS.length; i++) {
            System.out.println((i + 1) + ")" + TIME_SLOTS[i]);
        }

        System.out.println("Pick a slot number (1-" + TIME_SLOTS.length + "): ");
        int slotNum = readInt(scanner);

        // Validates that the selected slot number is within range.
        while (slotNum < 1 || slotNum > TIME_SLOTS.length) {
            System.out.println("Invalid slot. Pick 1-" + TIME_SLOTS.length + ": ");
            slotNum = readInt(scanner);
        }

        // Returns the zero-based index of the selected slot.
        return slotNum - 1;
    }

    // Reads and validates an integer input from the user.
    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter a number: ");
        }

        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    // Searches for an appointment by time slot and returns it if found.
    private static AmaniAppointment findAppointmentBySlot(String slot) {
        for (AmaniAppointment appt : appointments) {
            if (appt.timeSlot.equals(slot)) {
                return appt;
            }
        }
        return null;
    }

    // Searches for an appointment by customer ID and returns it if found.
    private static AmaniAppointment findAppointmentByCustomer(String customerId) {
        for (AmaniAppointment appt : appointments) {
            if (appt.customerId.equals(customerId)) {
                return appt;
            }
        }
        return null;
    }

    // Returns a formatted string representation of the appointment.
    @Override
    public String toString() {
        return "Appointment{name='" + name + "', customerId='" + customerId +
                "', timeSlot='" + timeSlot + "', haircut='" + haircut + "'}";
    }

    // Returns the checked-in status of the appointment.
    public boolean getCheckedIn() {
        return this.checkedIn;
    }

    // Updates the checked-in status of the appointment.
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    // Returns the customer’s name associated with the appointment.
    public String getName() {
        return this.name;
    }

    // Returns the customer ID associated with the appointment.
    public String getCustomerId() {
        return this.customerId;
    }

    // Returns the haircut or service associated with the appointment.
    public String getHaircut() {
        return this.haircut;
    }

    // Returns the time slot associated with the appointment.
    public String getTimeSlot() {
        return this.timeSlot;
    }
}