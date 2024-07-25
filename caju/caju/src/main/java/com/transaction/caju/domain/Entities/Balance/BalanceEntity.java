package com.transaction.caju.domain.Entities.Balance;

public class BalanceEntity {

    public BalanceEntity(String accountId, double balance){
        this.accountId = accountId;
        this.balance = balance;
    }
    public final String accountId;
    public final double balance;
}
