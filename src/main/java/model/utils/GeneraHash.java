package model.utils;

import org.mindrot.jbcrypt.BCrypt;

public class GeneraHash {
    public static void main(String[] args) {
        String password = "adminpass";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt(12));
        System.out.println(hashed);
    }
}
