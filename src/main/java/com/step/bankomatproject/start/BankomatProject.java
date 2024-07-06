package com.step.bankomatproject.start;

import com.step.bankomatproject.controller.BankomatInputController;
import com.step.bankomatproject.entity.BankAccount;
import com.step.bankomatproject.entity.Bankomat;
import com.step.bankomatproject.entity.CreditCard;
import com.step.bankomatproject.service.BankomatService;
import com.step.bankomatproject.service.BankomatServiceImpl;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;
import com.step.bankomatproject.service.CreditCardCService;
import com.step.bankomatproject.service.CreditCardCServiceImpl;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankomatProject {
 
    public static void main(String[] args) throws Exception {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = date.format(dateTimeFormatter);
        
        BankAccount bankAccount1 = new BankAccount ("1", 2000);
        CreditCard card1 = new CreditCard ("4444-3333-2222-1111","5555", bankAccount1, false, formattedDate);
     
        //эта карта будет заблокирована для примера -> см. последний аргумент
        BankAccount bankAccount2 = new BankAccount ("2", 500);
        CreditCard card2 = new CreditCard ("1111-2222-3333-4444","5555", bankAccount2, true, formattedDate);
        
        BankAccount bankAccount3 = new BankAccount ("3", 200);
        CreditCard card3 = new CreditCard ("0000-0000-0000-0000","0000", bankAccount3, false, formattedDate);
        
        Set <CreditCard> cardsList = new LinkedHashSet <>();
        cardsList.add(card1);
        cardsList.add(card2);
        cardsList.add(card3);
      
        Bankomat bankomat = new Bankomat(1000, cardsList);
        
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        
        CreditCardCService cardService = new CreditCardCServiceImpl(input);
        BankomatService bankomatService = new BankomatServiceImpl(bankomat,input);   
        
        BankomatInputController controller = new BankomatInputController (bankomat,cardService, bankomatService);  
        controller.start(); 
    }
}
