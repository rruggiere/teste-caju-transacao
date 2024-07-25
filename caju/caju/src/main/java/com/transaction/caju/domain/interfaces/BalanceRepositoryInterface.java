package com.transaction.caju.domain.interfaces;

import com.transaction.caju.domain.Entities.Balance.BalanceEntity;

public interface BalanceRepositoryInterface {
    BalanceEntity getBalance();
    BalanceEntity updateBalance(BalanceEntity balance);
}
