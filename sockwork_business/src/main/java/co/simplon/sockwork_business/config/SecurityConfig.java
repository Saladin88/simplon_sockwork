package co.simplon.sockwork_business.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class SecurityConfig {

//    @Value("${co.simplon.sockwork_business.hash.iteration}")
//    private Integer hashTourCount;

    public static String hashEncoder(String password){
        return new BCryptPasswordEncoder(12).encode(password);
    };

    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword, storedHash);  // Vérifie si le mot de passe correspond au hachage
    }

}
