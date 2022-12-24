package data.req_classes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReqFuncs {

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

    ByteArrayOutputStream byteArrayOutputStream ;
    public byte[] getImageSize(byte[] image) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(image));
        byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);
        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
        return size;
    }

    public byte[] getImage() {
        return byteArrayOutputStream.toByteArray();
    }


}
