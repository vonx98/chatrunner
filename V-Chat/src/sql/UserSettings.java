package sql;

public class UserSettings {

    private int ID;
    private String USERNAME;
    private String PASSWORD;

    private String NAME;

    private String SURNAME;
    private String LASTLOGIN;

    private int FIRSTLOGIN;

    private int LEVEL;
    private byte[] IMAGE;

    public UserSettings() {
    }

    public UserSettings(int ID, String USERNAME, String PASSWORD, String NAME, String SURNAME, String LASTLOGIN,int FIRSTLOGIN, int LEVEL, byte[] IMAGE) {
        this.ID = ID;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.LASTLOGIN = LASTLOGIN;
        this.FIRSTLOGIN = FIRSTLOGIN;
        this.LEVEL = LEVEL;
        this.IMAGE = IMAGE;
    }

    public int getFIRSTLOGIN() {
        return FIRSTLOGIN;
    }

    public void setFIRSTLOGIN(int FIRSTLOGIN) {
        this.FIRSTLOGIN = FIRSTLOGIN;
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

    public String getLASTLOGIN() {
        return LASTLOGIN;
    }

    public void setLASTLOGIN(String FIRSTLOGIN) {
        this.LASTLOGIN = LASTLOGIN;
    }

    public byte[] getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(byte[] IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public void setSURNAME(String SURNAME) {
        this.SURNAME = SURNAME;
    }

    public int getLEVEL() {
        return LEVEL;
    }

    public void setLEVEL(int LEVEL) {
        this.LEVEL = LEVEL;
    }
}
