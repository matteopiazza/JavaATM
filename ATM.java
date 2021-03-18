/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaatm;

/**
 *
 * @author Matteo
 */

import java.util.Scanner;

public class ATM {
    
     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        bank theBank = new bank("Big Bank");
        

        
        

        
        user currentUser;
        
      
        String newFName;
        String newLName;
        String newPin;
        String newBank;

        System.out.println("Enter First Name:");
        newFName = sc.nextLine();
        System.out.println("Enter Last Name:");
        newLName = sc.nextLine();
        System.out.println("Enter 4 digit pin");
        newPin = sc.nextLine();

        user newUser = theBank.addUser(newFName, newLName, newPin);  
        
        account newAccount = new account("Checking", newUser, theBank);
        newUser.addAccount(newAccount);
          
          
        while(true){
            //stay in login prompt
            currentUser = ATM.mainMenuPrompt(theBank, sc);
            //stay in main menu prompt
            ATM.printUserMenu(currentUser, sc);
        }
        
    }
     
    
     public static user mainMenuPrompt(bank theBank, Scanner sc){
         String userID;
         String pin;
         user authUser;
         
         do{
             System.out.printf("\n\nWelcome to %s\n", theBank.getName());
             System.out.printf("Enter User ID: \n");
             userID = sc.nextLine();
             System.out.print("Enter Pin: ");
             pin = sc.nextLine();
             
             //get user object that corresponds to ID and pin
             authUser = theBank.userLogin(userID,pin);
             if(authUser == null){
                 System.out.println("Incorrect user ID/pin combination. "+"Please Try Again");
                 
             }
         }while(authUser == null);//loop until login successful
         return authUser;
     }
     
     public static void printUserMenu(user theUser, Scanner sc){
         //print summary of accounts
         theUser.printAccountsSummary();
        
         int choice;
         do{
             System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
             System.out.println("   1)Show Account Transaction History");
             System.out.println("   2)Withdrawal");
             System.out.println("   3)Deposit");
             System.out.println("   4)Transfer");
             System.out.println("   5)Quit");
             System.out.println();
             System.out.print("Enter Choice: ");
             choice = sc.nextInt();
             
             if(choice<1 || choice > 5){
                 System.out.println("Invalid choice");
             }
         }while(choice<1|| choice > 5);
         
         switch(choice){
            case 1:
             ATM.showTransHistory(theUser, sc);
                 break;
            case 2:
             ATM.withdrawalFunds(theUser, sc);
                 break;     
            case 3:
             ATM.depositFunds(theUser, sc);
                 break;
            case 4:
             ATM.transferFunds(theUser, sc);
                 break;    
            case 5:
                sc.nextLine();
                break;
         }
         
         //redisplay menu unless quit
         if(choice !=5){
             ATM.printUserMenu(theUser, sc);
         }
     }

    public static void showTransHistory(user theUser, Scanner sc) {
        int theAcct;
        //choose account
        do{
           System.out.printf("Enter the number 1-%d of the account\n "+"whose transactions you want to see: ",theUser.numAccounts());
           theAcct = sc.nextInt()-1;
           if(theAcct<0 || theAcct >= theUser.numAccounts()){
               System.out.println("Invalid Account. Try Again");
           }
        }while(theAcct<0 || theAcct>= theUser.numAccounts());
        
        //print history
        theUser.printAccountTransHistory(theAcct);
    }
    
    public static void transferFunds(user theUser, Scanner sc){
        int fromAcct;
        int toAcct;
        double amount;
        double acctBalance;
        
        do{
            System.out.printf("Enter the number 1-%d of the account \n" + "to transfer from\n", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct<0 || fromAcct>= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while(fromAcct<0 || fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(fromAcct);
        
        do{
            System.out.printf("Enter the number 1-%d of the account \n" + "to transfer to\n", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct>= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while(toAcct<0 || toAcct >= theUser.numAccounts());
        
        do{
            System.out.printf("Enter the amount to transfer (max. $%.02f): $",acctBalance);
            amount = sc.nextDouble();
            if(amount <0){
                System.out.println("Amount must be greater than 0\n");
            }else if(amount > acctBalance){
                System.out.println("Amount cannot be greater than account balance\n");
            }
        }while(amount<0||amount>acctBalance);
        
        //perform transfer
        theUser.addAccountTransaction(fromAcct, -1*amount, String.format("Transfer to account %s\n", theUser.getAccountUUID(toAcct)));
        theUser.addAccountTransaction(toAcct, amount, String.format("Transfer from account %s\n", theUser.getAccountUUID(fromAcct)));
    }
    
    public static void withdrawalFunds(user theUser, Scanner sc){
        int fromAcct;
        double amount;
        double acctBalance;
        String memo;
        
        do{
            System.out.printf("Enter the number 1-%d of the account \n" + "to withdraw from\n", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if(fromAcct<0 || fromAcct>= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again\n");
            }
        }while(fromAcct<0 || fromAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(fromAcct);
        
        do{
            System.out.printf("Enter the amount to withdraw (max. $%.02f): $",acctBalance);
            amount = sc.nextDouble();
            if(amount <0){
                System.out.println("Amount must be greater than 0\n");
            }else if(amount > acctBalance){
                System.out.println("Amount cannot be greater than account balance\n");
            }
        }while(amount<0||amount>acctBalance);
        
        sc.nextLine();
        System.out.println("Enter a Memo:");
        memo = sc.nextLine();

        theUser.addAccountTransaction(fromAcct, -1*amount, String.format("Withdrawal from account %s\n", theUser.getAccountUUID(fromAcct), memo));
    }
    
    public static void depositFunds(user theUser, Scanner sc){
        int toAcct;
        double amount;
        double acctBalance;
        String memo;
        
        do{
            System.out.printf("Enter the number 1-%d of the account \n" + "to deposit to:", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if(toAcct<0 || toAcct>= theUser.numAccounts()){
                System.out.println("Invalid account. Please try again");
            }
        }while(toAcct<0 || toAcct >= theUser.numAccounts());
        acctBalance = theUser.getAccountBalance(toAcct);
        
        do{
            System.out.printf("Enter the amount to deposit (max. $%.02f): $",acctBalance);
            amount = sc.nextDouble();
            if(amount <0){
                System.out.println("Amount must be greater than 0");
            }
        }while(amount<0);

        sc.nextLine();
        System.out.println("Enter a Memo:");
        memo = sc.nextLine();
        
        theUser.addAccountTransaction(toAcct, amount, String.format("Deposit to account %s\n", theUser.getAccountUUID(toAcct), memo));
    }
    
}
