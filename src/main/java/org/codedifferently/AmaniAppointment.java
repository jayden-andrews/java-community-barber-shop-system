package org.codedifferently;
import java.util.ArrayList;
import java.util.Scanner;

public class AmaniAppointment {
    private static final String[] TIME_SLOTS = {"9:00 AM", "9:30 AM", "10:00 AM", "10:30 AM",
    "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "1:30 PM", "2:00 PM", "2:30 PM",
    "3:00 PM", "3:30 PM", "4:00 PM", "4:30 PM", "5:00 PM"};
    private static final ArrayList<AmaniAppointment> appointments = new ArrayList<>();
    private String name;
    private String haircut;
    private String timeSlot;
    private String patientId;

    // Constructor
    public AmaniAppointment(BobbyPatient customer, String haircut, String timeSlot) {
        this.name = customer.getName();
        this.haircut = haircut;
        this.timeSlot = timeSlot;
        this.patientId = customer.getPatientId();
    }

    public static void appointmentMenu(BobbyPatient customer) {
        Scanner scanner = new Scanner(System.in);
        appointmentMenu(customer, scanner);
    }


    public static void appointmentMenu(BobbyPatient customer, Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("\n=== Appointment Menu ===");
            System.out.println("1) Schedule Appointment");
            System.out.println("2) Cancel Appointment");
            System.out.println("3) Check In");
            System.out.println("4) View Schedule");
            System.out.println("0) Exit");
            System.out.println("Choose: ");

            int choice = readInt(scanner);

            AmaniAppointment temp = new AmaniAppointment(customer, "N/A", "N/A");
            switch (choice) {
                case 1 -> temp.scheduleAppointment(customer, scanner);
                break;
                case 2 -> temp.cancelAppointment(customer);
                break;
                case 3 -> temp.checkIn(customer);
                break;
                case 4 -> temp.viewSchedule();
                break;
                case 0 -> running = false;
                break;
                default -> System.out.println("Invalid option.");
            }
        }


    }

    public void scheduleAppointment(BobbyPatient customer, Scanner scanner) {
        System.out.println("\n--- Schedule Appointment ---");

        int slotIndex = promptForSlot(scanner);
        String slot = TIME_SLOTS[slotIndex];

        if (findAppointmentBySlot(slot) != null) {
            System.out.println("That time slot is already booked. Pick another.");
            return;
        }

        AmaniAppointment existing = findAppointmentByPatient(customer.getPatientId());
        if (existing != null) {
            System.out.println("You already have an appointment at " + existing.timeSlot + ".");
            System.out.println("Cancel it first if you want to reschedule.");
            return;
        }

        System.out.println("Enter haircut/service (ex: Fade, Trim, Lineup): ");
        String haircutInput = scanner.nextLine().trim();
        while (haircutInput.isEmpty()) {
            System.out.println("Service cannot be empty. Enter haircut/service: ");
            haircutInput = scanner.nextLine().trim();
        }

        appointments.add(new AmaniAppointment(customer, haircutInput, slot));
        System.out.println("Booked! " + customer.getName() + " at " + slot + " for " + haircutInput + ".");

    }

    public void cancelAppointment(BobbyPatient customer) {
        System.out.println("\n--- Cancel Appointment ---");

        AmaniAppointment appt = findAppointmentByPatient(customer.getPatientId());
        if (appt == null) {
            System.out.println("No appointment found for " + customer.getName() + ".");
            return;
        }
        appointments.remove(appt);
        System.out.println("Canceled appointment at " + appt.timeSlot + " for " + customer.getName() + ".");

    }

    public void checkIn(BobbyPatient customer) {
        System.out.println("\n--- Check In ---");

        AmaniAppointment appt = findAppointmentByPatient(customer.getPatientId());
        if (appt == null) {
            System.out.println("No appointment found for " + customer.getName() + ".");
            return;
        }

        System.out.println("Checked in: " + appt.name + " | " + appt.timeSlot + " | " + appt.haircut);
    }

    public void viewSchedule() {
        System.out.println("\n--- Full Schedule ---");

        for (String slot : TIME_SLOTS) {
            AmaniAppointment appt = findAppointmentBySlot(slot);
            if (appt == null) {
                System.out.println(slot + " -> Available");
            } else {
                System.out.println(slot + " -> BOOKED " + appt.name + " (" + appt.haircut + ")");
            }
        }
    }

    private int promptForSlot(Scanner scanner) {
        System.out.println("Available time slots:");
        for (int i = 0; i < TIME_SLOTS.length; i++) {
            System.out.println((i + 1) + ")" + TIME_SLOTS[i]);
        }

        System.out.println("Pick a slot number (1-" + TIME_SLOTS.length + ": ");
        int slotNum = readInt(scanner);
        while (slotNum < 1 || slotNum > TIME_SLOTS.length) {
            System.out.println("Invalid slot. Pick 1-" + TIME_SLOTS.length + ": ");
            slotNum = readInt(scanner);
        }
        return slotNum -1;
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Enter a number: ");
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private static AmaniAppointment findAppointmentBySlot(String slot) {
        for (AmaniAppointment appt : appointments) {
            if (appt.timeSlot.equals(slot)) {
                return appt;
            }
        }
        return null;
    }

    private static AmaniAppointment findAppointmentByPatient(String patientId) {
        for (AmaniAppointment appt : appointments) {
            if (appt.patientId.equals(patientId)) {
                return appt;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Appointment{name='" + name + "', patientId='" + patientId + "', timeSlot='" + timeSlot + "', haicut='"
                + haircut + "'}";
    }
}
