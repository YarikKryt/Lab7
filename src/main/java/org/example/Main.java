package org.example;

import org.example.commandPattern.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static public String url = "jdbc:sqlserver://localhost:1433;databaseName=TaxiDepotApp;encrypt=true;trustServerCertificate=true;integratedSecurity=true;";
    public static void main(String[] args) throws SQLException{
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }

        try{
            Connection connection;
            connection = DriverManager.getConnection(url);
            if (connection != null) {
                System.out.println("Connected");
            }

            while (true) {
                Statement statement = connection.createStatement();
                AccountManager acc = new AccountManager(statement);
                Scanner sc = new Scanner(System.in);

                // Log in or Register
                boolean programShut = false;
                System.out.println("If you want to log in   - press 0");
                System.out.println("If you want to register - press 1");
                System.out.println("If you want to close program - press 2");
                while (true) {
                    String choice = sc.nextLine();
                    if (choice.equals("0") || choice.equals("1") || choice.equals("2")) {
                        if (choice.equals("0"))
                            acc.logInAccount();
                        if (choice.equals("1"))
                            acc.createAccount();
                        if(choice.equals("2"))
                            programShut = true;
                        break;
                    }
                    System.out.println("You didn't choose between 0 to 2");
                }
                if(programShut == true)
                    break;

                // Menu
                System.out.println("Your options menu:");
                System.out.println("Show my data: press 1");
                System.out.println("Show renting car: press 2");
                System.out.println("Log out***: press 6");
                while (true) {
                    String choice = sc.nextLine();
                    if (choice.equals("1"))
                        acc.showUserData();
                    if (choice.equals("2"))
                        acc.showRentCars();
                    if (choice.equals("6"))
                        break;
                    System.out.println("Your next options menu:");
                    System.out.println("Show my data: press 1");
                    System.out.println("Show renting car: press 2");
                    System.out.println("Log out***: press 6");
                }
            }

            System.out.println("Program succesfuly closed");
            connection.close();
        } catch (SQLException e) {
            System.out.println("An error occurred:");
            throw new RuntimeException(e);
        }
    }
}
