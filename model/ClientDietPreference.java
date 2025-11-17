package model;

public class ClientDietPreference {

    private int dietPreferenceID;
    private int clientID;

    public ClientDietPreference() {}

    public ClientDietPreference(int dietPreferenceID, int clientID) {
        this.dietPreferenceID = dietPreferenceID;
        this.clientID = clientID;
    }

    public int getDietPreferenceID() {
        return dietPreferenceID;
    }

    public void setDietPreferenceID(int dietPreferenceID) {
        this.dietPreferenceID = dietPreferenceID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
}
