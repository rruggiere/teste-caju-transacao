package com.transaction.caju.service;

import com.transaction.caju.domain.Entities.Balance.BalanceEntity;
import com.transaction.caju.domain.constants.MccCodes;
import com.transaction.caju.domain.constants.MerchantReplaceNames;
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
public class MerchantAuthorizerUserCase {

    @Autowired
    private CashBalanceRepository cashBalanceRepository;
    @Autowired
    private FoodBalanceRepository foodBalanceRepository;
    @Autowired
    private MealBalanceRepository mealBalanceRepository;

    public ResponseCodesDetails Execute(TransactionCommand transaction) {
        try {
            String[] merchantNames = transaction.merchant.split(" ");
            ///Exemplo de como a implementação poderia lidar com nomes conhecidos salvos em determinado banco de dados para subsituir os valores
            if(ReplaceMcc(MerchantReplaceNames.FOOD, transaction.merchant))
                transaction.mcc = MccCodes.FOOD[0];
            else if(ReplaceMcc(MerchantReplaceNames.MEAL, transaction.merchant))
                transaction.mcc = MccCodes.MEAL[0];

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

    private Boolean ReplaceMcc(String[] merchantReplaceNames, String merchant){
        for(String name : merchantReplaceNames){
            if(merchant.contains(name))
                return  true;
        }

        return  false;
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
