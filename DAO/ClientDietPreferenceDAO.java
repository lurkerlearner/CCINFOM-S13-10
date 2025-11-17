package DAO;

import model.*;
import app.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDietPreferenceDAO {

    public boolean add(ClientDietPreference cdp) {

        String checkClient = "SELECT COUNT(*) FROM client WHERE client_id = ?";
        String checkDiet = "SELECT COUNT(*) FROM diet_preference WHERE diet_preference_id = ?";
        String checkDuplicate =
                "SELECT COUNT(*) FROM client_diet_preference WHERE client_id = ? AND diet_preference_id = ?";
        String insert =
                "INSERT INTO client_diet_preference (diet_preference_id, client_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(checkClient)) {
                ps.setInt(1, cdp.getClientID());
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return false; // client not found
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(checkDiet)) {
                ps.setInt(1, cdp.getDietPreferenceID());
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return false; // diet preference not found
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(checkDuplicate)) {
                ps.setInt(1, cdp.getClientID());
                ps.setInt(2, cdp.getDietPreferenceID());
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    return false; // already exists
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(insert)) {
                ps.setInt(1, cdp.getDietPreferenceID());
                ps.setInt(2, cdp.getClientID());
                return ps.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<ClientDietPreference> getAll() {
        List<ClientDietPreference> list = new ArrayList<>();
        String sql = "SELECT * FROM client_diet_preference ORDER BY client_id";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ClientDietPreference(
                        rs.getInt("diet_preference_id"),
                        rs.getInt("client_id")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ClientDietPreference> searchByDietPref(int dietPrefID) {
        List<ClientDietPreference> list = new ArrayList<>();
        String sql = "SELECT * FROM client_diet_preference WHERE diet_preference_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, dietPrefID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ClientDietPreference(
                        rs.getInt("diet_preference_id"),
                        rs.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ClientDietPreference> searchByClient(int clientID) {
        List<ClientDietPreference> list = new ArrayList<>();
        String sql = "SELECT * FROM client_diet_preference WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new ClientDietPreference(
                        rs.getInt("diet_preference_id"),
                        rs.getInt("client_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
