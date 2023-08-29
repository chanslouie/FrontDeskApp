/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Database.DatabasePointer;
import File.log;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lC
 */
public class View_RetrievePackage {
    
    public void RetrievePackage(DatabasePointer dbPointer) throws IOException {
        System.out.println("Retrieve Package===========");
        System.out.println("Enter Customer Id: ");
        Scanner scn = new Scanner(System.in);
        int cust_id = scn.nextInt();
        ResultSet rs = dbPointer.DisplayPackage(cust_id);
        try {
            while (rs.next()) {
                System.out.println("Package and Storage Information===");
                System.out.println("First Name: " + rs.getString(1));
                System.out.println("Last Name: " + rs.getString(2));
                System.out.println("Phone Number: " + rs.getString(3));
                System.out.println("Package Type: " + rs.getString(4));
                System.out.println("Package Quantity: " + rs.getString(5));
                System.out.println("Facility Name: " + rs.getString(6));
                System.out.println("Storage Type: " + rs.getString(7));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(View_RetrievePackage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        log transLog = new log();
        transLog.appendToFile_access(cust_id);
        
    }
    
}
