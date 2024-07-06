package com.step.bankomatproject.service;

import com.step.bankomatproject.entity.CreditCard;
import java.util.Set;

public interface BankomatService {
    
    public void selectTransaction(CreditCard card);
    public void showBalanse (CreditCard card);
    public boolean withdrawCash (CreditCard card);
    public boolean depositCash (CreditCard card);
    
    public void recodData(Set <CreditCard> cardsList);
    public Set <CreditCard> readData();
    public void updateData(Set <CreditCard> cardsList);
    
    public void updateBlockedStatus (CreditCard card);  
}
