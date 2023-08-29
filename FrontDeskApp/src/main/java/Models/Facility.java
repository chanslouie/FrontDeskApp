/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author lC
 */
public class Facility {

    private String facility_name;
    private int facility_id;

    public Facility(int facility_id, String facility_name) {
        this.facility_id = facility_id;
        this.facility_name = facility_name;
    }

    public String GetFacilityName() {
        return facility_name;
    }

    public int GetFacilityId() {
        return facility_id;
    }

}
