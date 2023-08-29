/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Database.DatabasePointer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author lC
 */
public class View_CheckStorage {

    public void CheckStorage(DatabasePointer dbPointer) throws SQLException {
        System.out.println("Check Storage=====");
        System.out.println("1 - Check by Storage Type");
        System.out.println("2 - Check by Facility");
        Scanner scn = new Scanner(System.in);

        int option = scn.nextInt();

        switch (option) {
            case 1:
                ResultSet rs1 = dbPointer.CheckAvail_byType();
                System.out.println("Storage Type\tFacility Name\tTotal Availability");
                while (rs1.next()) {
                    String column2 = "";
                    if (rs1.getString(2) == null || rs1.getString(2).equals("")) {
                        column2 = "Total-->";
                    } else {
                        column2 = rs1.getString(2);
                    }
                    System.out.println(rs1.getString(1) + "\t\t" + column2 + "\t" + rs1.getInt(3));

                }
                System.out.println();
                break;
            case 2:
                ResultSet rs2 = dbPointer.CheckAvail_byFacility();
                System.out.println("Facility Name\tStorage Type\tTotal Availability");
                while (rs2.next()) {
                    String column2 = "";
                    if (rs2.getString(2) == null || rs2.getString(2).equals("")) {
                        column2 = "Total-->";
                    } else {
                        column2 = rs2.getString(2);
                    }
                    System.out.println(rs2.getString(1) + "\t" + column2 + "\t\t" + rs2.getInt(3));

                }
                System.out.println();
                break;
            default:
                break;
        }

    }

}
