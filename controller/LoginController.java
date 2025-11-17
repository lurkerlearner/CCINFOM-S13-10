package controller;

import DAO.ClientDAO;
import model.Client;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class LoginController {

    private ClientDAO clientDAO;

    public LoginController() {
        this.clientDAO = new ClientDAO();
    }

    public boolean register(String fullname, String contactNo, String password,
                            String unitDetails, int locationId, int planId, List<Integer> dietPrefIds) {
        if (clientDAO.isContactExists(contactNo)) return false;

        Client c = new Client(fullname, contactNo, password, unitDetails,
                LocalDate.now(), locationId, planId, dietPrefIds);
        int clientId = clientDAO.addClient(c);

        if (clientId > 0) {
            clientDAO.addClientDietPreferences(clientId, dietPrefIds);
            return true;
        }

        return false;
    }



    public Client login(String contact, String password) {
        return clientDAO.login(contact.trim(), password.trim());
    }

    public ClientDAO getClientDAO() {
        return clientDAO;
    }
}
