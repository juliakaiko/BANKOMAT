package com.step.bankomatproject.service;

import com.step.bankomatproject.entity.BankAccount;
import com.step.bankomatproject.entity.Bankomat;
import static com.step.bankomatproject.entity.Bankomat.DEPOSIT_LIMIT;
import com.step.bankomatproject.entity.CreditCard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankomatServiceImpl implements BankomatService, Validator{
    private Bankomat bankomat;
    private BufferedReader input;

    public BankomatServiceImpl() {}

    public BankomatServiceImpl(BufferedReader input) {
        this.input = input;
    }

    public BankomatServiceImpl(Bankomat bankomat, BufferedReader input) {
        this.bankomat = bankomat;
        this.input = input;
    }

    @Override
    public void selectTransaction(CreditCard card) {
        String answer = "";
        do {           
            System.out.println("\nDo you want to continue? YES/NO");
            try {
                answer = input.readLine().toLowerCase();
            } catch (IOException ex) {
                Logger.getLogger(BankomatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            switch (answer){
                case "yes":
                    switch (сheckOperationSelection()){
                    case 1:  showBalanse(card);
                        break; 
                    case 2: withdrawCash (card);
                        break; 
                    case 3: depositCash (card);
                        break; 
                    case 4: answer="no"; 
                        System.out.println("Don't forget to pick up your card!");
                        break;
                    }
                break; 
                case "no":answer="no"; 
                    System.out.println("Don't forget to pick up your card!");
                    break;
                default: answer="yes";
                    System.out.println("Incorrect input");
                    break; 
            }
            card.setDate(refreshDate());
            updateData(bankomat.getCardsList());
        }while(!answer.equalsIgnoreCase("no"));
    }

    public int сheckOperationSelection() {
        System.out.println("Select transaction: "+
            "\n1 - CHECK BALANCE" + 
            "\n2 - WITHDRAW CASH" +
            "\n3 - REFILL THE BALANCE"+
            "\n4 - FINISH");   
        String operation = "";
        boolean b;
        do {
            b = false;
            try { 
               operation = input.readLine();
            } catch (IOException ex) {
               Logger.getLogger(BankomatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (operation.length()!=1){
                System.out.println("ERROR");
            }            
            b = checkInputForNums(operation,'1','4');   
        } while (operation.length()!=1 | b); // если хотя бы одно true, то продолжает цикл
        System.out.println("You have chosen the operation "+operation);
        int operationNum = Integer.parseInt(operation); 
        return operationNum; 
    }

    @Override
    public void showBalanse(CreditCard card) {
        System.out.println("Balance sum: " + card.getAccount().getBalance());
    }

    @Override
    public boolean withdrawCash(CreditCard card) {
        long sumBefor = card.getAccount().getBalance();
        long sumOff=0;      
        String sum="";
        System.out.print("Sum to withdraw: ");
        do {
            try { 
                sum = input.readLine(); 
                sumOff = Long.parseLong(sum);
            } catch (NumberFormatException ехс){ 
                sum="-1";
            } catch (IOException ex){
                Logger.getLogger(BankomatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            } 
            if (sumOff >=bankomat.getAtmLimit()){
                System.out.println("You have exceeded the ATM limit.");
                sum="-1";
            }           
        } while (checkInputForNums(sum,'0','9')); 
        
        if (sumOff <= sumBefor){
            card.getAccount().setBalance(sumBefor-sumOff);
            System.out.println("SUCCESSFULLY! Get your money : "+sumOff);
        } else {
            System.out.println("Insufficient funds");
            return false; 
        }    
        return true;   
    }

    @Override
    public boolean depositCash(CreditCard card) {
        long sumBefor = card.getAccount().getBalance();
        long sumIn=0; 
        String sum="";
        System.out.print("Sum to deposit: ");
        do {
            try { 
                sum = input.readLine();
                sumIn = Long.parseLong(sum); 
            } catch (NumberFormatException ехс){ 
                sum="-1";
            } catch (IOException ex){
                Logger.getLogger(BankomatServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (checkInputForNums(sum,'0','9')); 

        if (sumIn >= DEPOSIT_LIMIT){
            System.out.println("The deposit sum may not exceed 1 000 000");
            return false; 
        } else
            card.getAccount().setBalance(sumBefor+sumIn);  
        System.out.println("SUCCESSFULLY! Account balance is " + card.getAccount().getBalance());        
        return true;   
    }
    
    @Override
    public boolean  checkInputForNums(String input, char ch1, char ch2) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!(chars[i] >= ch1 & chars[i]<=ch2)){
                System.out.println("ERROR. Try again");
                return true;
            }      
        }
        return false;
    }
    
    //После каждого изменения данные в файле будут обновляться 
    @Override
    public void recodData(Set <CreditCard> listToFile){
        String fileName = "Bankomat.txt";
        listToFile = bankomat.getCardsList();
        try (FileWriter fileWriter = new FileWriter(fileName, false);){
            for (CreditCard card:listToFile) {
                fileWriter.write (card.toString()+"\n");
            }
            System.out.println("Data was recorded successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        } 
    } 
    
    @Override
    public void updateData(Set <CreditCard> cardsList) {
        bankomat.setCardsList(cardsList);
        recodData(cardsList);
    }
    
    @Override
    public Set <CreditCard> readData() {
        String fileName = "Bankomat.txt";
        String strFromFile = "";
        Set <String> listFromFile = new LinkedHashSet<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {  
            while((strFromFile = fileReader.readLine()) != null) { 
                listFromFile.add(strFromFile);
            }
        }catch(IOException ехс) {
            System.out.println("SERVER ERROR");
        }
        Set <CreditCard> newCardList = new LinkedHashSet <>();
        String [] cardDataFromFile=null; 
        if (!bankomat.getCardsList().isEmpty()){
            for (String s: listFromFile){
                cardDataFromFile = s.split(" ");

                boolean cardStatus;
                if (cardDataFromFile[4].equalsIgnoreCase("NOT_BLOCKED"))
                    cardStatus = false;
                else
                    cardStatus = true;           

                String cardNum = cardDataFromFile[0];
                String pin = cardDataFromFile[1];
                String accountNum = cardDataFromFile[2];
                long balance = Long.parseLong(cardDataFromFile[3]);
                BankAccount account = new  BankAccount (accountNum,balance);
                String date = cardDataFromFile[5]+" "+cardDataFromFile[6];
                CreditCard card = new CreditCard (cardNum, pin, account, cardStatus, date);
                newCardList.add(card);
            }      
        }
        return newCardList;
    } 
   
    @Override
    public void updateBlockedStatus (CreditCard card){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateOfCardBlocking;
        LocalDateTime dateOfCardUnBlocking;
        LocalDateTime now;
        for (CreditCard c: readData()){
            if(c.equals(card)&card.isBlocked()){
                String s = c.getDate();
                dateOfCardBlocking = LocalDateTime.parse(s, dateTimeFormatter);
                System.out.println("dateOfCardBlocking = "+dateOfCardBlocking);
                //Для проверки работы кода поменять на plusMinutes(1l);
                dateOfCardUnBlocking = LocalDateTime.parse(s, dateTimeFormatter).plusDays(1l);
                System.out.println("dateOfCardUnBlocking = "+dateOfCardUnBlocking);
                now = LocalDateTime.parse(refreshDate(), dateTimeFormatter);  
                if (now.isAfter(dateOfCardUnBlocking)){
                    System.out.println("This card is unblocked");
                    card.setBlocked(false); 
                    card.setDate(refreshDate ()); 
                }
            }     
        }        
    }
    
    @Override
    public String refreshDate (){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String newDate = now.format(dateTimeFormatter);
        return newDate;
    }
}
