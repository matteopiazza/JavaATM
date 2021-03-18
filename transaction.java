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

import java.util.Date;

public class transaction {
    private double amount;
    private Date timestamp;
    private String memo;
    private account inAccount;
    
    
    public transaction(double amount, account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";
    }
    
    public transaction(double amount, String memo, account inAccount){
        this(amount, inAccount);
        this.memo = memo;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public String getSummaryLine(){
        if(this.amount>=0){
            return String.format("%s: $%.02f : %s", this.timestamp.toString(), this.amount, this.memo);
        }else{
            return String.format("%s: $(%.02f) : %s", this.timestamp.toString(), this.amount, this.memo);
        }
    }
}
