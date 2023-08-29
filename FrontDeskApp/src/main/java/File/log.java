/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package File;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author lC
 */
public class log {

    public void appendToFile_store(int customer_id, String package_type, int quantity) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        File file = new File("log_store.txt");

        FileWriter fr = new FileWriter(file, true);
        fr.write("Created on "+dtf.format(now) + " customer_id: " + customer_id + ", packageType: " + package_type + ", quantity: " + quantity+"\n");
        fr.close();
    }

    public void appendToFile_access(int customer_id) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        File file = new File("log_access.txt");

        FileWriter fr = new FileWriter(file, true);
        fr.write("On " + dtf.format(now) + " customer_id: " + customer_id + " was retrieved.\n");
        fr.close();
    }

    public void readFile(int mode) {
        try {
            switch (mode) {
                case 1:
                    File f1 = new File("log.txt");
                    Scanner myReader1 = new Scanner(f1);
                    while (myReader1.hasNextLine()) {
                        String data = myReader1.nextLine();
                        System.out.println(data);
                    }
                    myReader1.close();
                    break;
                case 2:
                    File f2 = new File("log.txt");
                    Scanner myReader2 = new Scanner(f2);
                    while (myReader2.hasNextLine()) {
                        String data = myReader2.nextLine();
                        System.out.println(data);
                    }
                    myReader2.close();
                    break;
                default:
                    break;

            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
