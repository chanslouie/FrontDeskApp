/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lC
 */
public class StorageRepository {

    private Facility facility;
    private ArrayList<Storage> storages;

    public StorageRepository(Facility facility, ArrayList<Storage> storages) {
        this.facility = facility;
        this.storages = storages;
    }

    public Facility GetFacility() {
        return facility;
    }

    public int TotalStorage() {
        int total = 0;
        for (Storage s : storages) {
            total = total + s.GetStorageAvailability();
        }
        return total;

    }

    public Storage GetStorage(String type) {
        for (Storage storage : storages) {
            if (storage.GetStorageType().equals(type)) {
                return storage;
            }
        }
        return null; 
    }

}
