package data.data_classes;

import java.io.Serializable;

public class UserDetail implements Serializable {

    private String username;

    private String name;

    private String surname;

    private String level;

    private byte[] imageByte;

    public UserDetail(String username, String name, String surname, String level, byte[] imageByte) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.level = level;
        this.imageByte = imageByte;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }
}
