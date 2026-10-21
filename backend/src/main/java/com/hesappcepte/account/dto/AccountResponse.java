package com.hesappcepte.account.dto;

import com.hesappcepte.account.AccountType;
import com.hesappcepte.account.AccountStatus;

import java.math.BigDecimal;

public record AccountResponse(
        Long id,
        String name,
        AccountType accountType,
        String currency,
        BigDecimal initialBalance,
        BigDecimal currentBalance,
        AccountStatus status
) {
}