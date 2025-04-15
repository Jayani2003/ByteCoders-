package com.lms.bytecoders.Utils;

import java.io.*;
import java.util.Base64;

public class FileUtils {
    public static String convertImageToBase64(String imagePath) {
        File imageFile = new File(imagePath);
        String base64String = null;

        try {

            if (imageFile.exists()) {
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                byte[] imageData = new byte[(int) imageFile.length()];
                fileInputStream.read(imageData);
                fileInputStream.close();

                base64String = Base64.getEncoder().encodeToString(imageData);

            } else {
                System.out.println("File does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64String;
    }

    public static void convertBase64ToImage(String str, String filePath){
        OutputStream os;
        FileOutputStream fos;
        BufferedOutputStream bos;

        byte[] imgByteArray = Base64.getDecoder().decode(str);

        try {
            File file = new File(filePath);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            os = bos;

            os.write(imgByteArray);
            os.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
