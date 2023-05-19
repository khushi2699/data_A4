package org.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Authentication {

    private final ArrayList<String> credData = new ArrayList<>();

    // This function is used to authenticate user using username and password
    public boolean authenticatePassword(String username, String password) {

        try{

            File obj = new File("src/CustomFiles/credentials");

            Scanner reader = new Scanner(obj);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                String[] arr = data.split(" ");
                credData.add(arr[2]);

                if (Objects.equals(username, arr[0]) && Objects.equals(password, arr[1]))
                    return true;
            }


        } catch (Exception e) {
            return false;
        }

        return false;
    }

    // Once the user is authenticated with username and password then this function is called
    // And this is used for 2FA by asking security question.
    public boolean authenticateAnswer (String answer) {

        for (String credDatum : credData) {
            if (Objects.equals(answer, credDatum)) return true ;
        }

        return false;

    }

}
