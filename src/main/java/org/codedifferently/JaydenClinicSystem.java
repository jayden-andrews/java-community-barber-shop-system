package org.codedifferently;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.*;

public class JaydenClinicSystem {
    private static ArrayList<BobbyPatient> customers = new ArrayList<>();

    public void displayOptions(Scanner sc) {
        System.out.println("Welcome! What would you like to do today? (Enter a number)");
        int input;

        do {
            // Prints the menu.
            System.out.println("Barber Shop Management System");
            System.out.println("1. Add a new customer.");
            System.out.println("2. View all customers.");
            System.out.println("3. Check in a customer.");
            System.out.println("4. Search for a customer.");
            System.out.print("Selection: ");

            // Validates the input.
            if (sc.hasNextInt()) {
                input = sc.nextInt();
            } else {
                input = -1;
            }
            sc.nextLine();


            switch (input) {
                case 1:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();

                    // The regex that will be used to match the phone number input
                    String regex = "^\\+?1?\\D*?(\\d{3})\\D*?(\\d{3})\\D*?(\\d{4})$";
                    // Turns the regex into a pattern object.
                    Pattern pattern = Pattern.compile(regex);

                    String phoneNumber;

                    while (true) {
                        // Prompts the user to enter their phone number.
                        System.out.print("Enter your phone number (XXX-XXX-XXXX): ");
                        phoneNumber = sc.nextLine();

                        // Checks the phone number to see if it matches the pattern.
                        Matcher matcher = pattern.matcher(phoneNumber);

                        // If the phone number matches the expected structure
                        if (matcher.matches()) {

                            // Reformat into XXX-XXX-XXXX
                            phoneNumber = matcher.group(1) + "-" + matcher.group(2) + "-" + matcher.group(3);
                            // Exit loop
                            break;
                        } else {
                            System.out.println("\nInvalid phone number. Please try again.\n");
                        }
                    }
                    addCustomer(new BobbyPatient(name, phoneNumber));
                case 2:
                    System.out.println(getCustomers());
                    break;
                case 3:
                    // AmaniAppointment.checkIn();
                    break;
                case 4:
                    System.out.println("Enter customer id: ");
                    String customerId = sc.nextLine();
                    findCustomer(customerId);
                    break;
                case 5:
                    System.out.println("Exiting system.");
                default:
                    System.out.println("\nPlease select a valid option.\n");
            }
        } while (input != 4);
    }

    public void addCustomer(BobbyPatient customer) {
        customers.add(customer);
    }

    public static ArrayList<BobbyPatient> getCustomers() {
        return customers;
    }

    public void findCustomer(String customerId) {
        // Code goes here
    }

}
