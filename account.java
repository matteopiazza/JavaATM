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
import java.util.ArrayList;

public class account{

    private String name;
    private double balance;
    private String uuid;
    private user holder;
    
    private ArrayList<transaction> transactions;

    public account(String name, user holder, bank theBank){
       //set account name and holder
        this.name = name;
        this.holder = holder;
        
        this.uuid = theBank.getNewAccountUUID();
        
        this.transactions = new ArrayList<transaction>();

        
        
    }
    
    public String getUUID(){
        return this.uuid;
    }
    
    public String getSummaryLine(){
        double balance = this.getBalance();

        if(balance>0){
            return String.format("%s : $%.02f : %s ", this.uuid, balance, this.name);
        }else{
            return String.format("%s : $(%.02f) : %s ", this.uuid, balance, this.name);
        }
      
        
    }
    
    public double getBalance(){
        double balance = 0;
        for(transaction t: this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }
    
    public void printTransHistory(){
        System.out.printf("\nTransaction history for account %s \n", this.uuid);
        for(int i = this.transactions.size()-1;i>=0;i--){
            System.out.printf(this.transactions.get(i).getSummaryLine());
            
        }
        System.out.println();
    }
    
    public void addTransaction(double amount, String memo){
        transaction newTransaction = new transaction(amount, memo, this);
        this.transactions.add(newTransaction);
    }
   
}