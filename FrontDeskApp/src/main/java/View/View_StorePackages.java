/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Database.DatabasePointer;
import File.log;
import Models.Customer;
import Models.Facility;
import Models.StorageRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lC
 */
public class View_StorePackages {

    public void StorePackages(DatabasePointer dbPointer, int option) throws IOException {
        Scanner scn = new Scanner(System.in);
        String firstName, lastName, phoneNumber;
        String packageType;
        int quantity;

        ArrayList<StorageRepository> facilities = dbPointer.GetStoragesAvail();
        System.out.println("Facility to store the Package?");
        for (int x = 0; x < facilities.size(); x++) {
            System.out.println(x + 1 + " - " + facilities.get(x).GetFacility().GetFacilityName());
        }
        option = scn.nextInt();
        StorageRepository chosenSR = facilities.get(option - 1);
        Facility chosenFacility = chosenSR.GetFacility();

        System.out.println("Storing Package in " + chosenFacility.GetFacilityName() + "...");
        scn.nextLine();
        System.out.print("Enter First Name: ");
        firstName = scn.nextLine();

        System.out.print("Enter Last Name: ");
        lastName = scn.nextLine();

        System.out.print("Enter Phone Number: ");
        phoneNumber = scn.nextLine();

        System.out.print("Enter Package Type (Small, Medium, Large): ");
        packageType = scn.nextLine();

        if (chosenSR.GetStorage(packageType) == null) {
            System.out.println("Storage Unavailable");
            System.exit(0);
        }

        System.out.print("Enter " + packageType + " Package Quantity: ");
        quantity = scn.nextInt();

        int packageType_avail_Id = chosenSR.GetStorage(packageType).GetStorageId();

        int packageType_id = 0;
        switch (packageType) {
            case "Small":
                packageType_id = 1;
                break;
            case "Medium":
                packageType_id = 2;
                break;
            case "Large":
                packageType_id = 3;
                break;
            default:
                break;
        }
        int getAvailability = chosenSR.GetStorage(packageType).GetStorageAvailability();
        if (quantity <= getAvailability) {
            Customer cust = new Customer(firstName, lastName, phoneNumber);
            int customer_id = dbPointer.InsertCustomer(cust);

            int new_storageAvailable = getAvailability - quantity;
            dbPointer.UpdateStorageAvailability(new_storageAvailable, chosenFacility.GetFacilityId(), packageType);
            dbPointer.CreateTransaction(customer_id, packageType_avail_Id, packageType_id, quantity, "STORED");

            log transLog = new log();
            transLog.appendToFile_store(customer_id, packageType, quantity);
            System.out.println("Package is successfully stored with customer id: " + customer_id);

        } else {
            System.out.println("Entered Quanity of " + quantity + ", but only " + getAvailability + " " + packageType_id + " Storage left");
        }

    }
}
