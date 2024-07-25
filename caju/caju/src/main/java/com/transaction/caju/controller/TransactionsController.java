package com.transaction.caju.controller;

import com.transaction.caju.domain.valueObjects.ResponseCodesDetails;
import com.transaction.caju.service.Commands.TransactionCommand;
import com.transaction.caju.service.FallbackAuthorizerUserCase;
import com.transaction.caju.service.MerchantAuthorizerUserCase;
import com.transaction.caju.service.SimpleAuthorizerUserCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {

    @Autowired
    SimpleAuthorizerUserCase simpleAuthorizerUserCase;

    @Autowired
    FallbackAuthorizerUserCase fallbackAuthorizerUserCase;

    @Autowired
    MerchantAuthorizerUserCase merchantAuthorizerUserCase;

    @PostMapping("/simples")
    public ResponseCodesDetails executeSimple(@RequestBody  TransactionCommand command) {
        return simpleAuthorizerUserCase.Execute(command);
    }

    @PostMapping("/fallbacks")
    public ResponseCodesDetails executeWithFallback(@RequestBody TransactionCommand command) {
        return fallbackAuthorizerUserCase.Execute(command);
    }

    @PostMapping("/merchants")
    public ResponseCodesDetails executeWithMerchantReplace(@RequestBody TransactionCommand command) {
        return merchantAuthorizerUserCase.Execute(command);
    }
}
