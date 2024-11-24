/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project;

/**
 *
 * @author fatimabintetariq
 */

public class Branch {
    private int branchID;
    private String branchCode;
    private String name;
    private String city;
    private boolean isActive;
    private String address;
    private String phone;
    private int numOfEmployees;

    public Branch(String branchCode, String name, String city, boolean isActive, String address, String phone) {
        this.branchCode = branchCode;
        this.name = name;
        this.city = city;
        this.isActive = isActive;
        this.address = address;
        this.phone = phone;
        this.numOfEmployees = 0; // Initialize with 0 employees when the branch is created
    }

    public Branch(int branchId, String branchCode, String name, String city, boolean isActive, String address, String phone, int numEmployees) {
        this.branchID = branchId;
        this.branchCode = branchCode;
        this.name = name;
        this.city = city;
        this.isActive = isActive;
        this.address = address;
        this.phone = phone;
        this.numOfEmployees = numEmployees;
    }

    public int getBranchId() {
        return branchID;
    }

    public void setBranchId(int branchID) {
        this.branchID = branchID;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNumEmployees() {
        return numOfEmployees;
    }

    public void setNumEmployees(int numOfEmployees) {
        this.numOfEmployees = numOfEmployees;
    }

    @Override
    public String toString() {
        return "Branch ID: " + branchID + "\n" +
               "Branch Code: " + branchCode + "\n" +
               "Name: " + name + "\n" +
               "City: " + city + "\n" +
               "Active: " + (isActive ? "Yes" : "No") + "\n" +
               "Address: " + address + "\n" +
               "Phone: " + phone + "\n" +
               "Number of Employees: " + numOfEmployees;
    }
}
