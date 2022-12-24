package data.desing_classes;

import javafx.scene.image.Image;

public class TextData {

    private String username;
    private String name;
    private String text;

    private String time;
    private Image image;
    private boolean left;

    public TextData(String username,String name, String text,String time, Image image, boolean left) {
        this.username = username;
        this.name = name;
        this.text = text;
        this.time = time;
        this.image = image;
        this.left = left;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
}
