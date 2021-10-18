package banking;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        Map<String, Account> mapAccount = new HashMap<>();
        DataBaseManager newconnection = new DataBaseManager();

        boolean dbCreated = false;

        String akcija = "";

        while (!akcija.equals("0")) {
            if (!dbCreated) {
                newconnection.makeConnection();
                dbCreated = true;
            }
            System.out.println("MENU \n" +
                    "1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");
            akcija = s.next();
            if (akcija.equals("1")) {
                Account newaccount = new Account();
                System.out.println("Your card has been created:\n" +
                        "Your card number:\n" +
                        newaccount.getCardNumber() + "\n" +
                        "Your card PIN:\n" +
                        newaccount.getPin());
                mapAccount.put(newaccount.getCardNumber(), newaccount);
                newconnection.addAccount(newaccount);

            }
            if (akcija.equals("2")) {
                System.out.println("Enter your card number:");
                String cardNumber = s.next();
                System.out.println("Enter your PIN:");
                String pin = s.next();

                /*
                - enter cardNumber and pin from keyboard
                - get account from map by cardNumber
                - if account found:
                    - check if pin is ok
                        - display login menu
                 */

                Account foundAccount = mapAccount.get(cardNumber);
                if (newconnection.isCardInDatabase(cardNumber, pin)) {
                    System.out.println("You have successfully logged in!");

                    // print login menu
                    while (!akcija.equals("0")) {
                        System.out.println("""
                                1. Balance
                                2. Add income
                                3. Do Transfer
                                4. Close Account
                                5. Log out
                                0. Exit""");
                        akcija = s.next();
                        if (akcija.equals("1")) {
                            System.out.println("Balance:" + foundAccount.getBalance());

                        }
                        if (akcija.equals("2")) {
                            break;
                        }
                    }

                } else
                    System.out.println("Wrong card number or PIN!");

            }
            if (akcija.equals("0")) {
                System.out.println("Bye!");
            }
        }
    }
}