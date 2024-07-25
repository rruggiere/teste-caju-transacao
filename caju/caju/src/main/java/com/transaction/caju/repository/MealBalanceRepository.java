package com.transaction.caju.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.caju.domain.Entities.Balance.BalanceEntity;
import org.springframework.stereotype.Service;

@Service
public class MealBalanceRepository extends BaseFakeRepository {

    public MealBalanceRepository(ObjectMapper objectMapper) {
        super("meal_fake_balance.json",
                objectMapper);
    }
}
