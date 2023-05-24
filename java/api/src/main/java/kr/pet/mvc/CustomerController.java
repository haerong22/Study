package kr.pet.mvc;

import java.util.ArrayList;
import java.util.List;

public class CustomerController {

    private List<Customer> customers;
    private MedicalRecordController recordController;

    public CustomerController(MedicalRecordController recordController) {
        this.customers = new ArrayList<>();
        this.recordController = recordController;
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public void removeCustomer(String phoneNumber) {
        for (int i = 0; i < this.customers.size(); i++) {
            if (this.customers.get(i).getPhoneNumber().equals(phoneNumber)) {
                this.customers.remove(i);
                this.recordController.removeMedicalRecord(phoneNumber);
                break;
            }
        }
    }

    public Customer findCustomer(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return customer;
            }
        }

        return null;
    }

    public boolean isPhoneNumberExist(String phoneNumber) {
        for (Customer customer : customers) {
            if (customer.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }
}
