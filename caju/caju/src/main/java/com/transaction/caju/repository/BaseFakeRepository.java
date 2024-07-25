package com.transaction.caju.repository;

import com.transaction.caju.domain.Entities.Balance.BalanceEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transaction.caju.domain.interfaces.BalanceRepositoryInterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class BaseFakeRepository implements BalanceRepositoryInterface {

    private final ObjectMapper _objectMapper;
    private final String _path;

    protected BaseFakeRepository(String fileName, ObjectMapper objectMapper){
        _path = "caju/fakeDatabase/" + fileName;
        _objectMapper = objectMapper;
    }

    public BalanceEntity getBalance(){
        File jsonFile = new File(_path);
        try {
            return _objectMapper.readValue(jsonFile, BalanceEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BalanceEntity updateBalance(BalanceEntity balance){
        try (FileWriter fileWriter = new FileWriter(_path, false);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            String jsonString = _objectMapper.writeValueAsString(balance);

            bufferedWriter.write(jsonString);
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }
}
