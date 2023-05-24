package kr.pet.mvc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String phoneNumber;
    private String owner;
    private String petName;
    private String address;
    private String species;
    private int birthYear;
    private List<MedicalRecord> medicalRecords;

    public Customer() {
    }

    public Customer(String phoneNumber, String owner, String petName, String address, String species, int birthYear) {
        this.phoneNumber = phoneNumber;
        this.owner = owner;
        this.petName = petName;
        this.address = address;
        this.species = species;
        this.birthYear = birthYear;
        this.medicalRecords = new ArrayList<>();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }

    public void addMedicalRecords(MedicalRecord record) {
        this.medicalRecords.add(record);
    }
}
