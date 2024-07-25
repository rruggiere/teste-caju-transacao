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
public class FallbackAuthorizerUserCase {

    @Autowired
    private CashBalanceRepository cashBalanceRepository;
    @Autowired
    private FoodBalanceRepository foodBalanceRepository;
    @Autowired
    private MealBalanceRepository mealBalanceRepository;

    public ResponseCodesDetails Execute(TransactionCommand transaction) {
        try {
            if(Arrays.asList(MccCodes.FOOD).contains(transaction.mcc)){
                return  ExecuteInternal(transaction, foodBalanceRepository, true);
            }
            else if(Arrays.asList(MccCodes.MEAL).contains(transaction.mcc)){
                return  ExecuteInternal(transaction, mealBalanceRepository, true);
            }
            else
                return  ExecuteInternal(transaction, cashBalanceRepository, false);
        }
        catch (Exception e) {
            return ResponseCodes.UNEXPECT_ERROR;
        }
    }

    private ResponseCodesDetails ExecuteInternal(TransactionCommand transaction, BalanceRepositoryInterface balanceRepository, Boolean fallBack) {
        BalanceEntity currentBalance = balanceRepository.getBalance();
        var newBalance = currentBalance.balance - transaction.totalAmount;

        if(newBalance < 0){

            if(!fallBack)
                return  ResponseCodes.REJECTED;

            var cashBalance = cashBalanceRepository.getBalance();
            var balanceWithCash = cashBalance.balance + newBalance;

            if(balanceWithCash < 0){
                return ResponseCodes.REJECTED;
            }

            balanceRepository.updateBalance(new BalanceEntity(transaction.account, 0));
            cashBalanceRepository.updateBalance(new BalanceEntity(transaction.account, balanceWithCash));
            return ResponseCodes.APPROVED;
        }

        balanceRepository.updateBalance(new BalanceEntity(transaction.account, newBalance));

        return ResponseCodes.APPROVED;
    }
}
