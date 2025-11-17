package controller;

import DAO.*;
import model.*;
import view.ClientDietPreferencePanel;

import javax.swing.*;
import java.util.List;

public class ClientDietPreferenceController {

    private ClientDAO clientDAO;
    private DietPreferenceDAO dietDAO;
    private ClientDietPreferenceDAO dao;

    public ClientDietPreferenceController(ClientDietPreferenceDAO dao,
                                          ClientDAO clientDAO,
                                          DietPreferenceDAO dietDAO) {
        this.dao = dao;
        this.clientDAO = clientDAO;
        this.dietDAO = dietDAO;
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

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    public List<DietPreference> getAllDietPreferences() {
        return dietDAO.getAllDietPreferences();
    }


    public boolean remove(int dietPrefID, int clientID) {
        boolean success = dao.removeMapping(dietPrefID, clientID);
        if (!success) {
            JOptionPane.showMessageDialog(null,
                    "Cannot remove the last diet preference for this client.",
                    "Deletion Not Allowed",
                    JOptionPane.WARNING_MESSAGE);
        }
        return success;
    }
}
