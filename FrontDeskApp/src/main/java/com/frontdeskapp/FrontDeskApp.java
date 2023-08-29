/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.frontdeskapp;

import Database.DatabasePointer;
import Models.Customer;
import Models.Facility;
import Models.StorageRepository;
import View.View_CheckStorage;
import View.View_RetrievePackage;
import View.View_StorePackages;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lC
 */
public class FrontDeskApp {

    public static void main(String[] args) throws Exception {
        Connection conn = GetConnection("DeskApp");
        DatabasePointer dbPointer = new DatabasePointer(conn);
        dbPointer.createAll();

        int option = 0;

        Scanner scn = new Scanner(System.in);

        do {
            System.out.println("======\nOptions:");
            System.out.println("1 - Store Packages");
            System.out.println("2 - Retrieve Package");
            System.out.println("3 - Check Storage");
            System.out.println("4 - Exit");
            System.out.print("Enter your choice: ");
            option = scn.nextInt();

            switch (option) {
                case 1:
                    View_StorePackages v1 = new View_StorePackages();
                    v1.StorePackages(dbPointer, option);
                    break;
                case 2:
                    View_RetrievePackage v2 = new View_RetrievePackage();
                    v2.RetrievePackage(dbPointer);
                    break;
                case 3:
                    View_CheckStorage v3 = new View_CheckStorage();
                    v3.CheckStorage(dbPointer);
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        } while (option != 4);

        scn.close();

    }

    private static Connection GetConnection(String dbName) {
        Connection conn = null;
        try {
            //Connects to a db
            //if not existing creates a db

            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName + ".db");
            System.out.println("Successfully Connected to " + dbName);
        } catch (Exception e) {
            System.out.print("Cannot Connect to " + dbName);
        }

        return conn;
    }
}
