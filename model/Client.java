package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private int clientID;
    private String name;
    private String contactNo;
    private String password;
    private String unitDetails;
    private LocalDate dateCreated;
    private int locationID;
    private int planID;
    private List<Integer> dietPreferenceIDs;


    public Client() {}

    public Client(String name, String contactNo, String password, String unitDetails,
                  LocalDate dateCreated, int locationID, int planID, List<Integer> dietPreferenceIDs) {
        this.name = name;
        this.contactNo = contactNo;
        this.password = password;
        this.unitDetails = unitDetails;
        this.dateCreated = dateCreated;
        this.locationID = locationID;
        this.planID = planID;
        this.dietPreferenceIDs = new ArrayList<>(dietPreferenceIDs); // copy values
    }

    public Client(int clientID, String name, String contactNo, String password,
                  String unitDetails, LocalDate dateCreated, int locationID, int planID, List<Integer> dietPreferenceIDs) {
        this.clientID = clientID;
        this.name = name;
        this.contactNo = contactNo;
        this.password = password;
        this.unitDetails = unitDetails;
        this.dateCreated = dateCreated;
        this.locationID = locationID;
        this.planID = planID;
        this.dietPreferenceIDs = new ArrayList<>(dietPreferenceIDs); // copy values
    }


    public int getClientID() { return clientID; }
    public void setClientID(int clientID) { this.clientID = clientID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUnitDetails() { return unitDetails; }
    public void setUnitDetails(String unitDetails) { this.unitDetails = unitDetails; }

    public LocalDate getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public int getLocationID() { return locationID; }
    public void setLocationID(int locationID) { this.locationID = locationID; }

    public int getPlanID() { return planID; }
    public void setPlanID(int planID) { this.planID = planID; }

    public List<Integer> getDietPreferenceIDs() {return dietPreferenceIDs;}

    public void setDietPreferenceIDs(List<Integer> dietPreferenceIDs) {this.dietPreferenceIDs = dietPreferenceIDs;}
}
