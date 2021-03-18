/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaatm;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Matteo
 */
public class bank {
    private String name;
    private ArrayList<user> users;
    private ArrayList<account> account;
    
    public bank(String name){
        this.name = name;
        this.users = new ArrayList<user>();
        this.account = new ArrayList<account>();
        
    }
    public String getNewUserUUID(){
        String uuid;
        Random rng = new Random();
        int len = 5;
        boolean nonUnique;
        
        //loop until a unique id provided
        do{
            uuid = "";
            for(int i = 0; i <len; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //check if unique
            nonUnique = false;
            for(user u: this.users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        
        
        return uuid;
        
    }
    
    public String getNewAccountUUID(){
        String uuid;
        Random rng = new Random();
        int len = 7;
        boolean nonUnique;
        
        //loop until a unique id provided
        do{
            uuid = "";
            for(int i = 0; i <len; i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }
            //check if unique
            nonUnique = false;
            for(account a: this.account){
                if(uuid.compareTo(a.getUUID()) ==0){
                    nonUnique = true;
                    break;
                }
            }
        }while(nonUnique);
        return uuid;
    }

    public void addAccount(account anAccount){
       this.account.add(anAccount);
    }
    
    public user addUser(String firstName, String lastName, String pin){
        user newUser = new user(firstName, lastName, pin, this);
        this.users.add(newUser);
        
        account newAccount = new account("Savings", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);
        
        return newUser;
    }
    
    public user userLogin(String userID, String pin){
        //search through list of users
        for(user u: this.users){
            //check userID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }
    
    public String getName(){
        return this.name;
    }
    
    
}
