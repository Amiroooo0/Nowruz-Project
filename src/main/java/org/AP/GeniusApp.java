package org.AP;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



// GeniusApp.java
public class GeniusApp {
    private List<Account> accounts;
    private Account currentUser;

    public GeniusApp() {
        this.accounts = new ArrayList<>();
    }


    private void showMainMenu(Scanner scanner) {
        System.out.println("Welcome to Genius!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                registerUser(scanner);
                break;
            case 2:
              //  loginUser(scanner);
                break;
            case 3:
                System.exit(0);
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.println("Select account type:");
        System.out.println("1. Regular User");
        System.out.println("2. Artist");
        int type = scanner.nextInt();
        scanner.nextLine();

        Account account;
        if (type == 1) {
            account = new User(username, password, email);
        } else {
            account = new Artist(username, password, email);
        }

        accounts.add(account);
        System.out.println("Registration successful!");
    }
}