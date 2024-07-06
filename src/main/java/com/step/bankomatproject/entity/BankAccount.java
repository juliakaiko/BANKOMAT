package com.step.bankomatproject.entity;

import java.util.Objects;

public class BankAccount {
    private String accountNum;
    private long balance;

    public BankAccount() {}

    public BankAccount(String accountNum, long balance) {
        this.accountNum = accountNum;
        this.balance = balance;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.accountNum);
        hash = 53 * hash + (int) (this.balance ^ (this.balance >>> 32));
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
        final BankAccount other = (BankAccount) obj;
        if (this.balance != other.balance) {
            return false;
        }
        return Objects.equals(this.accountNum, other.accountNum);
    }

    @Override
    public String toString() {
        return "BankAccount{" + "accountNum=" + accountNum + ", balance=" + balance + '}';
    }  
}
