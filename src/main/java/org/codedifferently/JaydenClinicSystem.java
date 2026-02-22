package org.codedifferently;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

// Defines the main clinic system that manages customers.
public class JaydenClinicSystem {

    // Stores all registered customers in the system.
    private static ArrayList<BobbyPatient> customers = new ArrayList<>();

    // Displays the main menu and processes user selections until the user quits.
    public static void displayOptions(Scanner sc) {
        System.out.println("Welcome! What would you like to do today? (Enter a number)");
        int input;

        // Stores appointment data for generating a journal upon exit.
        ArrayList<AmaniAppointmentMenu> appointments = null;

        do {
            // Displays the main menu options to the user.
            System.out.println("\n=== Barber Shop Management Menu ===");
            System.out.println("1) Add a new customer");
            System.out.println("2) View all customers");
            System.out.println("3) Manage appointment for a customer");
            System.out.println("4) Search for a customer");
            System.out.println("0) Quit");
            System.out.print("Choose: ");

            // Validates numeric input before processing.
            if (sc.hasNextInt()) {
                input = sc.nextInt();
            } else {
                input = -1;
            }
            sc.nextLine();

            // Defines the regex pattern used to validate phone number input.
            String regex = "^\\+?1?\\D*?(\\d{3})\\D*?(\\d{3})\\D*?(\\d{4})$";

            // Compiles the regex string into a Pattern object.
            Pattern pattern = Pattern.compile(regex);

            String phoneNumber;

            switch (input) {
                // Handles the creation and storage of a new customer.
                case 1:
                    System.out.print("\nEnter your name: ");
                    String name = sc.nextLine();
                    phoneNumber = formatPhoneNumber(pattern, sc);
                    addCustomer(new BobbyPatient(name, phoneNumber));
                    break;
                // Displays the full list of registered customers.
                case 2:
                    System.out.println("\nCustomers");
                    System.out.println(getCustomers());
                    break;
                // Manages appointment scheduling for an existing customer.
                case 3:
                    phoneNumber = formatPhoneNumber(pattern, sc);
                    BobbyPatient customer = searchCustomer(phoneNumber, sc);
                    if (customer != null) {
                        appointments = AmaniAppointmentMenu.appointmentMenu(customer, sc);
                    } else {
                        System.out.println("\nCustomer not in database. Please add them first.");
                    }
                    break;
                // Searches for a customer using their phone number.
                case 4:
                    phoneNumber = formatPhoneNumber(pattern, sc);
                    BobbyPatient customer2 = searchCustomer(phoneNumber, sc);

                    // Prints out customer if found.
                    if (customer2 != null) {
                        System.out.println("\nCustomer found: " + customer2);
                    // Tells the user that the customer was not found if they do not exist.
                    } else {
                        System.out.println("\nCustomer not found.");
                    }
                    break;
                // Exits the system and prints a journal of completed appointments.
                case 0:
                    // Prints out electronic journal if appointments have happened today.
                    if (appointments != null && !(appointments.isEmpty())) {
                        System.out.println("\nElectronic Journal of Completed Appointments");
                        for (AmaniAppointmentMenu appt: appointments) {
                            if (appt.getCheckedIn()) {
                                System.out.println(appt.getTimeSlot() + " -> [Name: " + appt.getName() + ", " +
                                        "Patient Id: " + appt.getPatientId() + ", " +
                                        "Haircut: " + appt.getHaircut() + "]");
                            }
                        }
                    }
                    System.out.println("\nExiting system...");
                    break;

                // Handles invalid menu selections.
                default:
                    System.out.println("\nPlease select a valid option.");
            }
        // Stopping condition. Program will exit when the user inputs 0.
        } while (input != 0);
    }

    // Adds a new customer to the customer list.
    public static void addCustomer(BobbyPatient customer) {
        customers.add(customer);
        System.out.println("\nCustomer " + customer.getName() + " (" + customer.getPatientId() + ") added!");
    }

    // Returns the list of all registered customers.
    public static ArrayList<BobbyPatient> getCustomers() {
        return customers;
    }

    // Searches for a customer using their phone number and returns the match if found.
    public static BobbyPatient searchCustomer(String phoneNumber, Scanner sc) {
        ArrayList<BobbyPatient> customersFound = new ArrayList<>();
        for (BobbyPatient customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                customersFound.add(customer);
            }
        }
        // Returns null if the customer is not found.
        if (customersFound.isEmpty()) {
            return null;
        // Returns the customer if there are no duplicate phoneNumber.
        } else if (customersFound.size() == 1) {
            return customersFound.getFirst();
        // Prompts the user to select by patient id since more than one customer used the same phone number.
        } else {
            System.out.println("\nMultiple customers found: " + customersFound + "\n");
            while(true) {
                System.out.print("Enter patient id: ");
                String id = sc.nextLine();
                for (BobbyPatient customer : customers) {
                    if (customer.getPatientId().equals(id)) {
                        return customer;
                    }
                }
                System.out.println("Invalid patient id. Try again.\n");
            }
        }
    }

    // Validates and formats the user’s phone number input using the provided pattern.
    public static String formatPhoneNumber(Pattern pattern, Scanner sc) {
        while (true) {
            // Prompts the user to enter their phone number.
            System.out.print("Enter your phone number: ");
            String phoneNumber = sc.nextLine();

            // Creates a Matcher object to compare input against the pattern.
            Matcher matcher = pattern.matcher(phoneNumber);

            // Checks whether the phone number matches the expected format.
            if (matcher.matches()) {
                // Formats the number into XXX-XXX-XXXX structure.
                return (matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
            } else {
                // Displays an error message if validation fails.
                System.out.println("\nInvalid phone number. Please try again.\n");
            }
        }
    }
}