package com.transaction.caju.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.transaction.caju.domain.Entities.Balance.BalanceEntity;

@Service
public class FoodBalanceRepository extends BaseFakeRepository {

    public FoodBalanceRepository(ObjectMapper objectMapper) {
        super("food_fake_balance.json",
                objectMapper);
    }
}
