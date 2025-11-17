package controller;

import DAO.*;
import model.*;

import java.util.List;

public class ClientDietPreferenceController {

    private ClientDietPreferenceDAO dao;

    public ClientDietPreferenceController() {
        dao = new ClientDietPreferenceDAO();
    }

    public boolean add(int dietPrefID, int clientID) {
        ClientDietPreference c = new ClientDietPreference(dietPrefID, clientID);
        return dao.add(c);
    }

    public List<ClientDietPreference> getAll() {
        return dao.getAll();
    }

    public List<ClientDietPreference> searchByDietPref(int id) {
        return dao.searchByDietPref(id);
    }

    public List<ClientDietPreference> searchByClient(int id) {
        return dao.searchByClient(id);
    }
}
