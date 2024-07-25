package com.transaction.caju.service;

import com.transaction.caju.domain.Entities.Balance.BalanceEntity;
import com.transaction.caju.domain.constants.MccCodes;
import com.transaction.caju.domain.constants.ResponseCodes;
import com.transaction.caju.domain.interfaces.BalanceRepositoryInterface;
import com.transaction.caju.domain.valueObjects.ResponseCodesDetails;
import com.transaction.caju.repository.CashBalanceRepository;
import com.transaction.caju.repository.FoodBalanceRepository;
import com.transaction.caju.repository.MealBalanceRepository;
import com.transaction.caju.service.Commands.TransactionCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SimpleAuthorizerUserCase {

    @Autowired
    private CashBalanceRepository cashBalanceRepository;
    @Autowired
    private FoodBalanceRepository foodBalanceRepository;
    @Autowired
    private MealBalanceRepository mealBalanceRepository;

    public ResponseCodesDetails Execute(TransactionCommand transaction) {
        try {
            if(Arrays.asList(MccCodes.FOOD).contains(transaction.mcc)){
                return  ExecuteInternal(transaction, foodBalanceRepository);
            }
            else if(Arrays.asList(MccCodes.MEAL).contains(transaction.mcc)){
                return  ExecuteInternal(transaction, mealBalanceRepository);
            }
            else
                return  ExecuteInternal(transaction, cashBalanceRepository);
        }
        catch (Exception e) {
            return ResponseCodes.UNEXPECT_ERROR;
        }
    }

    private ResponseCodesDetails ExecuteInternal(TransactionCommand transaction, BalanceRepositoryInterface balanceRepository) {
        BalanceEntity currentBalance = balanceRepository.getBalance();
        var newBalance = currentBalance.balance - transaction.totalAmount;

        if(newBalance < 0){
            return ResponseCodes.REJECTED;
        }

        balanceRepository.updateBalance(new BalanceEntity(transaction.account, newBalance));

        return ResponseCodes.APPROVED;
    }
}
