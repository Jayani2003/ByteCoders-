package com.lms.bytecoders.Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String hashPassword(String pwd) {
        return BCrypt.hashpw(pwd, BCrypt.gensalt(12));
    }

    public static boolean verifyPassword(String pwd, String hashedPassword){
        return BCrypt.checkpw(pwd, hashedPassword);
    }
}