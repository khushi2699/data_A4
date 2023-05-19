package org.example;

import java.util.Scanner;

public class QuerySelector {

    public void querySelector(int choice) {

        Query query = new Query();
        Scanner sc = new Scanner(System.in);

        switch (choice) {
            case 1 -> {
                System.out.println("Please enter the create query according to given format!");
                System.out.println("Format:");
                System.out.println("CREATE TABLE table_name (id int, name string, age int)");
                String createQuery = sc.nextLine();

                query.CREATE(createQuery);
            }
            case 2 -> {
                System.out.println("Please enter the insert query according to given format!");
                System.out.println("Format:");
                System.out.println("INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...)");
                String insertQuery = sc.nextLine();

                query.INSERT(insertQuery);
            }
            case 3 -> {
                System.out.println("Please enter the update query according to given format!");
                System.out.println("Format:");
                System.out.println("UPDATE table_name SET (column1 = value1, column2 = value2) WHERE (condition)");
                String updateQuery = sc.nextLine();

                query.UPDATE(updateQuery);
            }
            case 4 -> {
                System.out.println("Please enter the select query according to given format!");
                System.out.println("Format:");
                System.out.println("SELECT * FROM table_name");
                String selectQuery = sc.nextLine();

                query.SELECT(selectQuery);
            }
            case 5 -> {
                System.out.println("Please enter the delete query according to given format!");
                System.out.println("Format:");
                System.out.println("DELETE FROM table_name WHERE (condition)");
                String deleteQuery = sc.nextLine();

                query.DELETE(deleteQuery);
            }
            default -> {
                System.out.println("Please enter correct choice!");
            }
        }

    }

}
