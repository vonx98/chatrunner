package sql;

import javafx.scene.image.Image;

import java.io.PrintStream;

public class UserSettings {

    private int ID;
    private String USERNAME;
    private String PASSWORD;

    private int REMEMBER;

    public UserSettings() {
    }

    public UserSettings(int ID, String USERNAME, String PASSWORD, int REMEMBER) {
        this.ID = ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.REMEMBER = REMEMBER;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getREMEMBER() {
        return REMEMBER;
    }

    public void setREMEMBER(int REMEMBER) {
        this.REMEMBER = REMEMBER;
    }
}
