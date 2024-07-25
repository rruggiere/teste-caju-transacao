package com.transaction.caju.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.caju.domain.Entities.Balance.BalanceEntity;
import org.springframework.stereotype.Service;

@Service
public class CashBalanceRepository extends BaseFakeRepository {

    public CashBalanceRepository(ObjectMapper objectMapper) {
        super("cash_fake_balance.json",
                objectMapper);
    }
}
