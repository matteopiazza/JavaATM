/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaatm;

import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class user{

    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];

    private ArrayList<account> accounts;
    
    public user(String firstName, String lastName, String pin, bank theBank){
       //set user name
        this.firstName = firstName;
        this.lastName = lastName;
        
      //store the pin's MD5 hash for security reasons
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught noSuchAlgorithmException");
            System.exit(1);
        }
        
        //provide new uuid for user
        this.uuid = theBank.getNewUserUUID();
        
       //create array for accounts
        this.accounts = new ArrayList<account>();
        
        System.out.printf("New user %s, %s with ID %s created. \n", firstName, lastName, this.uuid);
        
    }
    
    public void addAccount(account anAccount){
       this.accounts.add(anAccount);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        } catch (NoSuchAlgorithmException ex) {
            System.err.println("error, caught noSuchAlgorithmException");
            ex.printStackTrace();
            System.exit(1);
        }
       
        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }
    
    public void printAccountsSummary(){
        System.out.println(accounts.size());
        System.out.printf("\n%s Accounts Summary:\n", this.firstName);
        for(int i=0;i<this.accounts.size();i++){
            System.out.printf("%d) %s \n", i+1, this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }
    
    public int numAccounts(){
        return this.accounts.size();
    }
    
    public void printAccountTransHistory(int acctIdx){
        this.accounts.get(acctIdx).printTransHistory();
    }
    
    public double getAccountBalance(int acctIdx){
        return this.accounts.get(acctIdx).getBalance();
    }
    
    public String getAccountUUID(int acctIdx){
        return this.accounts.get(acctIdx).getUUID();
    }
    
    public void addAccountTransaction(int acctIdx, double amount, String memo){
        this.accounts.get(acctIdx).addTransaction(amount, memo);
    }
    
  
    
}

