package com.step.bankomatproject.entity;

import java.util.Objects;

public class CreditCard {
    private String cardNum;
    private String pin;
    private BankAccount account;
    private boolean blocked;
    private String date;

    public CreditCard() {}

    public CreditCard(String cardNum, String pin) {
        this.cardNum = cardNum;
        this.pin = pin;
    }

    public CreditCard(String cardNum, String pin, BankAccount account) {
        this.cardNum = cardNum;
        this.pin = pin;
        this.account = account;
    }

    public CreditCard(String cardNum, String pin, BankAccount account, boolean isBlocked) {
        this.cardNum = cardNum;
        this.pin = pin;
        this.account = account;
        this.blocked = isBlocked;
    }

    public CreditCard(String cardNum, String pin, BankAccount account, boolean blocked, String date) {
        this.cardNum = cardNum;
        this.pin = pin;
        this.account = account;
        this.blocked = blocked;
        this.date = date;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean isBlocked) {
        this.blocked = isBlocked;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.cardNum);
        hash = 89 * hash + Objects.hashCode(this.pin);
        hash = 89 * hash + Objects.hashCode(this.account);
        hash = 89 * hash + (this.blocked ? 1 : 0);
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
        final CreditCard other = (CreditCard) obj;
        if (this.blocked != other.blocked) {
            return false;
        }
        if (!Objects.equals(this.cardNum, other.cardNum)) {
            return false;
        }
        if (!Objects.equals(this.pin, other.pin)) {
            return false;
        }
        return Objects.equals(this.account, other.account);
    }

    

    @Override
    public String toString() {
        String blockStatus="";
        if (this.isBlocked())
            blockStatus = "BLOCKED";
        else
            blockStatus = "NOT_BLOCKED";
        return cardNum + " " + pin + " "+account.getAccountNum()+" "+ account.getBalance()
                + " "+blockStatus+ " " + date + " ";
    }
}
