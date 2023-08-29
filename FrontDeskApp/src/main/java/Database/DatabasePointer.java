/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Database;

import Models.Customer;
import Models.Facility;
import Models.Storage;
import Models.StorageRepository;
import com.frontdeskapp.FrontDeskApp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lC
 */
public class DatabasePointer {

    private Connection conn;

    public DatabasePointer(Connection conn) {
        this.conn = conn;
    }

    public void createAll() {
        String createCustomerTable = "CREATE TABLE IF NOT EXISTS Customer ("
                + "customer_id integer PRIMARY KEY autoincrement,"
                + "first_name varchar(15),"
                + "last_name varchar(15),"
                + "phone_num varchar(15));";

        String createPackageTable = "CREATE TABLE IF NOT EXISTS Package ("
                + "package_id integer primary key autoincrement,"
                + "package_type varchar(15));";

        String createStorageReposTable = "CREATE TABLE IF NOT EXISTS Storage_Repository ("
                + "facility_id integer primary key autoincrement,"
                + "facility_name varchar(30));";

        String createStorageAvailability = "CREATE TABLE IF NOT EXISTS Storage_availability("
                + "storage_avail_id integer primary key autoincrement,"
                + "facility_id integer,"
                + "storage_type varchar (10),"
                + "storage_availability integer,"
                + "FOREIGN KEY(facility_id) REFERENCES Storage_Repository(facility_id));";

        String createTransactionTable = "CREATE TABLE IF NOT EXISTS Transactions ("
                + "transac_id integer primary key autoincrement,"
                + "customer_id integer,"
                + "storage_avail_id integer,"
                + "package_id integer,"
                + "package_quantity integer,"
                + "details varchar(10),"
                + "FOREIGN KEY(customer_id) REFERENCES Customer(facility_id),"
                + "FOREIGN KEY(storage_avail_id) REFERENCES Storage_availability(storage_avail_id),"
                + "FOREIGN KEY(package_id) REFERENCES Package(package_id));";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(createCustomerTable);
            stmt.execute(createPackageTable);
            stmt.execute(createStorageReposTable);
            stmt.execute(createTransactionTable);
            stmt.execute(createStorageAvailability);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int InsertCustomer(Customer cust) {
        String insert_customers = "INSERT INTO Customer(first_name,last_name,phone_num) VALUES(?,?,?)";
        int generatedKey = -1;

        try {
            PreparedStatement pstmt = conn.prepareStatement(insert_customers, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cust.GetFirstName());
            pstmt.setString(2, cust.GetLastName());
            pstmt.setString(3, cust.GetPhoneNum());
            int checkExec = pstmt.executeUpdate();

            if (checkExec > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    generatedKey = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return generatedKey;
    }

    private ArrayList GetFacility() {
        ResultSet rs = null;
        Facility fc;
        ArrayList<Facility> facility = new ArrayList<>();
        String select_facility_id = "select facility_id, facility_name from Storage_Repository;";
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(select_facility_id);
            while (rs.next()) {
                fc = new Facility(rs.getInt("facility_id"), rs.getString("facility_name"));
                facility.add(fc);
            }
            stmt.close();
            return facility;
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return facility;
    }

    public ArrayList GetStoragesAvail() {
        String select_facilities = "select storage_avail_id, Storage_availability.facility_id, facility_name, storage_type, storage_availability "
                + "from Storage_availability INNER JOIN Storage_Repository "
                + "on Storage_availability.facility_id = Storage_Repository.facility_id "
                + "WHERE Storage_Repository.facility_id = ?;";

        ArrayList<Facility> facility = this.GetFacility();
        ArrayList<StorageRepository> storageAvail = new ArrayList<>();
        try {

            for (int x = 0; x < facility.size(); x++) {
                PreparedStatement pstmt = conn.prepareStatement(select_facilities);
                pstmt.setInt(1, facility.get(x).GetFacilityId());
                ResultSet rs = pstmt.executeQuery();

                ArrayList<Storage> storages = new ArrayList<>();
                while (rs.next()) {
                    Storage storage = new Storage(rs.getInt("storage_avail_id"), rs.getString("storage_type"), rs.getInt("storage_availability"));
                    storages.add(storage);
                }
                StorageRepository sr = new StorageRepository(facility.get(x), storages);
                storageAvail.add(sr);
            }

            return storageAvail;
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return storageAvail;

    }

    public void CreateTransaction(int customer_id, int storage_avail_id, int package_id, int package_quantity, String details) {
        String insert_transaction = ""
                + "INSERT INTO Transactions (customer_id,storage_avail_id,package_id,package_quantity,details) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(insert_transaction);
            pstmt.setInt(1, customer_id);
            pstmt.setInt(2, storage_avail_id);
            pstmt.setInt(3, package_id);
            pstmt.setInt(4, package_quantity);
            pstmt.setString(5, details);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void UpdateStorageAvailability(int updatedAvailability, int facility_id, String storage_type) {

        String update_availability = ""
                + "UPDATE Storage_availability SET storage_availability = ? WHERE facility_id = ? AND storage_type = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(update_availability);
            pstmt.setInt(1, updatedAvailability);
            pstmt.setInt(2, facility_id);
            pstmt.setString(3, storage_type);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ResultSet DisplayPackage(int customer_id) {
        ResultSet rs = null;
        String update_availability = ""
                + "select Customer.first_name, Customer.last_name, Customer.phone_num, \n"
                + "Package.package_type, Transactions.package_quantity, Storage_Repository.facility_name, Storage_availability.storage_type\n"
                + "from \n"
                + "Transactions INNER JOIN Package on Transactions.package_id = Package.package_id\n"
                + "INNER JOIN Customer on Transactions.customer_id = Customer.customer_id\n"
                + "INNER JOIN Storage_availability on Transactions.storage_avail_id = Storage_availability.storage_avail_id \n"
                + "INNER JOIN Storage_Repository on Storage_availability.facility_id = Storage_Repository.facility_id \n"
                + "WHERE Customer.customer_id = ?;";

        try {

            PreparedStatement pstmt = conn.prepareStatement(update_availability);
            pstmt.setInt(1, customer_id);
            rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet CheckAvail_byType() {
        ResultSet rs = null;
        String select_byType = "WITH Subtotals AS (\n"
                + "    SELECT\n"
                + "        sa.storage_type,\n"
                + "        sr.facility_name,\n"
                + "        SUM(sa.storage_availability) AS total_availability\n"
                + "    FROM Storage_availability AS sa\n"
                + "    INNER JOIN Storage_Repository AS sr ON sa.facility_id = sr.facility_id\n"
                + "    GROUP BY sa.storage_type, sr.facility_name\n"
                + ")\n"
                + "SELECT storage_type, facility_name, total_availability FROM Subtotals\n"
                + "UNION ALL\n"
                + "SELECT storage_type, NULL AS facility_name, SUM(total_availability) FROM Subtotals\n"
                + "GROUP BY storage_type\n"
                + "UNION ALL\n"
                + "SELECT NULL AS storage_type, NULL AS facility_name, SUM(total_availability) FROM Subtotals;";

        try {

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(select_byType);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public ResultSet CheckAvail_byFacility() {
        ResultSet rs = null;
        String select_byFacility = "SELECT facility_name, storage_type, SUM(storage_availability) AS total_availability\n"
                + "FROM (\n"
                + "    SELECT sr.facility_name, sa.storage_type, sa.storage_availability\n"
                + "    FROM Storage_availability AS sa\n"
                + "    INNER JOIN Storage_Repository AS sr ON sa.facility_id = sr.facility_id\n"
                + "    UNION ALL\n"
                + "    SELECT sr.facility_name, NULL AS storage_type, SUM(sa.storage_availability) AS storage_availability\n"
                + "    FROM Storage_availability AS sa\n"
                + "    INNER JOIN Storage_Repository AS sr ON sa.facility_id = sr.facility_id\n"
                + "    GROUP BY sr.facility_name\n"
                + ") AS combined_data\n"
                + "GROUP BY facility_name, storage_type\n"
                + "ORDER BY facility_name, storage_type;";

        try {

            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery(select_byFacility);
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(FrontDeskApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

}
