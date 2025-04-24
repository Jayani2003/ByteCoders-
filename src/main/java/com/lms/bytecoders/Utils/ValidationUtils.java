package com.lms.bytecoders.Utils;

public class ValidationUtils {
    public static boolean validateEmail(String email){
        String emailRegex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";
        return email.matches(emailRegex);
    }

    public static boolean validatePhoneNumber(String np){
        String tpRegex = "^07[1-8]\\d{7}$";
        return np.matches(tpRegex);
    }
}