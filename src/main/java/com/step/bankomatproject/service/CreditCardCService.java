package com.step.bankomatproject.service;

import com.step.bankomatproject.entity.CreditCard;
import java.util.Set;

public interface CreditCardCService {
    
    public CreditCard validateCreditCard (Set <CreditCard> cardsList); 
    public String checkNum();
    public String checkPin(CreditCard card); 

}
