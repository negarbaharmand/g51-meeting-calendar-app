package se.lexicon.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import se.lexicon.exception.AuthenticationFailedException;

import java.security.SecureRandom;
import java.util.Random;

public class User {
    private final String username;
    private String password;
    private boolean expired;

    //Register a new user
    public User(String username) {
        this.username = username;
        newPassword();
    }

    //Authentication
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //Fetch data from DB
    public User(String username, String password, boolean expired) {
        this.username = username;
        this.password = password;
        this.expired = expired;
    }

    public String getUsername() {
        return username;
    }


    public boolean isExpired() {
        return expired;
    }

    //For hashing the password
    public String getHashedPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.encode(this.password);
    }

    //For comparing the raw and hashed password
    public void checkHash(String hashedPassword) throws AuthenticationFailedException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isEqual = passwordEncoder.matches(this.password, hashedPassword);
        if (!isEqual) {
            throw new AuthenticationFailedException("Authentication failed. Invalid credentials.");
        }
    }


    public String userInfo() {
        return "Username: " + username + " Password: " + password;
    }

    private String generateRandomPassword() {
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 10;
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());  //1
            char randomChar = allowedCharacters.charAt(randomIndex);  //B
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public void newPassword() {
        this.password = generateRandomPassword();
    }

}
