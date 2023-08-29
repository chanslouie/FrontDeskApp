/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author lC
 */
public class Storage {

    private String storageType;
    private int storage_availability, storageId;

    public Storage(int storageId, String storageType, int storage_availability) {
        this.storageId = storageId;
        this.storageType = storageType;
        this.storage_availability = storage_availability;
    }

    public int GetStorageId() {
        return storageId;
    }

    public String GetStorageType() {
        return storageType;
    }

    public int GetStorageAvailability() {
        return storage_availability;
    }

}
