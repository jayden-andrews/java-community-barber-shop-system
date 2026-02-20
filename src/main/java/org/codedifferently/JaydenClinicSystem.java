package org.codedifferently;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class JaydenClinicSystem {
    private static ArrayList<BobbyPatient> customers = new ArrayList<>();

    public static void displayOptions(Scanner sc) {
        System.out.println("Welcome! What would you like to do today? (Enter a number)\n");
        int input;

        do {
            // Prints the menu.
            System.out.println("Barber Shop Management System");
            System.out.println("1. Add a new customer.");
            System.out.println("2. View all customers.");
            System.out.println("3. Manage appointment.");
            System.out.println("4. Search for a customer.");
            System.out.println("5. Quit");
            System.out.print("Selection: ");

            // Validates the input.
            if (sc.hasNextInt()) {
                input = sc.nextInt();
            } else {
                input = -1;
            }
            sc.nextLine();

            // The regex that will be used to match the phone number input
            String regex = "^\\+?1?\\D*?(\\d{3})\\D*?(\\d{3})\\D*?(\\d{4})$";
            // Turns the regex into a pattern object.
            Pattern pattern = Pattern.compile(regex);
            String phoneNumber;

            switch (input) {
                case 1:
                    System.out.print("\nEnter your name: ");
                    String name = sc.nextLine();
                    phoneNumber = formatPhoneNumber(pattern, sc);
                    addCustomer(new BobbyPatient(name, phoneNumber));
                    break;
                case 2:
                    System.out.println(getCustomers());
                    break;
                case 3:
                    AmaniAppointment.appointmentMenu();
                    break;
                case 4:
                    phoneNumber = formatPhoneNumber(pattern, sc);
                    BobbyPatient customer = searchCustomer(phoneNumber);

                    if (customer != null) {
                        System.out.print("Customer found:");
                        System.out.println(customer + "\n");
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
                case 5:
                    System.out.println("\nExiting system...");
                    break;
                default:
                    System.out.println("\nPlease select a valid option.\n");
            }
        } while (input != 5);
    }

    public static void addCustomer(BobbyPatient customer) {
        customers.add(customer);
    }

    public static ArrayList<BobbyPatient> getCustomers() {
        return customers;
    }

    public static BobbyPatient searchCustomer(String phoneNumber) {
        for (BobbyPatient customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }
        return null;
    }

    public static String formatPhoneNumber(Pattern pattern, Scanner sc) {
        while (true) {
            // Prompts the user to enter their phone number.
            System.out.print("Enter your phone number: ");
            String phoneNumber = sc.nextLine();

            // Checks the phone number to see if it matches the pattern.
            Matcher matcher = pattern.matcher(phoneNumber);

            // If the phone number matches the expected structure
            if (matcher.matches()) {
                // Reformat into XXX-XXX-XXXX
                return (matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3));
            } else {
                System.out.println("\nInvalid phone number. Please try again.\n");
            }
        }
    }
}
