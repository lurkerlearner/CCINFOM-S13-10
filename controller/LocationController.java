package controller;

import DAO.*;
import model.*;
import java.util.*;

public class LocationController {
    private LocationDAO locationDAO;

    public LocationController() {
        locationDAO = new LocationDAO();
    }

    public boolean addLocation(String street, String city, String zip) {
        Location location = new Location();
        location.setStreet(street);
        location.setCity(city);
        location.setZip(zip);

        return locationDAO.addLocation(location) > 0;
    }

    public List<Location> getAllLocations() {
        return locationDAO.getAllLocations();
    }

    public List<Location> searchLocationById(String idStr){
        try {
            int id = Integer.parseInt(idStr);
            return locationDAO.searchLocations("id", id);
        } catch (NumberFormatException e) {
            return List.of();
        }
    }

    public List<Location> searchLocationByStreet(String street){
        return locationDAO.searchLocations("street",street);
    }

    public List<Location> searchLocationByCity(String city){
        return locationDAO.searchLocations("city",city);
    }

    public List<Location> searchLocationByZip(String zip){
        return locationDAO.searchLocations("zip", zip);
    }

    public Location getLocationById(int id) {
        return locationDAO.getLocationById(id);
    }
}

