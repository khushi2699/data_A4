package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Please provide username: ");
        String username = sc.nextLine();

        System.out.println("Please provide password: ");
        String password = sc.nextLine();

        Authentication authentication = new Authentication();

        boolean res = authentication.authenticatePassword(username, password);

        if (res) {
            System.out.println("For 2FA Please answer security question");
            System.out.println("What is my favourite animal: ");
            String answer = sc.nextLine();

            boolean isAuthenticated = authentication.authenticateAnswer(answer);

            if(isAuthenticated) {
                System.out.println("Authenticated!!");

                System.out.println("Please select the choice: ");
                System.out.println("Press 1 for create");
                System.out.println("Press 2 for insert");
                System.out.println("Press 3 for update");
                System.out.println("Press 4 for select");
                System.out.println("Press 5 for delete");
                int choice = sc.nextInt();

                QuerySelector querySelector = new QuerySelector();

                querySelector.querySelector(choice);

            } else {
                System.out.println("Authentication Failed!!");
            }
        } else {
            System.out.println("Wrong Password!!");
        }
    }
}