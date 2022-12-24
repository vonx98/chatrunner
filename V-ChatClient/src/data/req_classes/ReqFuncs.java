package data.req_classes;

import data.data_classes.UserDetail;
import data.socket_classes.SUserData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ReqFuncs {

    Map<String,byte[]> images = new HashMap<>();
    ObservableList<SUserData> userList = FXCollections.observableArrayList();

    public String getDateTime() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        String dateTime = dtf.format(LocalDateTime.now());
        return dateTime;
    }

    public String getTime() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = dtf.format(LocalDateTime.now());
        return time;
    }


    public boolean typeControl(String data,String type) {
        if (data.contains("<type>"+type+"</type>")){
            return true;
        }
        else{
            return false;
        }

    }

    public Image urlConvertImage(String imageUrl) {
        Image image = new Image(imageUrl);
        return image;
    }

    public Image byteToImage(byte[] bytes) throws IOException {

        Image img = new Image(new ByteArrayInputStream(bytes));
        System.out.println("imageSize:"+bytes.length);
        return img;

    }

    public byte[] readFile(String filePath) throws IOException {


        ByteArrayOutputStream bos = null;
        File f = new File(filePath);
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        bos = new ByteArrayOutputStream();
        for (int len; (len = fis.read(buffer)) != -1;) {
            bos.write(buffer, 0, len);
        }

        return bos != null ? bos.toByteArray() : null;
    }

    public long getFileSizeNIO(String fileName) {

        Path path = Paths.get(fileName);

        try {

            // size of a file (in bytes)
            long bytes = Files.size(path);
            long kiloBytes = (bytes / 1024);
            return kiloBytes;

        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

    }



    public ObservableList<SUserData> getUserList(ArrayList<UserDetail> userImages) {

        userList = FXCollections.observableArrayList();
        for (UserDetail user : userImages)
        {
            SUserData userData = new SUserData(user.getImageByte(),user.getUsername(),user.getName(),user.getSurname(),Integer.parseInt(user.getLevel()));

            images.put(userData.getUsername(),userData.getImage());
            userList.add(userData);
        }

        return userList;



    }


    public Map<String, byte[]> getImages() {
        return images;
    }
}
