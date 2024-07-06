package com.step.bankomatproject.entity;

import java.util.Objects;
import java.util.Set;

public class Bankomat {
    
    public final static long DEPOSIT_LIMIT = 1_000_000; // сумма пополнения не больше 1000000
    private long atmLimit; // лимит средств в банкомате 
    private Set <CreditCard> cardsList;

    public Bankomat() {}
    
    public Bankomat(long atmLimit, Set<CreditCard> cardsList) {
        this.atmLimit = atmLimit;
        this.cardsList = cardsList;
    }
       
    public long getAtmLimit() {
        return atmLimit;
    }

    public void setAtmLimit(long atmLimit) {
        this.atmLimit = atmLimit;
    }

    public Set<CreditCard> getCardsList() {
        return cardsList;
    }

    public void setCardsList(Set<CreditCard> cardsList) {
        this.cardsList = cardsList;
    }
 
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.atmLimit ^ (this.atmLimit >>> 32));
        hash = 83 * hash + Objects.hashCode(this.cardsList);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bankomat other = (Bankomat) obj;
        if (this.atmLimit != other.atmLimit) {
            return false;
        }
        return Objects.equals(this.cardsList, other.cardsList);
    }

    @Override
    public String toString() {
        return "Bankomat{" + "atmLimit=" + atmLimit + ", cardsList=" + cardsList + '}';
    }  
}
