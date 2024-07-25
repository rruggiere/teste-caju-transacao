package com.transaction.caju.domain.constants;

import com.transaction.caju.domain.valueObjects.ResponseCodesDetails;

public final class ResponseCodes {
    public static final ResponseCodesDetails APPROVED = new ResponseCodesDetails("00", "Transação aprovada");
    public static final ResponseCodesDetails REJECTED = new ResponseCodesDetails("51", "Transação rejeitada. Saldo insuficiente");
    public static final ResponseCodesDetails UNEXPECT_ERROR = new ResponseCodesDetails("07", "Transação rejeitada. Um erro inesperado aconteceu");
}

