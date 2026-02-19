package org.codedifferently;
import java.util.ArrayList;

public class JaydenClinicSystem {
    private ArrayList<BobbyPatient> customers = new ArrayList<BobbyPatient>();

    public void displayMenu() {

    }

    public void addCustomer(BobbyPatient customer) {
        this.customers.add(customer);
    }

    public ArrayList<BobbyPatient> getCustomers() {
        return customers;
    }

    public void findCustomer(BobbyPatient customer) {
        // Code goes here
    }

}
