package com.step.bankomatproject.service;

import com.step.bankomatproject.entity.CreditCard;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreditCardCServiceImpl implements CreditCardCService, Validator{
    private boolean b;
    private BufferedReader input;

    public CreditCardCServiceImpl() {}

    public CreditCardCServiceImpl(BufferedReader input) {
        this.input = input;
    }
    
    @Override
    public CreditCard validateCreditCard (Set <CreditCard> cardsList){
        CreditCard card=null;
        String validatedNum="";
        int a;
        for  (a=0; a<=cardsList.size(); a++) {
            validatedNum = checkNum();
            for (CreditCard c : cardsList) {
                if (!c.getCardNum().equalsIgnoreCase(validatedNum)){ //& a!=cardsList.size()
                    a=-1;                  
                } else {
                    a=cardsList.size();
                    card = c;
                    break;
                }
            }
            if (a==-1) System.out.print(" doesn't exist. Try again\n");
        }
        try {
            if (!card.isBlocked())
                checkPin(card);
            else {
                System.out.println("This card was blocked for 24 hours");
            }
        }catch (NullPointerException e){
            System.out.print("doesn't exist. Try again\n");
        }   
        return card;
    }
   
    @Override
    public String checkNum() {
        System.out.println("ENTER YOUR CREDITCARD NUMBER: ");
        String cardNum = checkInputForCardNum (input,16);  
        return formatInput(cardNum);  
    }   
    
    @Override
    public String checkPin(CreditCard card) {
        System.out.println("ENTER YOUR PIN: ");
        String cardPin = checkInputForCardPin(card,input, 4);
        if (!card.isBlocked())           
            System.out.println("You have successfully logged in");   
        return cardPin;
    } 

    @Override
    public boolean  checkInputForNums(String input,char ch1,char ch2) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!(chars[i] >= '0' & chars[i]<='9')){
                System.out.println("ERROR. Not a number");
                return true;
            }      
        }
        return false;
    }   
        
     
    @Override
    public String refreshDate (){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String newDate = now.format(dateTimeFormatter);
        return newDate;
    }
    
    public String formatInput (String cardNum){ // XXXX-XXXX-XXXX-XXXX
          for (int i = 4; i < cardNum.length(); i+=4){
            if (cardNum.length() - i >= i/4){
                String before = cardNum.substring(0, i + (i/4 - 1));
                String after = cardNum.substring(i + (i/4 - 1), cardNum.length());
                cardNum = before + "-" + after;
            }
        }   
        System.out.println("CreditCard: " + cardNum);
        return cardNum;
    }
        
    public String  checkInputForCardNum(BufferedReader input, int length) {
        String result = " ";
        do {
            b = false;
            try {
                result = input.readLine();
            } catch (IOException ex) {
                Logger.getLogger(CreditCardCServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (result.length()!=length){
                System.out.println("ERROR. Invalid length");
            }
            b = checkInputForNums(result,'0','9');
        } while (result.length()!=length | b); // если хотя бы одно true, то продолжает цикл
        return result;
    }
    
    public String checkInputForCardPin(CreditCard card,BufferedReader input,int length) {
        String result = " ";
        int attemptsNum = 0;
        do {
            b = false;
            try {
                result = input.readLine();
            } catch (IOException ex) {
                Logger.getLogger(CreditCardCServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (!card.getPin().equalsIgnoreCase(result)) {
                System.out.println("INCORRECT PASSWORD");
                b=true;
            }
            b = checkInputForNums(result,'0','9');
            attemptsNum++;
        } while ((result.length()!=length | b)&attemptsNum!=3); // если хотя бы одно true, то продолжает цикл
        
        if (attemptsNum==3){
            card.setBlocked(true);
            card.setDate(refreshDate ());
            System.out.println("The number of attempts's exceeded 3. Your creditcard is blocked for 24 hours");
        }
        return result;
    }
    
}
