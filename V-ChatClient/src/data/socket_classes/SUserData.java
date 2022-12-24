package data.socket_classes;

import javafx.scene.image.Image;

public class SUserData {

    private byte[] image;

    private String username;
    private String name;
    private String surname;
    private int level;

    public SUserData(byte[] image,String username, String name, String surname, int level) {
        this.username = username;
        this.image = image;
        this.name = name;
        this.surname = surname;
        this.level = level;
    }


    public SUserData() {
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
