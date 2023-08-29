/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author lC
 */
public class Customer {

    private String lastName, firstName;
    private String phoneNumber;

    public Customer(String firstName, String lastName, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String GetLastName() {
        return lastName;
    }

    public String GetFirstName() {
        return firstName;
    }

    public String GetPhoneNum() {
        return phoneNumber;
    }

}
